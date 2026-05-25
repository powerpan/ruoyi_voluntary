package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.domain.VolActivityQrToken;
import com.ruoyi.voluntary.domain.VolActivitySignup;
import com.ruoyi.voluntary.domain.VolCheckinRecord;
import com.ruoyi.voluntary.domain.VolScanInfo;
import com.ruoyi.voluntary.mapper.VolActivityMapper;
import com.ruoyi.voluntary.mapper.VolActivityQrTokenMapper;
import com.ruoyi.voluntary.mapper.VolActivitySignupMapper;
import com.ruoyi.voluntary.mapper.VolCheckinRecordMapper;
import com.ruoyi.voluntary.service.IVolCheckinRecordService;
import com.ruoyi.voluntary.service.IVolServiceRecordService;

/**
 * 签到签退记录 Service 实现
 */
@Service
public class VolCheckinRecordServiceImpl implements IVolCheckinRecordService
{
    public static final String METHOD_QR = "qr";

    public static final String METHOD_MANUAL = "manual";

    public static final Integer STATUS_CHECKED_IN = 0;

    public static final Integer STATUS_CHECKED_OUT = 1;

    public static final Integer STATUS_ABNORMAL = 2;

    public static final Integer STATUS_MANUAL_CONFIRMED = 3;

    private static final Integer ACTIVITY_STATUS_PUBLISHED = 1;

    private static final Integer SIGNUP_STATUS_APPROVED = 1;

    @Autowired
    private VolCheckinRecordMapper checkinRecordMapper;

    @Autowired
    private VolActivityQrTokenMapper qrTokenMapper;

    @Autowired
    private VolActivityMapper activityMapper;

    @Autowired
    private VolActivitySignupMapper signupMapper;

    @Autowired
    private IVolServiceRecordService serviceRecordService;

    @Override
    public VolCheckinRecord selectVolCheckinRecordById(Long id)
    {
        return checkinRecordMapper.selectVolCheckinRecordById(id);
    }

    @Override
    public VolCheckinRecord selectVolCheckinRecordByActivityIdAndVolunteerUserId(Long activityId, Long volunteerUserId)
    {
        return checkinRecordMapper.selectVolCheckinRecordByActivityIdAndVolunteerUserId(activityId, volunteerUserId);
    }

    @Override
    public List<VolCheckinRecord> selectVolCheckinRecordList(VolCheckinRecord checkinRecord)
    {
        return checkinRecordMapper.selectVolCheckinRecordList(
                checkinRecord == null ? new VolCheckinRecord() : checkinRecord);
    }

    @Override
    public List<VolCheckinRecord> selectMyCheckinRecordList(Long userId, VolCheckinRecord checkinRecord)
    {
        if (userId == null)
        {
            throw new ServiceException("用户ID不能为空");
        }
        VolCheckinRecord query = checkinRecord == null ? new VolCheckinRecord() : checkinRecord;
        query.setVolunteerUserId(userId);
        return checkinRecordMapper.selectVolCheckinRecordList(query);
    }

    @Override
    public VolScanInfo getScanInfo(String token, Long userId)
    {
        ScanContext context = buildScanContext(token, userId);
        VolScanInfo scanInfo = new VolScanInfo();
        scanInfo.setQrToken(context.qrToken);
        scanInfo.setActivity(context.activity);
        scanInfo.setSignup(context.signup);
        scanInfo.setCheckinRecord(context.checkinRecord);
        scanInfo.setActionable(Boolean.valueOf(isActionable(context)));
        scanInfo.setMessage(resolveScanMessage(context));
        return scanInfo;
    }

