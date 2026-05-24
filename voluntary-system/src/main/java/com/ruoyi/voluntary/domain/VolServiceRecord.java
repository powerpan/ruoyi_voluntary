package com.ruoyi.voluntary.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务记录 vol_service_record
 */
public class VolServiceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 服务记录ID */
    private Long id;

    /** 活动ID */
    private Long activityId;

    /** 报名ID */
    private Long signupId;

    /** 签到签退记录ID */
    private Long checkinRecordId;

    /** 志愿者用户ID */
    private Long volunteerUserId;

    /** 服务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date serviceDate;

    /** 计入开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 计入结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 计入服务分钟数 */
    private Integer serviceMinutes;

    /** 服务记录状态（0待确认 1有效 2异常 3作废） */
    private Integer status;

    /** 确认人ID */
    private Long confirmUserId;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    /** 修正原因 */
    private String adjustReason;

    /** 活动标题，仅用于列表展示 */
    private String activityTitle;

    /** 服务地点，仅用于列表展示 */
    private String serviceLocation;

    /** 志愿者姓名，仅用于列表展示 */
    private String volunteerRealName;

    /** 志愿者联系电话，仅用于列表展示 */
    private String volunteerPhone;

    /** 志愿者组织，仅用于列表展示 */
    private String volunteerOrganization;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getActivityId()
    {
        return activityId;
    }

    public void setActivityId(Long activityId)
    {
        this.activityId = activityId;
    }

    public Long getSignupId()
    {
        return signupId;
    }

    public void setSignupId(Long signupId)
    {
        this.signupId = signupId;
    }

    public Long getCheckinRecordId()
    {
        return checkinRecordId;
    }

    public void setCheckinRecordId(Long checkinRecordId)
    {
        this.checkinRecordId = checkinRecordId;
    }

    public Long getVolunteerUserId()
    {
        return volunteerUserId;
    }

    public void setVolunteerUserId(Long volunteerUserId)
    {
        this.volunteerUserId = volunteerUserId;
    }

    public Date getServiceDate()
    {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate)
    {
        this.serviceDate = serviceDate;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Integer getServiceMinutes()
    {
        return serviceMinutes;
    }

    public void setServiceMinutes(Integer serviceMinutes)
    {
        this.serviceMinutes = serviceMinutes;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Long getConfirmUserId()
    {
        return confirmUserId;
    }

    public void setConfirmUserId(Long confirmUserId)
    {
        this.confirmUserId = confirmUserId;
    }

    public Date getConfirmTime()
    {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime)
    {
        this.confirmTime = confirmTime;
    }

    public String getAdjustReason()
    {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason)
    {
        this.adjustReason = adjustReason;
    }

    public String getActivityTitle()
    {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle)
    {
        this.activityTitle = activityTitle;
    }

    public String getServiceLocation()
    {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation)
    {
        this.serviceLocation = serviceLocation;
    }

    public String getVolunteerRealName()
    {
        return volunteerRealName;
    }

    public void setVolunteerRealName(String volunteerRealName)
    {
        this.volunteerRealName = volunteerRealName;
    }

    public String getVolunteerPhone()
    {
        return volunteerPhone;
    }

    public void setVolunteerPhone(String volunteerPhone)
    {
        this.volunteerPhone = volunteerPhone;
    }

    public String getVolunteerOrganization()
    {
        return volunteerOrganization;
    }

    public void setVolunteerOrganization(String volunteerOrganization)
    {
        this.volunteerOrganization = volunteerOrganization;
    }
}
