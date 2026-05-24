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

    int countApprovedSignupByActivityId(Long activityId);

    int insertVolActivitySignup(VolActivitySignup signup);

    int updateVolActivitySignup(VolActivitySignup signup);

    int deleteVolActivitySignupById(Long id);

    int deleteVolActivitySignupByIds(Long[] ids);
}
