package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
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

    public static final Integer STATUS_PUBLISHED = 1;

    public static final Integer STATUS_ENDED = 2;

    public static final Integer STATUS_OFFLINE = 3;

    public static final Integer STATUS_CANCELLED = 4;

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
    @Transactional
    public VolActivity createVolActivityByManager(String username, VolActivity activity)
    {
        validateActivityForSave(activity);
        activity.setStatus(STATUS_DRAFT);
        activity.setApprovedCount(0);
        activity.setCreateBy(username);
        activity.setCreateTime(new Date());
        if (activityMapper.insertVolActivity(activity) <= 0 || activity.getId() == null)
        {
            throw new ServiceException("活动创建失败");
        }
        return activityMapper.selectVolActivityById(activity.getId());
    }

    @Override
    @Transactional
    public VolActivity updateVolActivityByManager(Long id, String username, VolActivity activity)
    {
        VolActivity existedActivity = requireActivity(id);
        if (!STATUS_DRAFT.equals(existedActivity.getStatus()) && !STATUS_OFFLINE.equals(existedActivity.getStatus()))
        {
            throw new ServiceException("只有草稿或已下架活动允许编辑");
        }

        validateActivityForSave(activity);
        if (existedActivity.getApprovedCount() != null && existedActivity.getApprovedCount() > activity.getRecruitCount())
        {
            throw new ServiceException("招募人数不能小于已通过报名人数");
        }
        VolActivity updateActivity = buildManagerUpdateActivity(id, username, activity);
        if (activityMapper.updateVolActivity(updateActivity) <= 0)
        {
            throw new ServiceException("活动修改失败");
        }
        return activityMapper.selectVolActivityById(id);
    }

    @Override
    @Transactional
    public VolActivity changeVolActivityStatus(Long id, Integer status, String reason, String username)
    {
        if (status == null)
        {
            throw new ServiceException("活动状态不能为空");
        }
        VolActivity existedActivity = requireActivity(id);
        if (status.equals(existedActivity.getStatus()))
        {
            return existedActivity;
        }

        validateStatusTransition(existedActivity, status);
        if (STATUS_PUBLISHED.equals(status))
        {
            validateActivityForPublish(existedActivity);
        }

        VolActivity updateActivity = new VolActivity();
        updateActivity.setId(id);
        updateActivity.setStatus(status);
        updateActivity.setUpdateBy(username);
        updateActivity.setUpdateTime(new Date());
        if (STATUS_PUBLISHED.equals(status))
        {
            updateActivity.setPublishTime(new Date());
        }
        if (StringUtils.isNotEmpty(reason))
        {
            updateActivity.setRemark(reason);
        }

        if (activityMapper.updateVolActivity(updateActivity) <= 0)
        {
            throw new ServiceException("活动状态修改失败");
        }
        return activityMapper.selectVolActivityById(id);
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

    private VolActivity requireActivity(Long id)
    {
        if (id == null)
        {
            throw new ServiceException("活动ID不能为空");
        }
        VolActivity activity = activityMapper.selectVolActivityById(id);
        if (activity == null)
        {
            throw new ServiceException("活动不存在");
        }
        return activity;
    }

    private VolActivity buildManagerUpdateActivity(Long id, String username, VolActivity input)
    {
        VolActivity updateActivity = new VolActivity();
        updateActivity.setId(id);
        updateActivity.setTitle(input.getTitle());
        updateActivity.setActivityType(input.getActivityType());
        updateActivity.setCoverUrl(input.getCoverUrl());
        updateActivity.setServiceLocation(input.getServiceLocation());
        updateActivity.setStartTime(input.getStartTime());
        updateActivity.setEndTime(input.getEndTime());
        updateActivity.setSignupStartTime(input.getSignupStartTime());
        updateActivity.setSignupEndTime(input.getSignupEndTime());
        updateActivity.setRecruitCount(input.getRecruitCount());
        updateActivity.setServiceTarget(input.getServiceTarget());
        updateActivity.setContent(input.getContent());
        updateActivity.setRequirements(input.getRequirements());
        updateActivity.setManagerName(input.getManagerName());
        updateActivity.setManagerPhone(input.getManagerPhone());
        updateActivity.setMaxServiceMinutes(input.getMaxServiceMinutes());
        updateActivity.setRemark(input.getRemark());
        updateActivity.setUpdateBy(username);
        updateActivity.setUpdateTime(new Date());
        return updateActivity;
    }

    private void validateActivityForSave(VolActivity activity)
    {
        if (activity == null)
        {
            throw new ServiceException("活动信息不能为空");
        }
        if (StringUtils.isEmpty(activity.getTitle()))
        {
            throw new ServiceException("活动标题不能为空");
        }
        if (StringUtils.isEmpty(activity.getActivityType()))
        {
            throw new ServiceException("活动类型不能为空");
        }
        if (StringUtils.isEmpty(activity.getServiceLocation()))
        {
            throw new ServiceException("服务地点不能为空");
        }
        if (activity.getStartTime() == null || activity.getEndTime() == null)
        {
            throw new ServiceException("活动时间不能为空");
        }
        if (!activity.getStartTime().before(activity.getEndTime()))
        {
            throw new ServiceException("活动开始时间必须早于结束时间");
        }
        if (activity.getSignupStartTime() == null || activity.getSignupEndTime() == null)
        {
            throw new ServiceException("报名时间不能为空");
        }
        if (!activity.getSignupStartTime().before(activity.getSignupEndTime()))
        {
            throw new ServiceException("报名开始时间必须早于截止时间");
        }
        if (activity.getSignupEndTime().after(activity.getStartTime()))
        {
            throw new ServiceException("报名截止时间不能晚于活动开始时间");
        }
        if (activity.getRecruitCount() == null || activity.getRecruitCount() <= 0)
        {
            throw new ServiceException("招募人数必须大于0");
        }
        if (activity.getMaxServiceMinutes() != null && activity.getMaxServiceMinutes() <= 0)
        {
            throw new ServiceException("最大可计入服务分钟数必须大于0");
        }
    }

    private void validateActivityForPublish(VolActivity activity)
    {
        validateActivityForSave(activity);
        if (activity.getApprovedCount() != null && activity.getApprovedCount() > activity.getRecruitCount())
        {
            throw new ServiceException("已通过报名人数不能大于招募人数");
        }
    }

    private void validateStatusTransition(VolActivity activity, Integer targetStatus)
    {
        Integer currentStatus = activity.getStatus();
        if (STATUS_ENDED.equals(currentStatus) || STATUS_CANCELLED.equals(currentStatus))
        {
            throw new ServiceException("已结束或已取消活动不能再变更状态");
        }
        if (STATUS_PUBLISHED.equals(targetStatus))
        {
            if (!STATUS_DRAFT.equals(currentStatus) && !STATUS_OFFLINE.equals(currentStatus))
            {
                throw new ServiceException("只有草稿或已下架活动可以发布");
            }
            return;
        }
        if (STATUS_OFFLINE.equals(targetStatus))
        {
            if (!STATUS_PUBLISHED.equals(currentStatus))
            {
                throw new ServiceException("只有已发布活动可以下架");
            }
            return;
        }
        if (STATUS_ENDED.equals(targetStatus))
        {
            if (!STATUS_PUBLISHED.equals(currentStatus))
            {
                throw new ServiceException("只有已发布活动可以结束");
            }
            return;
        }
        if (STATUS_CANCELLED.equals(targetStatus))
        {
            if (!STATUS_DRAFT.equals(currentStatus) && !STATUS_PUBLISHED.equals(currentStatus)
                    && !STATUS_OFFLINE.equals(currentStatus))
            {
                throw new ServiceException("当前活动状态不允许取消");
            }
            return;
        }
        throw new ServiceException("不支持的活动状态");
    }
}