    @Override
    @Transactional
    public VolCheckinRecord checkinByQrToken(String token, Long userId, String username)
    {
        ScanContext context = buildScanContext(token, userId);
        requireActionType(context.qrToken, VolActivityQrTokenServiceImpl.ACTION_CHECKIN);
        validateSignupApproved(context.signup);
        if (context.checkinRecord != null)
        {
            if (STATUS_CHECKED_IN.equals(context.checkinRecord.getStatus()))
            {
                throw new ServiceException("已完成签到，不能重复签到");
            }
            if (STATUS_CHECKED_OUT.equals(context.checkinRecord.getStatus()))
            {
                throw new ServiceException("已完成签退，不能重复签到");
            }
            throw new ServiceException("已有签到记录，当前状态不允许重复签到");
        }

        Date now = new Date();
        VolCheckinRecord checkinRecord = new VolCheckinRecord();
        checkinRecord.setActivityId(context.qrToken.getActivityId());
        checkinRecord.setSignupId(context.signup.getId());
        checkinRecord.setVolunteerUserId(userId);
        checkinRecord.setCheckinTime(now);
        checkinRecord.setCheckinMethod(METHOD_QR);
        checkinRecord.setStatus(STATUS_CHECKED_IN);
        checkinRecord.setCreateBy(username);
        checkinRecord.setCreateTime(now);
        checkinRecord.setRemark("令牌签到");
        if (checkinRecordMapper.insertVolCheckinRecord(checkinRecord) <= 0 || checkinRecord.getId() == null)
        {
            throw new ServiceException("签到失败");
        }
        return checkinRecordMapper.selectVolCheckinRecordById(checkinRecord.getId());
    }

    @Override
    @Transactional
    public VolCheckinRecord checkoutByQrToken(String token, Long userId, String username)
    {
        ScanContext context = buildScanContext(token, userId);
        requireActionType(context.qrToken, VolActivityQrTokenServiceImpl.ACTION_CHECKOUT);
        validateSignupApproved(context.signup);
        if (context.checkinRecord == null || context.checkinRecord.getCheckinTime() == null)
        {
            throw new ServiceException("尚未签到，不能签退");
        }
        if (STATUS_CHECKED_OUT.equals(context.checkinRecord.getStatus()))
        {
            throw new ServiceException("已完成签退，不能重复签退");
        }
        if (!STATUS_CHECKED_IN.equals(context.checkinRecord.getStatus()))
        {
            throw new ServiceException("当前签到状态不允许签退");
        }

        Date now = new Date();
        VolCheckinRecord updateRecord = new VolCheckinRecord();
        updateRecord.setId(context.checkinRecord.getId());
        updateRecord.setCheckoutTime(now);
        updateRecord.setCheckoutMethod(METHOD_QR);
        updateRecord.setStatus(STATUS_CHECKED_OUT);
        updateRecord.setUpdateBy(username);
        updateRecord.setUpdateTime(now);
        updateRecord.setRemark("令牌签退");
        if (checkinRecordMapper.updateVolCheckinRecord(updateRecord) <= 0)
        {
            throw new ServiceException("签退失败");
        }
        VolCheckinRecord updatedRecord = checkinRecordMapper.selectVolCheckinRecordById(context.checkinRecord.getId());
        serviceRecordService.generateServiceRecordFromCheckinRecord(updatedRecord.getId(), username);
        return updatedRecord;
    }

    @Override
    public int insertVolCheckinRecord(VolCheckinRecord checkinRecord)
    {
        validateCheckinRecord(checkinRecord);
        if (checkinRecord.getStatus() == null)
        {
            checkinRecord.setStatus(STATUS_CHECKED_IN);
        }
        checkinRecord.setCreateTime(new Date());
        return checkinRecordMapper.insertVolCheckinRecord(checkinRecord);
    }

    @Override
    public int updateVolCheckinRecord(VolCheckinRecord checkinRecord)
    {
        if (checkinRecord == null || checkinRecord.getId() == null)
        {
            throw new ServiceException("签到记录ID不能为空");
        }
        checkinRecord.setUpdateTime(new Date());
        return checkinRecordMapper.updateVolCheckinRecord(checkinRecord);
    }

    @Override
    public int deleteVolCheckinRecordById(Long id)
    {
        return checkinRecordMapper.deleteVolCheckinRecordById(id);
    }

    @Override
    public int deleteVolCheckinRecordByIds(Long[] ids)
    {
        return checkinRecordMapper.deleteVolCheckinRecordByIds(ids);
    }

