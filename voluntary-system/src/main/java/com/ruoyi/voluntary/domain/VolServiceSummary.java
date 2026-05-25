package com.ruoyi.voluntary.domain;

import java.io.Serializable;

/**
 * 志愿者服务时长汇总。
 */
public class VolServiceSummary implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 志愿者用户ID */
    private Long volunteerUserId;

    /** 累计有效服务分钟数 */
    private Integer totalServiceMinutes;

    /** 有效服务次数 */
    private Integer serviceCount;

    public Long getVolunteerUserId()
    {
        return volunteerUserId;
    }

    public void setVolunteerUserId(Long volunteerUserId)
    {
        this.volunteerUserId = volunteerUserId;
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
}
