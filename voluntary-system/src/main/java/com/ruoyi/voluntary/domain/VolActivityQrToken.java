package com.ruoyi.voluntary.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动二维码令牌 vol_activity_qr_token
 */
public class VolActivityQrToken extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 二维码令牌ID */
    private Long id;

    /** 活动ID */
    private Long activityId;

    /** 二维码随机令牌 */
    private String token;

    /** 操作类型（checkin签到 checkout签退） */
    private String actionType;

    /** 过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /** 令牌状态（0有效 1失效） */
    private Integer status;

    /** 活动标题，仅用于列表展示 */
    private String activityTitle;

    /** 服务地点，仅用于列表展示 */
    private String serviceLocation;

    /** 扫码地址，仅用于接口返回 */
    private String scanUrl;

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

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getActionType()
    {
        return actionType;
    }

    public void setActionType(String actionType)
    {
        this.actionType = actionType;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
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

    public String getScanUrl()
    {
        return scanUrl;
    }

    public void setScanUrl(String scanUrl)
    {
        this.scanUrl = scanUrl;
    }
}
