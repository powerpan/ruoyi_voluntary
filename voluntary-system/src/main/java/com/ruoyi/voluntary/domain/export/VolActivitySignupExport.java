package com.ruoyi.voluntary.domain.export;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.voluntary.domain.VolActivitySignup;

/**
 * 报名名单导出对象。
 */
public class VolActivitySignupExport
{
    @Excel(name = "报名ID", sort = 1, cellType = ColumnType.NUMERIC)
    private Long id;

    @Excel(name = "活动标题", sort = 2, width = 30)
    private String activityTitle;

    @Excel(name = "活动类型", sort = 3)
    private String activityType;

    @Excel(name = "服务地点", sort = 4, width = 30)
    private String serviceLocation;

    @Excel(name = "活动开始时间", sort = 5, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date activityStartTime;

    @Excel(name = "活动结束时间", sort = 6, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date activityEndTime;

    @Excel(name = "志愿者姓名", sort = 7)
    private String realName;

    @Excel(name = "联系电话", sort = 8)
    private String phone;

    @Excel(name = "所属组织", sort = 9)
    private String organization;

    @Excel(name = "报名理由", sort = 10, width = 30)
    private String applyReason;

    @Excel(name = "相关经验", sort = 11, width = 30)
    private String experience;

    @Excel(name = "报名状态", sort = 12, readConverterExp = "0=待筛选,1=通过,2=拒绝,3=候补,4=取消")
    private Integer status;

    @Excel(name = "筛选意见", sort = 13, width = 30)
    private String reviewReason;

    @Excel(name = "筛选人", sort = 14)
    private String reviewerName;

    @Excel(name = "筛选时间", sort = 15, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date reviewTime;

    @Excel(name = "报名时间", sort = 16, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date createTime;

    public static List<VolActivitySignupExport> fromList(List<VolActivitySignup> signups)
    {
        List<VolActivitySignupExport> exports = new ArrayList<VolActivitySignupExport>();
        if (signups == null)
        {
            return exports;
        }
        for (VolActivitySignup signup : signups)
        {
            exports.add(from(signup));
        }
        return exports;
    }

    private static VolActivitySignupExport from(VolActivitySignup signup)
    {
        VolActivitySignupExport export = new VolActivitySignupExport();
        export.id = signup.getId();
        export.activityTitle = signup.getActivityTitle();
        export.activityType = signup.getActivityType();
        export.serviceLocation = signup.getServiceLocation();
        export.activityStartTime = signup.getActivityStartTime();
        export.activityEndTime = signup.getActivityEndTime();
        export.realName = signup.getRealName();
        export.phone = signup.getPhone();
        export.organization = signup.getOrganization();
        export.applyReason = signup.getApplyReason();
        export.experience = signup.getExperience();
        export.status = signup.getStatus();
        export.reviewReason = signup.getReviewReason();
        export.reviewerName = signup.getReviewerName();
        export.reviewTime = signup.getReviewTime();
        export.createTime = signup.getCreateTime();
        return export;
    }
}
