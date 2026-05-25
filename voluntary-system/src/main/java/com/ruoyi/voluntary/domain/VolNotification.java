package com.ruoyi.voluntary.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务通知 vol_notification
 */
public class VolNotification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 通知ID */
    private Long id;

    /** 接收人用户ID */
    private Long receiverUserId;

    /** 触发人用户ID */
    private Long actorUserId;

    /** 通知类型 */
    private String noticeType;

    /** 业务对象类型 */
    private String targetType;

    /** 业务对象ID */
    private Long targetId;

    /** 通知标题 */
    private String title;

    /** 通知内容 */
    private String content;

    /** 用户端跳转地址 */
    private String actionUrl;

    /** 通知状态（0未读 1已读） */
    private Integer status;

    /** 阅读时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date readTime;

    /** 接收人账号，仅用于管理端展示 */
    private String receiverUserName;

    /** 接收人昵称，仅用于管理端展示 */
    private String receiverNickName;

    /** 触发人账号，仅用于管理端展示 */
    private String actorUserName;

    /** 触发人昵称，仅用于管理端展示 */
    private String actorNickName;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getReceiverUserId()
    {
        return receiverUserId;
    }

    public void setReceiverUserId(Long receiverUserId)
    {
        this.receiverUserId = receiverUserId;
    }

    public Long getActorUserId()
    {
        return actorUserId;
    }

    public void setActorUserId(Long actorUserId)
    {
        this.actorUserId = actorUserId;
    }

    public String getNoticeType()
    {
        return noticeType;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public String getTargetType()
    {
        return targetType;
    }

    public void setTargetType(String targetType)
    {
        this.targetType = targetType;
    }

    public Long getTargetId()
    {
        return targetId;
    }

    public void setTargetId(Long targetId)
    {
        this.targetId = targetId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getActionUrl()
    {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl)
    {
        this.actionUrl = actionUrl;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getReadTime()
    {
        return readTime;
    }

    public void setReadTime(Date readTime)
    {
        this.readTime = readTime;
    }

    public String getReceiverUserName()
    {
        return receiverUserName;
    }

    public void setReceiverUserName(String receiverUserName)
    {
        this.receiverUserName = receiverUserName;
    }

    public String getReceiverNickName()
    {
        return receiverNickName;
    }

    public void setReceiverNickName(String receiverNickName)
    {
        this.receiverNickName = receiverNickName;
    }

    public String getActorUserName()
    {
        return actorUserName;
    }

    public void setActorUserName(String actorUserName)
    {
        this.actorUserName = actorUserName;
    }

    public String getActorNickName()
    {
        return actorNickName;
    }

    public void setActorNickName(String actorNickName)
    {
        this.actorNickName = actorNickName;
    }
}
