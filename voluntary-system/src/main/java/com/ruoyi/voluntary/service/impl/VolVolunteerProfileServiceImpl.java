package com.ruoyi.voluntary.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.RegisterUserPostProcessor;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.voluntary.domain.VolAuditRecord;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;
import com.ruoyi.voluntary.mapper.VolAuditRecordMapper;
import com.ruoyi.voluntary.mapper.VolVolunteerProfileMapper;
import com.ruoyi.voluntary.service.IVolNotificationService;
import com.ruoyi.voluntary.service.IVolVolunteerProfileService;

/**
 * 志愿者档案 Service 实现
 */
@Service
public class VolVolunteerProfileServiceImpl implements IVolVolunteerProfileService, RegisterUserPostProcessor
{
    public static final String ROLE_KEY_VOLUNTEER = "volunteer";

    public static final String TARGET_TYPE_VOLUNTEER = "volunteer";

    public static final Integer AUDIT_STATUS_PENDING = 0;

    public static final Integer AUDIT_STATUS_APPROVED = 1;

    public static final Integer AUDIT_STATUS_REJECTED = 2;

    public static final Integer AUDIT_STATUS_DISABLED = 3;

    private static final String REGISTER_AUDIT_REASON = "志愿者注册后进入待审核状态";

    private static final String PROFILE_UPDATE_AUDIT_REASON = "志愿者修改资料后进入待审核状态";

    private static final String MANAGER_APPROVE_REASON = "管理员审核通过";

    private static final String MANAGER_DISABLE_REASON = "管理员禁用志愿者档案";

    private static final String MANAGER_ENABLE_REASON = "管理员启用志愿者档案";

    private static final String NOTICE_TYPE_VOLUNTEER_AUDIT = "volunteer_audit";

    @Autowired
    private VolVolunteerProfileMapper volunteerProfileMapper;

    @Autowired
    private VolAuditRecordMapper auditRecordMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private IVolNotificationService notificationService;

    @Override
    public VolVolunteerProfile selectVolVolunteerProfileById(Long id)
    {
        return volunteerProfileMapper.selectVolVolunteerProfileById(id);
    }

    @Override
    public VolVolunteerProfile selectVolVolunteerProfileByUserId(Long userId)
    {
        return volunteerProfileMapper.selectVolVolunteerProfileByUserId(userId);
    }

    @Override
    public List<VolVolunteerProfile> selectVolVolunteerProfileList(VolVolunteerProfile profile)
    {
        return volunteerProfileMapper.selectVolVolunteerProfileList(profile);
    }

    @Override
    public int insertVolVolunteerProfile(VolVolunteerProfile profile)
    {
        profile.setCreateTime(new Date());
        return volunteerProfileMapper.insertVolVolunteerProfile(profile);
    }

    @Override
    public int updateVolVolunteerProfile(VolVolunteerProfile profile)
    {
        profile.setUpdateTime(new Date());
        return volunteerProfileMapper.updateVolVolunteerProfile(profile);
    }

    @Override
    @Transactional
    public VolVolunteerProfile updateMyVolunteerProfile(Long userId, String username, VolVolunteerProfile profile)
    {
        VolVolunteerProfile existedProfile = volunteerProfileMapper.selectVolVolunteerProfileByUserId(userId);
        if (existedProfile == null)
        {
            throw new ServiceException("志愿者档案不存在，请重新注册或联系管理员");
        }
        if (AUDIT_STATUS_DISABLED.equals(existedProfile.getAuditStatus()))
        {
            throw new ServiceException("志愿者档案已禁用，不能修改资料");
        }

        VolVolunteerProfile updateProfile = buildUserUpdateProfile(existedProfile, profile, username);
        boolean shouldCreateAuditRecord = shouldCreateProfileUpdateAuditRecord(existedProfile, updateProfile);

        if (volunteerProfileMapper.updateVolVolunteerProfile(updateProfile) <= 0)
        {
            throw new ServiceException("志愿者档案修改失败");
        }
        if (shouldCreateAuditRecord)
        {
            VolAuditRecord auditRecord = buildProfileUpdateAuditRecord(existedProfile, username);
            if (auditRecordMapper.insertVolAuditRecord(auditRecord) <= 0)
            {
                throw new ServiceException("志愿者档案修改记录创建失败");
            }
        }
        return volunteerProfileMapper.selectVolVolunteerProfileByUserId(userId);
    }

