package com.ruoyi.voluntary.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voluntary.domain.VolActivitySignup;

/**
 * 活动报名 Mapper
 */
public interface VolActivitySignupMapper
{
    VolActivitySignup selectVolActivitySignupById(Long id);

    VolActivitySignup selectVolActivitySignupByActivityIdAndVolunteerUserId(@Param("activityId") Long activityId,
            @Param("volunteerUserId") Long volunteerUserId);

    List<VolActivitySignup> selectVolActivitySignupList(VolActivitySignup signup);

    int countApprovedSignupByActivityId(Long activityId);

    int insertVolActivitySignup(VolActivitySignup signup);

    int updateVolActivitySignup(VolActivitySignup signup);

    int deleteVolActivitySignupById(Long id);

    int deleteVolActivitySignupByIds(Long[] ids);
}
