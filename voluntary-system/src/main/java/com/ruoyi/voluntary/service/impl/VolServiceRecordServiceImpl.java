package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.domain.VolCheckinRecord;
import com.ruoyi.voluntary.domain.VolServiceRecord;
import com.ruoyi.voluntary.domain.VolServiceSummary;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;
import com.ruoyi.voluntary.mapper.VolActivityMapper;
import com.ruoyi.voluntary.mapper.VolCheckinRecordMapper;
import com.ruoyi.voluntary.mapper.VolServiceRecordMapper;
import com.ruoyi.voluntary.mapper.VolVolunteerProfileMapper;
import com.ruoyi.voluntary.service.IVolServiceRecordService;

/**
 * 服务记录 Service 实现
 */
@Service
public class VolServiceRecordServiceImpl implements IVolServiceRecordService
{
    public static final Integer STATUS_PENDING = 0;

    public static final Integer STATUS_EFFECTIVE = 1;

    public static final Integer STATUS_ABNORMAL = 2;

    public static final Integer STATUS_VOIDED = 3;

    @Autowired
    private VolServiceRecordMapper serviceRecordMapper;

    @Autowired
    private VolCheckinRecordMapper checkinRecordMapper;

    @Autowired
    private VolActivityMapper activityMapper;

    @Autowired
    private VolVolunteerProfileMapper volunteerProfileMapper;

    @Override
    public VolServiceRecord selectVolServiceRecordById(Long id)
    {
        return serviceRecordMapper.selectVolServiceRecordById(id);
    }

    @Override
    public VolServiceRecord selectVolServiceRecordByCheckinRecordId(Long checkinRecordId)
    {
        return serviceRecordMapper.selectVolServiceRecordByCheckinRecordId(checkinRecordId);
    }

    @Override
    public List<VolServiceRecord> selectVolServiceRecordList(VolServiceRecord serviceRecord)
    {
        return serviceRecordMapper.selectVolServiceRecordList(
                serviceRecord == null ? new VolServiceRecord() : serviceRecord);
    }

    @Override
    public List<VolServiceRecord> selectMyServiceRecordList(Long userId, VolServiceRecord serviceRecord)
    {
        if (userId == null)
        {
            throw new ServiceException("用户ID不能为空");
        }
        VolServiceRecord query = serviceRecord == null ? new VolServiceRecord() : serviceRecord;
        query.setVolunteerUserId(userId);
        return serviceRecordMapper.selectVolServiceRecordList(query);
    }

    @Override
    public VolServiceSummary selectMyServiceSummary(Long userId)
    {
        if (userId == null)
        {
            throw new ServiceException("用户ID不能为空");
        }
        VolServiceSummary summary = new VolServiceSummary();
        summary.setVolunteerUserId(userId);
        summary.setTotalServiceMinutes(sumEffectiveServiceMinutesByVolunteerUserId(userId));
        summary.setServiceCount(countEffectiveServiceRecordByVolunteerUserId(userId));
        return summary;
    }

    @Override
    public int sumEffectiveServiceMinutesByVolunteerUserId(Long volunteerUserId)
    {
        if (volunteerUserId == null)
        {
            throw new ServiceException("志愿者用户ID不能为空");
        }
        return serviceRecordMapper.sumEffectiveServiceMinutesByVolunteerUserId(volunteerUserId);
    }

    @Override
    public int countEffectiveServiceRecordByVolunteerUserId(Long volunteerUserId)
    {
        if (volunteerUserId == null)
        {
            throw new ServiceException("志愿者用户ID不能为空");
        }
        return serviceRecordMapper.countEffectiveServiceRecordByVolunteerUserId(volunteerUserId);
    }

    @Override
    @Transactional
    public VolServiceRecord generateServiceRecordFromCheckinRecord(Long checkinRecordId, String username)
    {
        if (checkinRecordId == null)
        {
            throw new ServiceException("签到记录ID不能为空");
        }
        VolServiceRecord existedRecord = serviceRecordMapper.selectVolServiceRecordByCheckinRecordId(checkinRecordId);
        if (existedRecord != null)
        {
            return existedRecord;
        }
        VolCheckinRecord checkinRecord = checkinRecordMapper.selectVolCheckinRecordById(checkinRecordId);
        validateCheckoutRecord(checkinRecord);

        VolActivity activity = activityMapper.selectVolActivityById(checkinRecord.getActivityId());
        if (activity == null)
        {
            throw new ServiceException("活动不存在");
        }

        Date now = new Date();
        int serviceMinutes = calculateServiceMinutes(checkinRecord, activity);
        VolServiceRecord serviceRecord = new VolServiceRecord();
        serviceRecord.setActivityId(checkinRecord.getActivityId());
        serviceRecord.setSignupId(checkinRecord.getSignupId());
        serviceRecord.setCheckinRecordId(checkinRecord.getId());
        serviceRecord.setVolunteerUserId(checkinRecord.getVolunteerUserId());
        serviceRecord.setServiceDate(new java.sql.Date(checkinRecord.getCheckinTime().getTime()));
        serviceRecord.setStartTime(checkinRecord.getCheckinTime());
        serviceRecord.setEndTime(checkinRecord.getCheckoutTime());
        serviceRecord.setServiceMinutes(serviceMinutes);
        serviceRecord.setStatus(serviceMinutes > 0 ? STATUS_EFFECTIVE : STATUS_ABNORMAL);
        serviceRecord.setCreateBy(username);
        serviceRecord.setCreateTime(now);
        serviceRecord.setRemark(serviceMinutes > 0 ? "二维码签退自动生成服务记录" : "服务时长异常");

        if (serviceRecordMapper.insertVolServiceRecord(serviceRecord) <= 0 || serviceRecord.getId() == null)
        {
            throw new ServiceException("生成服务记录失败");
        }
        refreshVolunteerServiceSummary(checkinRecord.getVolunteerUserId(), username, now);
        return serviceRecordMapper.selectVolServiceRecordById(serviceRecord.getId());
    }

