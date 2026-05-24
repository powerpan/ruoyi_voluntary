package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.voluntary.domain.VolActivitySignup;

/**
 * 活动报名 Service
 */
public interface IVolActivitySignupService
{
    VolActivitySignup selectVolActivitySignupById(Long id);

    VolActivitySignup selectVolActivitySignupByActivityIdAndVolunteerUserId(Long activityId, Long volunteerUserId);

    List<VolActivitySignup> selectVolActivitySignupList(VolActivitySignup signup);

    List<VolActivitySignup> selectMyActivitySignupList(Long userId, VolActivitySignup signup);

    int countApprovedSignupByActivityId(Long activityId);

    int insertVolActivitySignup(VolActivitySignup signup);

    int updateVolActivitySignup(VolActivitySignup signup);

    VolActivitySignup applyForActivity(Long activityId, Long userId, String username, String applyReason, String experience);

    VolActivitySignup cancelMyActivitySignup(Long signupId, Long userId, String username);

    VolActivitySignup reviewActivitySignup(Long signupId, Integer status, String reviewReason, Long reviewerId,
            String reviewerName);

    int deleteVolActivitySignupById(Long id);

    int deleteVolActivitySignupByIds(Long[] ids);
}
