package com.ruoyi.voluntary.domain;

import java.io.Serializable;

/**
 * 服务趋势统计。
 */
public class VolTrendStatistics implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String statDate;

    private Long serviceCount;

    private Long serviceMinutes;

    public String getStatDate()
    {
        return statDate;
    }

    public void setStatDate(String statDate)
    {
        this.statDate = statDate;
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
}