    @Override
    @Transactional
    public VolVolunteerProfile updateVolunteerProfileByManager(Long profileId, String username, VolVolunteerProfile profile)
    {
        VolVolunteerProfile existedProfile = requireProfile(profileId);
        VolVolunteerProfile updateProfile = buildManagerUpdateProfile(existedProfile, profile, username);
        if (volunteerProfileMapper.updateVolVolunteerProfile(updateProfile) <= 0)
        {
            throw new ServiceException("志愿者档案修改失败");
        }
        return volunteerProfileMapper.selectVolVolunteerProfileById(profileId);
    }

    @Override
    @Transactional
    public VolVolunteerProfile auditVolunteerProfile(Long profileId, Integer auditStatus, String auditReason, Long auditorId,
            String auditorName)
    {
        if (!AUDIT_STATUS_APPROVED.equals(auditStatus) && !AUDIT_STATUS_REJECTED.equals(auditStatus))
        {
            throw new ServiceException("审核状态只能为通过或驳回");
        }
        if (AUDIT_STATUS_REJECTED.equals(auditStatus) && StringUtils.isBlank(auditReason))
        {
            throw new ServiceException("驳回时必须填写审核意见");
        }
        return updateAuditStatus(profileId, auditStatus,
                StringUtils.isBlank(auditReason) ? MANAGER_APPROVE_REASON : auditReason,
                auditorId, auditorName);
    }

    @Override
    @Transactional
    public VolVolunteerProfile changeVolunteerProfileStatus(Long profileId, Integer auditStatus, String auditReason,
            Long auditorId, String auditorName)
    {
        if (!AUDIT_STATUS_APPROVED.equals(auditStatus) && !AUDIT_STATUS_DISABLED.equals(auditStatus))
        {
            throw new ServiceException("状态只能设置为启用或禁用");
        }
        String defaultReason = AUDIT_STATUS_DISABLED.equals(auditStatus) ? MANAGER_DISABLE_REASON : MANAGER_ENABLE_REASON;
        return updateAuditStatus(profileId, auditStatus,
                StringUtils.isBlank(auditReason) ? defaultReason : auditReason,
                auditorId, auditorName);
    }

    @Override
    public List<VolAuditRecord> selectVolunteerAuditRecords(Long profileId)
    {
        VolVolunteerProfile profile = requireProfile(profileId);
        VolAuditRecord query = new VolAuditRecord();
        query.setTargetType(TARGET_TYPE_VOLUNTEER);
        query.setTargetId(profile.getId());
        return auditRecordMapper.selectVolAuditRecordList(query);
    }

    @Override
    @Transactional
    public void createPendingProfileForRegisteredUser(SysUser user)
    {
        if (user == null || user.getUserId() == null)
        {
            throw new ServiceException("注册用户信息缺失，无法创建志愿者档案");
        }
        VolVolunteerProfile existedProfile = volunteerProfileMapper.selectVolVolunteerProfileByUserId(user.getUserId());
        if (existedProfile != null)
        {
            return;
        }

        bindVolunteerRole(user.getUserId());

        VolVolunteerProfile profile = buildPendingProfile(user);
        if (volunteerProfileMapper.insertVolVolunteerProfile(profile) <= 0 || profile.getId() == null)
        {
            throw new ServiceException("志愿者档案创建失败");
        }

        VolAuditRecord auditRecord = buildRegisterAuditRecord(user, profile.getId());
        if (auditRecordMapper.insertVolAuditRecord(auditRecord) <= 0)
        {
            throw new ServiceException("志愿者审核记录创建失败");
        }
    }

