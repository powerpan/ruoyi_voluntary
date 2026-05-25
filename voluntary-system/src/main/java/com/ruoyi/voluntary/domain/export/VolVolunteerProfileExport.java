package com.ruoyi.voluntary.domain.export;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;

/**
 * 志愿者档案导出对象。
 */
public class VolVolunteerProfileExport
{
    @Excel(name = "档案ID", sort = 1, cellType = ColumnType.NUMERIC)
    private Long id;

    @Excel(name = "账号", sort = 2)
    private String userName;

    @Excel(name = "真实姓名", sort = 3)
    private String realName;

    @Excel(name = "性别", sort = 4, readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    @Excel(name = "联系电话", sort = 5)
    private String phone;

    @Excel(name = "所属组织", sort = 6)
    private String organization;

    @Excel(name = "学院班级", sort = 7)
    private String majorOrClass;

    @Excel(name = "审核状态", sort = 8, readConverterExp = "0=待审核,1=通过,2=驳回,3=禁用")
    private Integer auditStatus;

    @Excel(name = "审核意见", sort = 9, width = 30)
    private String auditReason;

    @Excel(name = "审核人", sort = 10)
    private String auditorName;

    @Excel(name = "审核时间", sort = 11, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date auditTime;

    @Excel(name = "累计服务分钟数", sort = 12, cellType = ColumnType.NUMERIC, isStatistics = true)
    private Integer totalServiceMinutes;

    @Excel(name = "有效服务次数", sort = 13, cellType = ColumnType.NUMERIC, isStatistics = true)
    private Integer serviceCount;

    @Excel(name = "创建时间", sort = 14, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date createTime;

    @Excel(name = "更新时间", sort = 15, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date updateTime;

    public static List<VolVolunteerProfileExport> fromList(List<VolVolunteerProfile> profiles)
    {
        List<VolVolunteerProfileExport> exports = new ArrayList<VolVolunteerProfileExport>();
        if (profiles == null)
        {
            return exports;
        }
        for (VolVolunteerProfile profile : profiles)
        {
            exports.add(from(profile));
        }
        return exports;
    }

    private static VolVolunteerProfileExport from(VolVolunteerProfile profile)
    {
        VolVolunteerProfileExport export = new VolVolunteerProfileExport();
        export.id = profile.getId();
        export.userName = profile.getUserName();
        export.realName = profile.getRealName();
        export.gender = profile.getGender();
        export.phone = profile.getPhone();
        export.organization = profile.getOrganization();
        export.majorOrClass = profile.getMajorOrClass();
        export.auditStatus = profile.getAuditStatus();
        export.auditReason = profile.getAuditReason();
        export.auditorName = profile.getAuditorName();
        export.auditTime = profile.getAuditTime();
        export.totalServiceMinutes = profile.getTotalServiceMinutes();
        export.serviceCount = profile.getServiceCount();
        export.createTime = profile.getCreateTime();
        export.updateTime = profile.getUpdateTime();
        return export;
    }
}
