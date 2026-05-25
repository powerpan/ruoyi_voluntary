package com.ruoyi.voluntary.domain;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 志愿业务统计查询参数。
 */
public class VolStatisticsQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 统计开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /** 统计结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 活动ID */
    private Long activityId;

    /** 活动标题 */
    private String activityTitle;

    /** 志愿者组织 */
    private String organization;

    /** 志愿者姓名 */
    private String volunteerRealName;

    /** 趋势类型，day 或 month */
    private String trendType;

    public Date getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(Date beginDate)
    {
        this.beginDate = beginDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

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

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization(String organization)
    {
        this.organization = organization;
    }

    public String getVolunteerRealName()
    {
        return volunteerRealName;
    }

    public void setVolunteerRealName(String volunteerRealName)
    {
        this.volunteerRealName = volunteerRealName;
    }

    public String getTrendType()
    {
        return trendType;
    }

    public void setTrendType(String trendType)
    {
        this.trendType = trendType;
    }
}