    @Override
    public boolean isVolunteerApproved(Long userId)
    {
        VolVolunteerProfile profile = volunteerProfileMapper.selectVolVolunteerProfileByUserId(userId);
        return profile != null && AUDIT_STATUS_APPROVED.equals(profile.getAuditStatus());
    }

    @Override
    public void afterRegister(SysUser user)
    {
        createPendingProfileForRegisteredUser(user);
    }

    private void bindVolunteerRole(Long userId)
    {
        SysRole volunteerRole = roleMapper.checkRoleKeyUnique(ROLE_KEY_VOLUNTEER);
        if (volunteerRole == null || volunteerRole.getRoleId() == null)
        {
            throw new ServiceException("志愿者角色未初始化，请先导入系统初始化 SQL");
        }

        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(volunteerRole.getRoleId());
        userRoleMapper.batchUserRole(Collections.singletonList(userRole));
    }

    private VolVolunteerProfile buildPendingProfile(SysUser user)
    {
        Date now = new Date();
        VolVolunteerProfile profile = new VolVolunteerProfile();
        profile.setUserId(user.getUserId());
        profile.setRealName("");
        profile.setGender(StringUtils.isEmpty(user.getSex()) ? "2" : user.getSex());
        profile.setPhone(StringUtils.isEmpty(user.getPhonenumber()) ? "" : user.getPhonenumber());
        profile.setAuditStatus(AUDIT_STATUS_PENDING);
        profile.setAuditReason("");
        profile.setTotalServiceMinutes(0);
        profile.setServiceCount(0);
        profile.setCreateBy(user.getUserName());
        profile.setCreateTime(now);
        return profile;
    }

    private VolVolunteerProfile buildUserUpdateProfile(VolVolunteerProfile existedProfile, VolVolunteerProfile input, String username)
    {
        VolVolunteerProfile updateProfile = new VolVolunteerProfile();
        updateProfile.setId(existedProfile.getId());
        updateProfile.setUserId(existedProfile.getUserId());
        updateProfile.setRealName(input.getRealName());
        updateProfile.setGender(input.getGender());
        updateProfile.setIdCard(input.getIdCard());
        updateProfile.setPhone(input.getPhone());
        updateProfile.setOrganization(input.getOrganization());
        updateProfile.setMajorOrClass(input.getMajorOrClass());
        updateProfile.setSpecialty(input.getSpecialty());
        updateProfile.setEmergencyContact(input.getEmergencyContact());
        updateProfile.setEmergencyPhone(input.getEmergencyPhone());
        updateProfile.setRemark(input.getRemark());
        updateProfile.setUpdateBy(username);
        updateProfile.setUpdateTime(new Date());

        if (shouldResetToPending(existedProfile, input))
        {
            updateProfile.setAuditStatus(AUDIT_STATUS_PENDING);
            updateProfile.setAuditReason("");
        }
        return updateProfile;
    }

    private VolVolunteerProfile buildManagerUpdateProfile(VolVolunteerProfile existedProfile, VolVolunteerProfile input, String username)
    {
        VolVolunteerProfile updateProfile = new VolVolunteerProfile();
        updateProfile.setId(existedProfile.getId());
        updateProfile.setUserId(existedProfile.getUserId());
        updateProfile.setRealName(input.getRealName());
        updateProfile.setGender(input.getGender());
        updateProfile.setIdCard(input.getIdCard());
        updateProfile.setPhone(input.getPhone());
        updateProfile.setOrganization(input.getOrganization());
        updateProfile.setMajorOrClass(input.getMajorOrClass());
        updateProfile.setSpecialty(input.getSpecialty());
        updateProfile.setEmergencyContact(input.getEmergencyContact());
        updateProfile.setEmergencyPhone(input.getEmergencyPhone());
        updateProfile.setRemark(input.getRemark());
        updateProfile.setUpdateBy(username);
        updateProfile.setUpdateTime(new Date());
        return updateProfile;
    }

