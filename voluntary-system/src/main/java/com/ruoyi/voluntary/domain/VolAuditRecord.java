package com.ruoyi.voluntary.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 审核记录 vol_audit_record
 */
public class VolAuditRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审核记录ID */
    private Long id;

    /** 审核人ID */
    private Long auditorId;

    /** 审核人姓名快照 */
    private String auditorName;

    /** 目标类型 */
    private String targetType;

    /** 目标ID */
    private Long targetId;

    /** 目标用户ID */
    private Long targetUserId;

    /** 操作前状态 */
    private String beforeStatus;

    /** 审核或处理结果 */
    private String auditStatus;

    /** 审核意见或处理原因 */
    private String auditReason;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getAuditorId()
    {
        return auditorId;
    }

    public void setAuditorId(Long auditorId)
    {
        this.auditorId = auditorId;
    }

    public String getAuditorName()
    {
        return auditorName;
    }

    public void setAuditorName(String auditorName)
    {
        this.auditorName = auditorName;
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

    public Long getTargetUserId()
    {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId)
    {
        this.targetUserId = targetUserId;
    }

    public String getBeforeStatus()
    {
        return beforeStatus;
    }

    public void setBeforeStatus(String beforeStatus)
    {
        this.beforeStatus = beforeStatus;
    }

    public String getAuditStatus()
    {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus)
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditReason()
    {
        return auditReason;
    }

    public void setAuditReason(String auditReason)
    {
        this.auditReason = auditReason;
    }
}
