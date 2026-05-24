package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.domain.VolActivitySignup;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;
import com.ruoyi.voluntary.mapper.VolActivityMapper;
import com.ruoyi.voluntary.mapper.VolActivitySignupMapper;
import com.ruoyi.voluntary.service.IVolActivitySignupService;
import com.ruoyi.voluntary.service.IVolVolunteerProfileService;

/**
 * 活动报名 Service 实现
 */
@Service
public class VolActivitySignupServiceImpl implements IVolActivitySignupService
{
    public static final Integer STATUS_PENDING = 0;

    public static final Integer STATUS_APPROVED = 1;

    public static final Integer STATUS_REJECTED = 2;

    public static final Integer STATUS_WAITLIST = 3;

    public static final Integer STATUS_CANCELLED = 4;

    @Autowired
    private VolActivitySignupMapper signupMapper;

    @Autowired
    private VolActivityMapper activityMapper;

    @Autowired
    private IVolVolunteerProfileService volunteerProfileService;

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
    public List<VolActivitySignup> selectMyActivitySignupList(Long userId, VolActivitySignup signup)
    {
        if (userId == null)
        {
            throw new ServiceException("用户ID不能为空");
        }
        VolActivitySignup query = signup == null ? new VolActivitySignup() : signup;
        query.setVolunteerUserId(userId);
        return signupMapper.selectVolActivitySignupList(query);
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
    @Transactional
    public VolActivitySignup applyForActivity(Long activityId, Long userId, String username, String applyReason, String experience)
    {
        VolVolunteerProfile profile = requireApprovedProfile(userId);
        VolActivity activity = requirePublishedActivityInSignupTime(activityId);

        VolActivitySignup existedSignup = signupMapper.selectVolActivitySignupByActivityIdAndVolunteerUserId(activity.getId(),
                userId);
        if (existedSignup != null)
        {
            return handleExistingSignup(existedSignup, profile, username, applyReason, experience);
        }

        VolActivitySignup signup = buildPendingSignup(activity.getId(), profile, username, applyReason, experience);
        if (signupMapper.insertVolActivitySignup(signup) <= 0 || signup.getId() == null)
        {
            throw new ServiceException("活动报名失败");
        }
        return signupMapper.selectVolActivitySignupById(signup.getId());
    }

    @Override
    @Transactional
    public VolActivitySignup cancelMyActivitySignup(Long signupId, Long userId, String username)
    {
        if (userId == null)
        {
            throw new ServiceException("用户ID不能为空");
        }
        if (signupId == null)
        {
            throw new ServiceException("报名ID不能为空");
        }
        VolActivitySignup signup = signupMapper.selectVolActivitySignupById(signupId);
        if (signup == null)
        {
            throw new ServiceException("报名记录不存在");
        }
        if (!userId.equals(signup.getVolunteerUserId()))
        {
            throw new ServiceException("不能操作他人的报名记录");
        }
        if (STATUS_CANCELLED.equals(signup.getStatus()))
        {
            return signup;
        }
        if (STATUS_REJECTED.equals(signup.getStatus()))
        {
            throw new ServiceException("已拒绝报名不能取消");
        }

        VolActivitySignup updateSignup = new VolActivitySignup();
        updateSignup.setId(signup.getId());
        updateSignup.setStatus(STATUS_CANCELLED);
        updateSignup.setUpdateBy(username);
        updateSignup.setUpdateTime(new Date());
        updateSignup.setRemark("志愿者取消报名");
        if (signupMapper.updateVolActivitySignup(updateSignup) <= 0)
        {
            throw new ServiceException("取消报名失败");
        }

        if (STATUS_APPROVED.equals(signup.getStatus()))
        {
            decreaseActivityApprovedCount(signup.getActivityId());
        }
        return signupMapper.selectVolActivitySignupById(signup.getId());
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

    private VolVolunteerProfile requireApprovedProfile(Long userId)
    {
        if (userId == null)
        {
            throw new ServiceException("用户ID不能为空");
        }
        VolVolunteerProfile profile = volunteerProfileService.selectVolVolunteerProfileByUserId(userId);
        if (profile == null)
        {
            throw new ServiceException("志愿者档案不存在，请先完善资料");
        }
        if (!volunteerProfileService.isVolunteerApproved(userId))
        {
            throw new ServiceException("志愿者档案未审核通过，不能报名活动");
        }
        return profile;
    }

    private VolActivity requirePublishedActivityInSignupTime(Long activityId)
    {
        if (activityId == null)
        {
            throw new ServiceException("活动ID不能为空");
        }
        VolActivity activity = activityMapper.selectVolActivityById(activityId);
        if (activity == null || !VolActivityServiceImpl.STATUS_PUBLISHED.equals(activity.getStatus()))
        {
            throw new ServiceException("活动不存在或未发布");
        }
        Date now = new Date();
        if (activity.getSignupStartTime() == null || activity.getSignupEndTime() == null)
        {
            throw new ServiceException("活动报名时间未配置");
        }
        if (now.before(activity.getSignupStartTime()))
        {
            throw new ServiceException("活动报名尚未开始");
        }
        if (now.after(activity.getSignupEndTime()))
        {
            throw new ServiceException("活动报名已截止");
        }
        return activity;
    }

    private VolActivitySignup handleExistingSignup(VolActivitySignup existedSignup, VolVolunteerProfile profile,
            String username, String applyReason, String experience)
    {
        if (STATUS_PENDING.equals(existedSignup.getStatus()) || STATUS_APPROVED.equals(existedSignup.getStatus())
                || STATUS_WAITLIST.equals(existedSignup.getStatus()))
        {
            throw new ServiceException("已提交过该活动报名，请勿重复报名");
        }
        if (STATUS_REJECTED.equals(existedSignup.getStatus()))
        {
            throw new ServiceException("该活动报名已被拒绝，如需再次报名请联系管理员");
        }
        if (!STATUS_CANCELLED.equals(existedSignup.getStatus()))
        {
            throw new ServiceException("当前报名状态不能再次报名");
        }

        VolActivitySignup updateSignup = buildPendingSignup(existedSignup.getActivityId(), profile, username, applyReason,
                experience);
        updateSignup.setId(existedSignup.getId());
        updateSignup.setUpdateBy(username);
        updateSignup.setUpdateTime(updateSignup.getCreateTime());
        updateSignup.setRemark("取消后重新报名");
        if (signupMapper.resubmitCancelledSignup(updateSignup) <= 0)
        {
            throw new ServiceException("重新报名失败");
        }
        return signupMapper.selectVolActivitySignupById(existedSignup.getId());
    }

    private VolActivitySignup buildPendingSignup(Long activityId, VolVolunteerProfile profile, String username,
            String applyReason, String experience)
    {
        VolActivitySignup signup = new VolActivitySignup();
        signup.setActivityId(activityId);
        signup.setVolunteerUserId(profile.getUserId());
        signup.setRealName(profile.getRealName());
        signup.setPhone(profile.getPhone());
        signup.setOrganization(profile.getOrganization());
        signup.setApplyReason(applyReason);
        signup.setExperience(experience);
        signup.setStatus(STATUS_PENDING);
        signup.setCreateBy(username);
        signup.setCreateTime(new Date());
        return signup;
    }

    private void decreaseActivityApprovedCount(Long activityId)
    {
        VolActivity activity = activityMapper.selectVolActivityById(activityId);
        if (activity == null)
        {
            return;
        }
        VolActivity updateActivity = new VolActivity();
        updateActivity.setId(activityId);
        int approvedCount = activity.getApprovedCount() == null ? 0 : activity.getApprovedCount();
        updateActivity.setApprovedCount(Math.max(approvedCount - 1, 0));
        updateActivity.setUpdateTime(new Date());
        activityMapper.updateVolActivity(updateActivity);
    }
}
