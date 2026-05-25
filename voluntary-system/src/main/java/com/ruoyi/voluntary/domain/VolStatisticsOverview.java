package com.ruoyi.voluntary.domain;

import java.io.Serializable;

/**
 * 志愿业务统计总览。
 */
public class VolStatisticsOverview implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long volunteerTotal;

    private Long approvedVolunteerTotal;

    private Long activityTotal;

    private Long publishedActivityTotal;

    private Long signupTotal;

    private Long approvedSignupTotal;

    private Long checkinTotal;

    private Long checkoutTotal;

    private Long serviceRecordTotal;

    private Long serviceMinutesTotal;

    private Long abnormalTotal;

    public Long getVolunteerTotal()
    {
        return volunteerTotal;
    }

    public void setVolunteerTotal(Long volunteerTotal)
    {
        this.volunteerTotal = volunteerTotal;
    }

    public Long getApprovedVolunteerTotal()
    {
        return approvedVolunteerTotal;
    }

    public void setApprovedVolunteerTotal(Long approvedVolunteerTotal)
    {
        this.approvedVolunteerTotal = approvedVolunteerTotal;
    }

    public Long getActivityTotal()
    {
        return activityTotal;
    }

    public void setActivityTotal(Long activityTotal)
    {
        this.activityTotal = activityTotal;
    }

    public Long getPublishedActivityTotal()
    {
        return publishedActivityTotal;
    }

    public void setPublishedActivityTotal(Long publishedActivityTotal)
    {
        this.publishedActivityTotal = publishedActivityTotal;
    }

    public Long getSignupTotal()
    {
        return signupTotal;
    }

    public void setSignupTotal(Long signupTotal)
    {
        this.signupTotal = signupTotal;
    }

    public Long getApprovedSignupTotal()
    {
        return approvedSignupTotal;
    }

    public void setApprovedSignupTotal(Long approvedSignupTotal)
    {
        this.approvedSignupTotal = approvedSignupTotal;
    }

    public Long getCheckinTotal()
    {
        return checkinTotal;
    }

    public void setCheckinTotal(Long checkinTotal)
    {
        this.checkinTotal = checkinTotal;
    }

    public Long getCheckoutTotal()
    {
        return checkoutTotal;
    }

    public void setCheckoutTotal(Long checkoutTotal)
    {
        this.checkoutTotal = checkoutTotal;
    }

    public Long getServiceRecordTotal()
    {
        return serviceRecordTotal;
    }

    public void setServiceRecordTotal(Long serviceRecordTotal)
    {
        this.serviceRecordTotal = serviceRecordTotal;
    }

    public Long getServiceMinutesTotal()
    {
        return serviceMinutesTotal;
    }

    public void setServiceMinutesTotal(Long serviceMinutesTotal)
    {
        this.serviceMinutesTotal = serviceMinutesTotal;
    }

    public Long getAbnormalTotal()
    {
        return abnormalTotal;
    }

    public void setAbnormalTotal(Long abnormalTotal)
    {
        this.abnormalTotal = abnormalTotal;
    }
}
