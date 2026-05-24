package com.ruoyi.voluntary.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 签到签退记录 vol_checkin_record
 */
public class VolCheckinRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 签到签退记录ID */
    private Long id;

    /** 活动ID */
    private Long activityId;

    /** 报名ID */
    private Long signupId;

    /** 志愿者用户ID */
    private Long volunteerUserId;

    /** 签到时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkinTime;

    /** 签退时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkoutTime;

    /** 签到方式（qr manual） */
    private String checkinMethod;

    /** 签退方式（qr manual） */
    private String checkoutMethod;

    /** 签到状态（0已签到 1已签退 2异常 3人工确认） */
    private Integer status;

    /** 异常原因 */
    private String abnormalReason;

    /** 人工处理原因 */
    private String manualReason;

    /** 人工处理人ID */
    private Long operatorId;

    /** 活动标题，仅用于列表展示 */
    private String activityTitle;

    /** 活动类型，仅用于列表展示 */
    private String activityType;

    /** 服务地点，仅用于列表展示 */
    private String serviceLocation;

    /** 活动开始时间，仅用于列表展示 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityStartTime;

    /** 活动结束时间，仅用于列表展示 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityEndTime;

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

    public Long getVolunteerUserId()
    {
        return volunteerUserId;
    }

    public void setVolunteerUserId(Long volunteerUserId)
    {
        this.volunteerUserId = volunteerUserId;
    }

    public Date getCheckinTime()
    {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime)
    {
        this.checkinTime = checkinTime;
    }

    public Date getCheckoutTime()
    {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime)
    {
        this.checkoutTime = checkoutTime;
    }

    public String getCheckinMethod()
    {
        return checkinMethod;
    }

    public void setCheckinMethod(String checkinMethod)
    {
        this.checkinMethod = checkinMethod;
    }

    public String getCheckoutMethod()
    {
        return checkoutMethod;
    }

    public void setCheckoutMethod(String checkoutMethod)
    {
        this.checkoutMethod = checkoutMethod;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getAbnormalReason()
    {
        return abnormalReason;
    }

    public void setAbnormalReason(String abnormalReason)
    {
        this.abnormalReason = abnormalReason;
    }

    public String getManualReason()
    {
        return manualReason;
    }

    public void setManualReason(String manualReason)
    {
        this.manualReason = manualReason;
    }

    public Long getOperatorId()
    {
        return operatorId;
    }

    public void setOperatorId(Long operatorId)
    {
        this.operatorId = operatorId;
    }

    public String getActivityTitle()
    {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle)
    {
        this.activityTitle = activityTitle;
    }

    public String getActivityType()
    {
        return activityType;
    }

    public void setActivityType(String activityType)
    {
        this.activityType = activityType;
    }

    public String getServiceLocation()
    {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation)
    {
        this.serviceLocation = serviceLocation;
    }

    public Date getActivityStartTime()
    {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime)
    {
        this.activityStartTime = activityStartTime;
    }

    public Date getActivityEndTime()
    {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime)
    {
        this.activityEndTime = activityEndTime;
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
