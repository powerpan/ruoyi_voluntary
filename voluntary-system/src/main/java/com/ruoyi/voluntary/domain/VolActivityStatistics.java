package com.ruoyi.voluntary.domain;

import java.io.Serializable;

/**
 * 活动维度统计。
 */
public class VolActivityStatistics implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long activityId;

    private String activityTitle;

    private Integer recruitCount;

    private Long signupCount;

    private Long approvedSignupCount;

    private Long checkinCount;

    private Long checkoutCount;

    private Long serviceRecordCount;

    private Long serviceMinutes;

    public Long getActivityId()
    {
        return activityId;
    }

    public void setActivityId(Long activityId)
    {
        this.activityId = activityId;
    }

    public String getActivityTitle()
    {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle)
    {
        this.activityTitle = activityTitle;
    }

    public Integer getRecruitCount()
    {
        return recruitCount;
    }

    public void setRecruitCount(Integer recruitCount)
    {
        this.recruitCount = recruitCount;
    }

    public Long getSignupCount()
    {
        return signupCount;
    }

    public void setSignupCount(Long signupCount)
    {
        this.signupCount = signupCount;
    }

    public Long getApprovedSignupCount()
    {
        return approvedSignupCount;
    }

    public void setApprovedSignupCount(Long approvedSignupCount)
    {
        this.approvedSignupCount = approvedSignupCount;
    }

    public Long getCheckinCount()
    {
        return checkinCount;
    }

    public void setCheckinCount(Long checkinCount)
    {
        this.checkinCount = checkinCount;
    }

    public Long getCheckoutCount()
    {
        return checkoutCount;
    }

    public void setCheckoutCount(Long checkoutCount)
    {
        this.checkoutCount = checkoutCount;
    }

    public Long getServiceRecordCount()
    {
        return serviceRecordCount;
    }

    public void setServiceRecordCount(Long serviceRecordCount)
    {
        this.serviceRecordCount = serviceRecordCount;
    }

    public Long getServiceMinutes()
    {
        return serviceMinutes;
    }

    public void setServiceMinutes(Long serviceMinutes)
    {
        this.serviceMinutes = serviceMinutes;
    }
}
