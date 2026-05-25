package com.ruoyi.voluntary.domain;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 志愿者服务统计。
 */
public class VolVolunteerStatistics implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long volunteerUserId;

    private String realName;

    private String phone;

    private String organization;

    private Long serviceCount;

    private Long serviceMinutes;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date latestServiceDate;

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