    @Override
    public int insertVolServiceRecord(VolServiceRecord serviceRecord)
    {
        validateServiceRecord(serviceRecord);
        if (serviceRecord.getStatus() == null)
        {
            serviceRecord.setStatus(STATUS_EFFECTIVE);
        }
        if (serviceRecord.getServiceMinutes() == null)
        {
            serviceRecord.setServiceMinutes(0);
        }
        serviceRecord.setCreateTime(new Date());
        return serviceRecordMapper.insertVolServiceRecord(serviceRecord);
    }

    @Override
    public int updateVolServiceRecord(VolServiceRecord serviceRecord)
    {
        if (serviceRecord == null || serviceRecord.getId() == null)
        {
            throw new ServiceException("服务记录ID不能为空");
        }
        serviceRecord.setUpdateTime(new Date());
        return serviceRecordMapper.updateVolServiceRecord(serviceRecord);
    }

    @Override
    public int deleteVolServiceRecordById(Long id)
    {
        return serviceRecordMapper.deleteVolServiceRecordById(id);
    }

    @Override
    public int deleteVolServiceRecordByIds(Long[] ids)
    {
        return serviceRecordMapper.deleteVolServiceRecordByIds(ids);
    }

    private void validateServiceRecord(VolServiceRecord serviceRecord)
    {
        if (serviceRecord == null)
        {
            throw new ServiceException("服务记录不能为空");
        }
        if (serviceRecord.getActivityId() == null)
        {
            throw new ServiceException("活动ID不能为空");
        }
        if (serviceRecord.getSignupId() == null)
        {
            throw new ServiceException("报名ID不能为空");
        }
        if (serviceRecord.getCheckinRecordId() == null)
        {
            throw new ServiceException("签到记录ID不能为空");
        }
        if (serviceRecord.getVolunteerUserId() == null)
        {
            throw new ServiceException("志愿者用户ID不能为空");
        }
        if (serviceRecord.getServiceDate() == null || serviceRecord.getStartTime() == null
                || serviceRecord.getEndTime() == null)
        {
            throw new ServiceException("服务日期和时间不能为空");
        }
    }

    private void validateCheckoutRecord(VolCheckinRecord checkinRecord)
    {
        if (checkinRecord == null)
        {
            throw new ServiceException("签到记录不存在");
        }
        if (!VolCheckinRecordServiceImpl.STATUS_CHECKED_OUT.equals(checkinRecord.getStatus()))
        {
            throw new ServiceException("签到记录未完成签退，不能生成服务记录");
        }
        if (checkinRecord.getCheckinTime() == null || checkinRecord.getCheckoutTime() == null)
        {
            throw new ServiceException("签到签退时间不完整，不能生成服务记录");
        }
    }

    private int calculateServiceMinutes(VolCheckinRecord checkinRecord, VolActivity activity)
    {
        long durationMillis = checkinRecord.getCheckoutTime().getTime() - checkinRecord.getCheckinTime().getTime();
        if (durationMillis <= 0)
        {
            return 0;
        }
        int serviceMinutes = (int) Math.ceil(durationMillis / 60000.0D);
        Integer maxServiceMinutes = activity.getMaxServiceMinutes();
        if (maxServiceMinutes != null && maxServiceMinutes > 0 && serviceMinutes > maxServiceMinutes)
        {
            return maxServiceMinutes;
        }
        return serviceMinutes;
    }

    private void refreshVolunteerServiceSummary(Long volunteerUserId, String username, Date now)
    {
        VolVolunteerProfile profile = volunteerProfileMapper.selectVolVolunteerProfileByUserId(volunteerUserId);
        if (profile == null)
        {
            throw new ServiceException("志愿者档案不存在");
        }
        VolVolunteerProfile updateProfile = new VolVolunteerProfile();
        updateProfile.setId(profile.getId());
        updateProfile.setTotalServiceMinutes(sumEffectiveServiceMinutesByVolunteerUserId(volunteerUserId));
        updateProfile.setServiceCount(countEffectiveServiceRecordByVolunteerUserId(volunteerUserId));
        updateProfile.setUpdateBy(username);
        updateProfile.setUpdateTime(now);
        if (volunteerProfileMapper.updateVolVolunteerProfile(updateProfile) <= 0)
        {
            throw new ServiceException("更新志愿者服务时长失败");
        }
    }
}
