package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.voluntary.domain.VolActivitySignup;
import com.ruoyi.voluntary.mapper.VolActivitySignupMapper;
import com.ruoyi.voluntary.service.IVolActivitySignupService;

/**
 * 活动报名 Service 实现
 */
@Service
public class VolActivitySignupServiceImpl implements IVolActivitySignupService
{
    public static final Integer STATUS_PENDING = 0;

    @Autowired
    private VolActivitySignupMapper signupMapper;

    @Override
    public VolActivitySignup selectVolActivitySignupById(Long id)
    {
        return signupMapper.selectVolActivitySignupById(id);
    }

    @Override
    public VolActivitySignup selectVolActivitySignupByActivityIdAndVolunteerUserId(Long activityId, Long volunteerUserId)
    {
        return signupMapper.selectVolActivitySignupByActivityIdAndVolunteerUserId(activityId, volunteerUserId);
    }

    @Override
    public List<VolActivitySignup> selectVolActivitySignupList(VolActivitySignup signup)
    {
        return signupMapper.selectVolActivitySignupList(signup == null ? new VolActivitySignup() : signup);
    }

    @Override
    public int countApprovedSignupByActivityId(Long activityId)
    {
        return signupMapper.countApprovedSignupByActivityId(activityId);
    }

    @Override
    public int insertVolActivitySignup(VolActivitySignup signup)
    {
        if (signup.getStatus() == null)
        {
            signup.setStatus(STATUS_PENDING);
        }
        signup.setCreateTime(new Date());
        return signupMapper.insertVolActivitySignup(signup);
    }

    @Override
    public int updateVolActivitySignup(VolActivitySignup signup)
    {
        signup.setUpdateTime(new Date());
        return signupMapper.updateVolActivitySignup(signup);
    }

    @Override
    public int deleteVolActivitySignupById(Long id)
    {
        return signupMapper.deleteVolActivitySignupById(id);
    }

    @Override
    public int deleteVolActivitySignupByIds(Long[] ids)
    {
        return signupMapper.deleteVolActivitySignupByIds(ids);
    }
}
