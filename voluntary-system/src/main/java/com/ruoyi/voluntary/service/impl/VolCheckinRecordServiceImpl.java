package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voluntary.domain.VolCheckinRecord;
import com.ruoyi.voluntary.mapper.VolCheckinRecordMapper;
import com.ruoyi.voluntary.service.IVolCheckinRecordService;

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

    @Autowired
    private VolCheckinRecordMapper checkinRecordMapper;

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
}
