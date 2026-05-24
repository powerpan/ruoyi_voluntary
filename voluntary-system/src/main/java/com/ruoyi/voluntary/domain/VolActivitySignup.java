package com.ruoyi.voluntary.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动报名 vol_activity_signup
 */
public class VolActivitySignup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报名ID */
    private Long id;

    /** 活动ID */
    private Long activityId;

    /** 志愿者用户ID */
    private Long volunteerUserId;

    /** 报名时姓名快照 */
    private String realName;

    /** 报名时联系电话快照 */
    private String phone;

    /** 报名时组织快照 */
    private String organization;

    /** 报名理由 */
    private String applyReason;

    /** 相关经验 */
    private String experience;

    /** 报名状态（0待筛选 1通过 2拒绝 3候补 4取消） */
    private Integer status;

    /** 筛选意见 */
    private String reviewReason;

    /** 处理人ID */
    private Long reviewerId;

    /** 处理人姓名快照 */
    private String reviewerName;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

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

    /** 活动状态，仅用于列表展示 */
    private Integer activityStatus;

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

    public Long getVolunteerUserId()
    {
        return volunteerUserId;
    }

    public void setVolunteerUserId(Long volunteerUserId)
    {
        this.volunteerUserId = volunteerUserId;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
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

    public String getApplyReason()
    {
        return applyReason;
    }

    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }

    public String getExperience()
    {
        return experience;
    }

    public void setExperience(String experience)
    {
        this.experience = experience;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getReviewReason()
    {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason)
    {
        this.reviewReason = reviewReason;
    }

    public Long getReviewerId()
    {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId)
    {
        this.reviewerId = reviewerId;
    }

    public String getReviewerName()
    {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }

    public Date getReviewTime()
    {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime)
    {
        this.reviewTime = reviewTime;
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

    public Integer getActivityStatus()
    {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus)
    {
        this.activityStatus = activityStatus;
    }
}
