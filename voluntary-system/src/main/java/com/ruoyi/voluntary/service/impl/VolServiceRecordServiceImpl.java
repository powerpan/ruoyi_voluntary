package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voluntary.domain.VolServiceRecord;
import com.ruoyi.voluntary.mapper.VolServiceRecordMapper;
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
}
