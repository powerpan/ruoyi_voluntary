package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.domain.VolActivitySignup;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;
import com.ruoyi.voluntary.mapper.VolActivityMapper;
import com.ruoyi.voluntary.mapper.VolActivitySignupMapper;
import com.ruoyi.voluntary.service.IVolActivitySignupService;
import com.ruoyi.voluntary.service.IVolNotificationService;
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

    private static final String NOTICE_TYPE_SIGNUP_REVIEW = "signup_review";

    private static final String TARGET_TYPE_SIGNUP = "signup";

    @Autowired
    private VolActivitySignupMapper signupMapper;

    @Autowired
    private VolActivityMapper activityMapper;

    @Autowired
    private IVolVolunteerProfileService volunteerProfileService;

    @Autowired
    private IVolNotificationService notificationService;

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
    @Transactional
    public VolActivitySignup reviewActivitySignup(Long signupId, Integer status, String reviewReason, Long reviewerId,
            String reviewerName)
    {
        validateReviewInput(signupId, status);
        VolActivitySignup signup = requireSignup(signupId);
        validateReviewCurrentStatus(signup);

        Integer approvedCountAfterReview = null;
        if (STATUS_APPROVED.equals(status))
        {
            approvedCountAfterReview = calculateApprovedCountAfterReview(signup.getActivityId());
        }

        VolActivitySignup updateSignup = new VolActivitySignup();
        updateSignup.setId(signup.getId());
        updateSignup.setStatus(status);
        updateSignup.setReviewReason(reviewReason == null ? "" : reviewReason);
        updateSignup.setReviewerId(reviewerId);
        updateSignup.setReviewerName(reviewerName);
        updateSignup.setReviewTime(new Date());
        updateSignup.setUpdateBy(reviewerName);
        updateSignup.setUpdateTime(new Date());
        if (signupMapper.updateVolActivitySignup(updateSignup) <= 0)
        {
            throw new ServiceException("报名筛选失败");
        }

        if (STATUS_APPROVED.equals(status))
        {
            updateActivityApprovedCount(signup.getActivityId(), approvedCountAfterReview);
        }
        VolActivitySignup reviewedSignup = signupMapper.selectVolActivitySignupById(signup.getId());
        sendSignupReviewNotification(reviewedSignup, status, reviewReason, reviewerId, reviewerName);
        return reviewedSignup;
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

    private VolActivitySignup requireSignup(Long signupId)
    {
        VolActivitySignup signup = signupMapper.selectVolActivitySignupById(signupId);
        if (signup == null)
        {
            throw new ServiceException("报名记录不存在");
        }
        return signup;
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

    private void validateReviewInput(Long signupId, Integer status)
    {
        if (signupId == null)
        {
            throw new ServiceException("报名ID不能为空");
        }
        if (status == null)
        {
            throw new ServiceException("筛选状态不能为空");
        }
        if (!STATUS_APPROVED.equals(status) && !STATUS_REJECTED.equals(status) && !STATUS_WAITLIST.equals(status))
        {
            throw new ServiceException("筛选状态只能为通过、拒绝或候补");
        }
    }

    private void validateReviewCurrentStatus(VolActivitySignup signup)
    {
        if (STATUS_CANCELLED.equals(signup.getStatus()))
        {
            throw new ServiceException("已取消报名不能筛选");
        }
        if (STATUS_APPROVED.equals(signup.getStatus()))
        {
            throw new ServiceException("已通过报名不能重复筛选");
        }
        if (STATUS_REJECTED.equals(signup.getStatus()))
        {
            throw new ServiceException("已拒绝报名不能再次筛选");
        }
        if (!STATUS_PENDING.equals(signup.getStatus()) && !STATUS_WAITLIST.equals(signup.getStatus()))
        {
            throw new ServiceException("只有待筛选或候补报名允许筛选");
        }
    }

    private Integer calculateApprovedCountAfterReview(Long activityId)
    {
        VolActivity activity = activityMapper.selectVolActivityById(activityId);
        if (activity == null)
        {
            throw new ServiceException("活动不存在");
        }
        Integer recruitCount = activity.getRecruitCount();
        if (recruitCount == null || recruitCount <= 0)
        {
            throw new ServiceException("活动招募人数配置异常");
        }
        int approvedCount = signupMapper.countApprovedSignupByActivityId(activityId);
        if (approvedCount >= recruitCount)
        {
            throw new ServiceException("活动通过人数已达到招募上限");
        }
        return approvedCount + 1;
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

    private void updateActivityApprovedCount(Long activityId, Integer approvedCount)
    {
        VolActivity updateActivity = new VolActivity();
        updateActivity.setId(activityId);
        updateActivity.setApprovedCount(approvedCount);
        updateActivity.setUpdateTime(new Date());
        if (activityMapper.updateVolActivity(updateActivity) <= 0)
        {
            throw new ServiceException("活动已通过人数更新失败");
        }
    }

    private void sendSignupReviewNotification(VolActivitySignup signup, Integer status, String reviewReason,
            Long reviewerId, String reviewerName)
    {
        notificationService.sendBusinessNotification(signup.getVolunteerUserId(), reviewerId, NOTICE_TYPE_SIGNUP_REVIEW,
                TARGET_TYPE_SIGNUP, signup.getId(), resolveSignupReviewTitle(status, signup),
                resolveSignupReviewContent(status, signup, reviewReason), "/signups", reviewerName);
    }

    private String resolveSignupReviewTitle(Integer status, VolActivitySignup signup)
    {
        String activityTitle = StringUtils.defaultIfEmpty(signup.getActivityTitle(), "志愿活动");
        if (STATUS_APPROVED.equals(status))
        {
            return "报名已通过：" + activityTitle;
        }
        if (STATUS_REJECTED.equals(status))
        {
            return "报名未通过：" + activityTitle;
        }
        return "报名已候补：" + activityTitle;
    }

    private String resolveSignupReviewContent(Integer status, VolActivitySignup signup, String reviewReason)
    {
        String activityTitle = StringUtils.defaultIfEmpty(signup.getActivityTitle(), "该活动");
        String reason = StringUtils.isBlank(reviewReason) ? "" : " 筛选意见：" + reviewReason;
        if (STATUS_APPROVED.equals(status))
        {
            return "你报名的“" + activityTitle + "”已通过筛选，请按活动要求准时参与并完成签到签退。" + reason;
        }
        if (STATUS_REJECTED.equals(status))
        {
            return "你报名的“" + activityTitle + "”未通过筛选，可继续关注其他志愿活动。" + reason;
        }
        return "你报名的“" + activityTitle + "”已进入候补，请等待管理员后续通知。" + reason;
    }
}
