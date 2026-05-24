package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.mapper.VolActivityMapper;
import com.ruoyi.voluntary.service.IVolActivityService;

/**
 * 志愿活动 Service 实现
 */
@Service
public class VolActivityServiceImpl implements IVolActivityService
{
    public static final Integer STATUS_DRAFT = 0;

    @Autowired
    private VolActivityMapper activityMapper;

    @Override
    public VolActivity selectVolActivityById(Long id)
    {
        return activityMapper.selectVolActivityById(id);
    }

    @Override
    public List<VolActivity> selectVolActivityList(VolActivity activity)
    {
        return activityMapper.selectVolActivityList(activity == null ? new VolActivity() : activity);
    }

    @Override
    public int insertVolActivity(VolActivity activity)
    {
        Date now = new Date();
        if (activity.getStatus() == null)
        {
            activity.setStatus(STATUS_DRAFT);
        }
        if (activity.getApprovedCount() == null)
        {
            activity.setApprovedCount(0);
        }
        activity.setCreateTime(now);
        return activityMapper.insertVolActivity(activity);
    }

    @Override
    public int updateVolActivity(VolActivity activity)
    {
        activity.setUpdateTime(new Date());
        return activityMapper.updateVolActivity(activity);
    }

    @Override
    public int deleteVolActivityById(Long id)
    {
        return activityMapper.deleteVolActivityById(id);
    }

    @Override
    public int deleteVolActivityByIds(Long[] ids)
    {
        return activityMapper.deleteVolActivityByIds(ids);
    }
}
