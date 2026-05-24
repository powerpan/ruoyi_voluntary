package com.ruoyi.voluntary.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 志愿活动 vol_activity
 */
public class VolActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 活动ID */
    private Long id;

    /** 活动标题 */
    private String title;

    /** 活动类型 */
    private String activityType;

    /** 封面图 */
    private String coverUrl;

    /** 服务地点 */
    private String serviceLocation;

    /** 活动开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 活动结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 报名开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signupStartTime;

    /** 报名截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signupEndTime;

    /** 招募人数 */
    private Integer recruitCount;

    /** 已通过报名人数 */
    private Integer approvedCount;

    /** 服务对象 */
    private String serviceTarget;

    /** 活动内容 */
    private String content;

    /** 报名要求 */
    private String requirements;

    /** 活动负责人 */
    private String managerName;

    /** 负责人联系电话 */
    private String managerPhone;

    /** 最大可计入服务分钟数 */
    private Integer maxServiceMinutes;

    /** 活动状态（0草稿 1已发布 2已结束 3已下架 4已取消） */
    private Integer status;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /** 标题、地点综合检索关键字 */
    private String keyword;

    /** 是否只查询报名开放中的活动 */
    private Boolean signupOpen;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getActivityType()
    {
        return activityType;
    }

    public void setActivityType(String activityType)
    {
        this.activityType = activityType;
    }

    public String getCoverUrl()
    {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl)
    {
        this.coverUrl = coverUrl;
    }

    public String getServiceLocation()
    {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation)
    {
        this.serviceLocation = serviceLocation;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getSignupStartTime()
    {
        return signupStartTime;
    }

    public void setSignupStartTime(Date signupStartTime)
    {
        this.signupStartTime = signupStartTime;
    }

    public Date getSignupEndTime()
    {
        return signupEndTime;
    }

    public void setSignupEndTime(Date signupEndTime)
    {
        this.signupEndTime = signupEndTime;
    }

    public Integer getRecruitCount()
    {
        return recruitCount;
    }

    public void setRecruitCount(Integer recruitCount)
    {
        this.recruitCount = recruitCount;
    }

    public Integer getApprovedCount()
    {
        return approvedCount;
    }

    public void setApprovedCount(Integer approvedCount)
    {
        this.approvedCount = approvedCount;
    }

    public String getServiceTarget()
    {
        return serviceTarget;
    }

    public void setServiceTarget(String serviceTarget)
    {
        this.serviceTarget = serviceTarget;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getRequirements()
    {
        return requirements;
    }

    public void setRequirements(String requirements)
    {
        this.requirements = requirements;
    }

    public String getManagerName()
    {
        return managerName;
    }

    public void setManagerName(String managerName)
    {
        this.managerName = managerName;
    }

    public String getManagerPhone()
    {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone)
    {
        this.managerPhone = managerPhone;
    }

    public Integer getMaxServiceMinutes()
    {
        return maxServiceMinutes;
    }

    public void setMaxServiceMinutes(Integer maxServiceMinutes)
    {
        this.maxServiceMinutes = maxServiceMinutes;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public Boolean getSignupOpen()
    {
        return signupOpen;
    }

    public void setSignupOpen(Boolean signupOpen)
    {
        this.signupOpen = signupOpen;
    }
}