    private VolVolunteerProfile updateAuditStatus(Long profileId, Integer auditStatus, String auditReason, Long auditorId,
            String auditorName)
    {
        VolVolunteerProfile existedProfile = requireProfile(profileId);
        if (Objects.equals(existedProfile.getAuditStatus(), auditStatus)
                && Objects.equals(StringUtils.defaultString(existedProfile.getAuditReason()), StringUtils.defaultString(auditReason)))
        {
            return existedProfile;
        }

        VolVolunteerProfile updateProfile = new VolVolunteerProfile();
        updateProfile.setId(existedProfile.getId());
        updateProfile.setAuditStatus(auditStatus);
        updateProfile.setAuditReason(auditReason);
        updateProfile.setAuditorId(auditorId);
        updateProfile.setAuditorName(auditorName);
        updateProfile.setAuditTime(new Date());
        updateProfile.setUpdateBy(auditorName);
        updateProfile.setUpdateTime(new Date());

        if (volunteerProfileMapper.updateVolVolunteerProfile(updateProfile) <= 0)
        {
            throw new ServiceException("志愿者审核状态修改失败");
        }

        VolAuditRecord auditRecord = buildManagerAuditRecord(existedProfile, auditStatus, auditReason, auditorId, auditorName);
        if (auditRecordMapper.insertVolAuditRecord(auditRecord) <= 0)
        {
            throw new ServiceException("志愿者审核记录创建失败");
        }
        sendAuditNotification(existedProfile, auditStatus, auditReason, auditorId, auditorName);
        return volunteerProfileMapper.selectVolVolunteerProfileById(profileId);
    }

    private void sendAuditNotification(VolVolunteerProfile profile, Integer auditStatus, String auditReason,
            Long auditorId, String auditorName)
    {
        notificationService.sendBusinessNotification(profile.getUserId(), auditorId, NOTICE_TYPE_VOLUNTEER_AUDIT,
                TARGET_TYPE_VOLUNTEER, profile.getId(), resolveAuditNoticeTitle(auditStatus),
                resolveAuditNoticeContent(auditStatus, auditReason), "/me", auditorName);
    }

    private String resolveAuditNoticeTitle(Integer auditStatus)
    {
        if (AUDIT_STATUS_APPROVED.equals(auditStatus))
        {
            return "志愿者档案审核通过";
        }
        if (AUDIT_STATUS_REJECTED.equals(auditStatus))
        {
            return "志愿者档案审核驳回";
        }
        if (AUDIT_STATUS_DISABLED.equals(auditStatus))
        {
            return "志愿者档案已禁用";
        }
        return "志愿者档案已启用";
    }

    private String resolveAuditNoticeContent(Integer auditStatus, String auditReason)
    {
        String reason = StringUtils.defaultString(auditReason);
        if (AUDIT_STATUS_APPROVED.equals(auditStatus))
        {
            return "你的志愿者档案已审核通过，可以报名参与活动。" + appendReason(reason);
        }
        if (AUDIT_STATUS_REJECTED.equals(auditStatus))
        {
            return "你的志愿者档案审核未通过，请根据审核意见修改资料后重新提交。" + appendReason(reason);
        }
        if (AUDIT_STATUS_DISABLED.equals(auditStatus))
        {
            return "你的志愿者档案已被禁用，暂时不能报名或参与活动。" + appendReason(reason);
        }
        return "你的志愿者档案已恢复启用，可以继续使用志愿服务功能。" + appendReason(reason);
    }

    private String appendReason(String reason)
    {
        return StringUtils.isBlank(reason) ? "" : " 审核意见：" + reason;
    }

