package com.ruoyi.voluntary.domain;

import java.io.Serializable;

/**
 * 用户端令牌签到信息。
 */
public class VolScanInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 签到令牌 */
    private VolActivityQrToken qrToken;

    /** 活动信息 */
    private VolActivity activity;

    /** 当前用户报名信息 */
    private VolActivitySignup signup;

    /** 当前用户签到签退记录 */
    private VolCheckinRecord checkinRecord;

    /** 是否可以执行当前令牌动作 */
    private Boolean actionable;

    /** 当前令牌动作提示 */
    private String message;

    public VolActivityQrToken getQrToken()
    {
        return qrToken;
    }

    public void setQrToken(VolActivityQrToken qrToken)
    {
        this.qrToken = qrToken;
    }

    public VolActivity getActivity()
    {
        return activity;
    }

    public void setActivity(VolActivity activity)
    {
        this.activity = activity;
    }

    public VolActivitySignup getSignup()
    {
        return signup;
    }

    public void setSignup(VolActivitySignup signup)
    {
        this.signup = signup;
    }

    public VolCheckinRecord getCheckinRecord()
    {
        return checkinRecord;
    }

    public void setCheckinRecord(VolCheckinRecord checkinRecord)
    {
        this.checkinRecord = checkinRecord;
    }

    public Boolean getActionable()
    {
        return actionable;
    }

    public void setActionable(Boolean actionable)
    {
        this.actionable = actionable;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
