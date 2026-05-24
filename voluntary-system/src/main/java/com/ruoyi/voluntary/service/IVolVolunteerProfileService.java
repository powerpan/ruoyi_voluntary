package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;

/**
 * 志愿者档案 Service
 */
public interface IVolVolunteerProfileService
{
    VolVolunteerProfile selectVolVolunteerProfileById(Long id);

    VolVolunteerProfile selectVolVolunteerProfileByUserId(Long userId);

    List<VolVolunteerProfile> selectVolVolunteerProfileList(VolVolunteerProfile profile);

    int insertVolVolunteerProfile(VolVolunteerProfile profile);

    int updateVolVolunteerProfile(VolVolunteerProfile profile);

    VolVolunteerProfile updateMyVolunteerProfile(Long userId, String username, VolVolunteerProfile profile);

    void createPendingProfileForRegisteredUser(SysUser user);

    boolean isVolunteerApproved(Long userId);
}