    private VolVolunteerProfile requireProfile(Long profileId)
    {
        if (profileId == null)
        {
            throw new ServiceException("志愿者档案ID不能为空");
        }
        VolVolunteerProfile profile = volunteerProfileMapper.selectVolVolunteerProfileById(profileId);
        if (profile == null)
        {
            throw new ServiceException("志愿者档案不存在");
        }
        return profile;
    }

    private boolean shouldResetToPending(VolVolunteerProfile existedProfile, VolVolunteerProfile input)
    {
        if (AUDIT_STATUS_REJECTED.equals(existedProfile.getAuditStatus()))
        {
            return true;
        }
        return AUDIT_STATUS_APPROVED.equals(existedProfile.getAuditStatus()) && hasCriticalFieldChanged(existedProfile, input);
    }

    private boolean hasCriticalFieldChanged(VolVolunteerProfile existedProfile, VolVolunteerProfile input)
    {
        return hasChanged(input.getRealName(), existedProfile.getRealName())
                || hasChanged(input.getIdCard(), existedProfile.getIdCard());
    }

    private boolean hasChanged(String inputValue, String existedValue)
    {
        return inputValue != null && !Objects.equals(inputValue, existedValue);
    }

    private boolean shouldCreateProfileUpdateAuditRecord(VolVolunteerProfile existedProfile, VolVolunteerProfile updateProfile)
    {
        return AUDIT_STATUS_PENDING.equals(updateProfile.getAuditStatus())
                && !AUDIT_STATUS_PENDING.equals(existedProfile.getAuditStatus());
    }

    private VolAuditRecord buildRegisterAuditRecord(SysUser user, Long profileId)
    {
        VolAuditRecord auditRecord = new VolAuditRecord();
        auditRecord.setTargetType(TARGET_TYPE_VOLUNTEER);
        auditRecord.setTargetId(profileId);
        auditRecord.setTargetUserId(user.getUserId());
        auditRecord.setBeforeStatus("");
        auditRecord.setAuditStatus(String.valueOf(AUDIT_STATUS_PENDING));
        auditRecord.setAuditReason(REGISTER_AUDIT_REASON);
        auditRecord.setCreateBy(user.getUserName());
        auditRecord.setCreateTime(new Date());
        return auditRecord;
    }

    private VolAuditRecord buildProfileUpdateAuditRecord(VolVolunteerProfile existedProfile, String username)
    {
        VolAuditRecord auditRecord = new VolAuditRecord();
        auditRecord.setTargetType(TARGET_TYPE_VOLUNTEER);
        auditRecord.setTargetId(existedProfile.getId());
        auditRecord.setTargetUserId(existedProfile.getUserId());
        auditRecord.setBeforeStatus(String.valueOf(existedProfile.getAuditStatus()));
        auditRecord.setAuditStatus(String.valueOf(AUDIT_STATUS_PENDING));
        auditRecord.setAuditReason(PROFILE_UPDATE_AUDIT_REASON);
        auditRecord.setCreateBy(username);
        auditRecord.setCreateTime(new Date());
        return auditRecord;
    }

    private VolAuditRecord buildManagerAuditRecord(VolVolunteerProfile existedProfile, Integer auditStatus, String auditReason,
            Long auditorId, String auditorName)
    {
        VolAuditRecord auditRecord = new VolAuditRecord();
        auditRecord.setAuditorId(auditorId);
        auditRecord.setAuditorName(auditorName);
        auditRecord.setTargetType(TARGET_TYPE_VOLUNTEER);
        auditRecord.setTargetId(existedProfile.getId());
        auditRecord.setTargetUserId(existedProfile.getUserId());
        auditRecord.setBeforeStatus(String.valueOf(existedProfile.getAuditStatus()));
        auditRecord.setAuditStatus(String.valueOf(auditStatus));
        auditRecord.setAuditReason(auditReason);
        auditRecord.setCreateBy(auditorName);
        auditRecord.setCreateTime(new Date());
        return auditRecord;
    }
}
