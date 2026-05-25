package com.ruoyi.voluntary.domain;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 组织维度服务统计。
 */
public class VolOrganizationStatistics implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String organization;

    private Long volunteerCount;

    private Long serviceCount;

    private Long serviceMinutes;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date latestServiceDate;

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization(String organization)
    {
        this.organization = organization;
    }

    public Long getVolunteerCount()
    {
        return volunteerCount;
    }

    public void setVolunteerCount(Long volunteerCount)
    {
        this.volunteerCount = volunteerCount;
    }

    public Long getServiceCount()
    {
        return serviceCount;
    }

    public void setServiceCount(Long serviceCount)
    {
        this.serviceCount = serviceCount;
    }

    public Long getServiceMinutes()
    {
        return serviceMinutes;
    }

    public void setServiceMinutes(Long serviceMinutes)
    {
        this.serviceMinutes = serviceMinutes;
    }

    public Date getLatestServiceDate()
    {
        return latestServiceDate;
    }

    public void setLatestServiceDate(Date latestServiceDate)
    {
        this.latestServiceDate = latestServiceDate;
    }
}