    private ScanContext buildScanContext(String token, Long userId)
    {
        if (token == null || token.trim().length() == 0)
        {
            throw new ServiceException("签到令牌不能为空");
        }
        if (userId == null)
        {
            throw new ServiceException("用户ID不能为空");
        }
        VolActivityQrToken qrToken = qrTokenMapper.selectValidVolActivityQrTokenByToken(token);
        if (qrToken == null)
        {
            throw new ServiceException("签到令牌不存在、已失效或已过期");
        }
        VolActivity activity = activityMapper.selectVolActivityById(qrToken.getActivityId());
        if (activity == null)
        {
            throw new ServiceException("活动不存在");
        }
        if (!ACTIVITY_STATUS_PUBLISHED.equals(activity.getStatus()))
        {
            throw new ServiceException("活动未发布，不能令牌签到签退");
        }
        VolActivitySignup signup = signupMapper.selectVolActivitySignupByActivityIdAndVolunteerUserId(
                qrToken.getActivityId(), userId);
        VolCheckinRecord checkinRecord = checkinRecordMapper.selectVolCheckinRecordByActivityIdAndVolunteerUserId(
                qrToken.getActivityId(), userId);
        return new ScanContext(qrToken, activity, signup, checkinRecord);
    }

    private boolean isActionable(ScanContext context)
    {
        if (context.signup == null || !SIGNUP_STATUS_APPROVED.equals(context.signup.getStatus()))
        {
            return false;
        }
        if (VolActivityQrTokenServiceImpl.ACTION_CHECKIN.equals(context.qrToken.getActionType()))
        {
            return context.checkinRecord == null;
        }
        if (VolActivityQrTokenServiceImpl.ACTION_CHECKOUT.equals(context.qrToken.getActionType()))
        {
            return context.checkinRecord != null && STATUS_CHECKED_IN.equals(context.checkinRecord.getStatus());
        }
        return false;
    }

    private String resolveScanMessage(ScanContext context)
    {
        if (context.signup == null)
        {
            return "未报名该活动，不能令牌签到签退";
        }
        if (!SIGNUP_STATUS_APPROVED.equals(context.signup.getStatus()))
        {
            return "报名未通过，不能令牌签到签退";
        }
        if (VolActivityQrTokenServiceImpl.ACTION_CHECKIN.equals(context.qrToken.getActionType()))
        {
            if (context.checkinRecord == null)
            {
                return "可以签到";
            }
            if (STATUS_CHECKED_IN.equals(context.checkinRecord.getStatus()))
            {
                return "已完成签到，不能重复签到";
            }
            if (STATUS_CHECKED_OUT.equals(context.checkinRecord.getStatus()))
            {
                return "已完成签退";
            }
        }
        if (VolActivityQrTokenServiceImpl.ACTION_CHECKOUT.equals(context.qrToken.getActionType()))
        {
            if (context.checkinRecord == null)
            {
                return "尚未签到，不能签退";
            }
            if (STATUS_CHECKED_IN.equals(context.checkinRecord.getStatus()))
            {
                return "可以签退";
            }
            if (STATUS_CHECKED_OUT.equals(context.checkinRecord.getStatus()))
            {
                return "已完成签退，不能重复签退";
            }
        }
        return "当前令牌状态不允许操作";
    }

    private void requireActionType(VolActivityQrToken qrToken, String requiredActionType)
    {
        if (!requiredActionType.equals(qrToken.getActionType()))
        {
            throw new ServiceException("令牌操作类型不匹配");
        }
    }

    private void validateSignupApproved(VolActivitySignup signup)
    {
        if (signup == null)
        {
            throw new ServiceException("未报名该活动，不能令牌签到签退");
        }
        if (!SIGNUP_STATUS_APPROVED.equals(signup.getStatus()))
        {
            throw new ServiceException("报名未通过，不能令牌签到签退");
        }
    }

    private void validateCheckinRecord(VolCheckinRecord checkinRecord)
    {
        if (checkinRecord == null)
        {
            throw new ServiceException("签到记录不能为空");
        }
        if (checkinRecord.getActivityId() == null)
        {
            throw new ServiceException("活动ID不能为空");
        }
        if (checkinRecord.getSignupId() == null)
        {
            throw new ServiceException("报名ID不能为空");
        }
        if (checkinRecord.getVolunteerUserId() == null)
        {
            throw new ServiceException("志愿者用户ID不能为空");
        }
    }

    private static class ScanContext
    {
        private final VolActivityQrToken qrToken;

        private final VolActivity activity;

        private final VolActivitySignup signup;

        private final VolCheckinRecord checkinRecord;

        private ScanContext(VolActivityQrToken qrToken, VolActivity activity, VolActivitySignup signup,
                VolCheckinRecord checkinRecord)
        {
            this.qrToken = qrToken;
            this.activity = activity;
            this.signup = signup;
            this.checkinRecord = checkinRecord;
        }
    }
}
