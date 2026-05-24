package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.voluntary.domain.VolAuditRecord;
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

    VolVolunteerProfile updateVolunteerProfileByManager(Long profileId, String username, VolVolunteerProfile profile);

    VolVolunteerProfile auditVolunteerProfile(Long profileId, Integer auditStatus, String auditReason, Long auditorId,
            String auditorName);

    VolVolunteerProfile changeVolunteerProfileStatus(Long profileId, Integer auditStatus, String auditReason,
            Long auditorId, String auditorName);

    List<VolAuditRecord> selectVolunteerAuditRecords(Long profileId);

    void createPendingProfileForRegisteredUser(SysUser user);

    boolean isVolunteerApproved(Long userId);
}
