package com.ruoyi.voluntary.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 志愿者档案 vol_volunteer_profile
 */
public class VolVolunteerProfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 档案ID */
    private Long id;

    /** 系统用户ID */
    private Long userId;

    /** 真实姓名 */
    private String realName;

    /** 性别（0男 1女 2未知） */
    private String gender;

    /** 证件号 */
    private String idCard;

    /** 联系电话 */
    private String phone;

    /** 所属组织 */
    private String organization;

    /** 学院班级或社区分组 */
    private String majorOrClass;

    /** 服务特长 */
    private String specialty;

    /** 紧急联系人 */
    private String emergencyContact;

    /** 紧急联系电话 */
    private String emergencyPhone;

    /** 审核状态（0待审核 1通过 2驳回 3禁用） */
    private Integer auditStatus;

    /** 审核意见 */
    private String auditReason;

    /** 审核人ID */
    private Long auditorId;

    /** 审核人姓名快照 */
    private String auditorName;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 累计有效服务分钟数 */
    private Integer totalServiceMinutes;

    /** 有效服务次数 */
    private Integer serviceCount;

    /** 账号名称，仅用于管理端展示 */
    private String userName;

    /** 用户昵称，仅用于管理端展示 */
    private String nickName;

    /** 用户邮箱，仅用于管理端展示 */
    private String email;

    /** 账号状态，仅用于管理端展示 */
    private String userStatus;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getIdCard()
    {
        return idCard;
    }

    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization(String organization)
    {
        this.organization = organization;
    }

    public String getMajorOrClass()
    {
        return majorOrClass;
    }

    public void setMajorOrClass(String majorOrClass)
    {
        this.majorOrClass = majorOrClass;
    }

    public String getSpecialty()
    {
        return specialty;
    }

    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }

    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact)
    {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone()
    {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone)
    {
        this.emergencyPhone = emergencyPhone;
    }

    public Integer getAuditStatus()
    {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus)
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditReason()
    {
        return auditReason;
    }

    public void setAuditReason(String auditReason)
    {
        this.auditReason = auditReason;
    }

    public Long getAuditorId()
    {
        return auditorId;
    }

    public void setAuditorId(Long auditorId)
    {
        this.auditorId = auditorId;
    }

    public String getAuditorName()
    {
        return auditorName;
    }

    public void setAuditorName(String auditorName)
    {
        this.auditorName = auditorName;
    }

    public Date getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public Integer getTotalServiceMinutes()
    {
        return totalServiceMinutes;
    }

    public void setTotalServiceMinutes(Integer totalServiceMinutes)
    {
        this.totalServiceMinutes = totalServiceMinutes;
    }

    public Integer getServiceCount()
    {
        return serviceCount;
    }

    public void setServiceCount(Integer serviceCount)
    {
        this.serviceCount = serviceCount;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(String userStatus)
    {
        this.userStatus = userStatus;
    }
}
