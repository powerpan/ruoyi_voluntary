package com.ruoyi.voluntary.domain.export;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.voluntary.domain.VolServiceRecord;

/**
 * 服务记录导出对象。
 */
public class VolServiceRecordExport
{
    @Excel(name = "服务记录ID", sort = 1, cellType = ColumnType.NUMERIC)
    private Long id;

    @Excel(name = "活动标题", sort = 2, width = 30)
    private String activityTitle;

    @Excel(name = "服务地点", sort = 3, width = 30)
    private String serviceLocation;

    @Excel(name = "志愿者姓名", sort = 4)
    private String volunteerRealName;

    @Excel(name = "联系电话", sort = 5)
    private String volunteerPhone;

    @Excel(name = "所属组织", sort = 6)
    private String volunteerOrganization;

    @Excel(name = "服务日期", sort = 7, dateFormat = "yyyy-MM-dd")
    private Date serviceDate;

    @Excel(name = "计入开始时间", sort = 8, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date startTime;

    @Excel(name = "计入结束时间", sort = 9, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date endTime;

    @Excel(name = "服务分钟数", sort = 10, cellType = ColumnType.NUMERIC, isStatistics = true)
    private Integer serviceMinutes;

    @Excel(name = "记录状态", sort = 11, readConverterExp = "0=待确认,1=有效,2=异常,3=作废")
    private Integer status;

    @Excel(name = "来源签到记录ID", sort = 12, cellType = ColumnType.NUMERIC)
    private Long checkinRecordId;

    @Excel(name = "修正原因", sort = 13, width = 30)
    private String adjustReason;

    @Excel(name = "生成时间", sort = 14, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date createTime;

    public static List<VolServiceRecordExport> fromList(List<VolServiceRecord> records)
    {
        List<VolServiceRecordExport> exports = new ArrayList<VolServiceRecordExport>();
        if (records == null)
        {
            return exports;
        }
        for (VolServiceRecord record : records)
        {
            exports.add(from(record));
        }
        return exports;
    }

    private static VolServiceRecordExport from(VolServiceRecord record)
    {
        VolServiceRecordExport export = new VolServiceRecordExport();
        export.id = record.getId();
        export.activityTitle = record.getActivityTitle();
        export.serviceLocation = record.getServiceLocation();
        export.volunteerRealName = record.getVolunteerRealName();
        export.volunteerPhone = record.getVolunteerPhone();
        export.volunteerOrganization = record.getVolunteerOrganization();
        export.serviceDate = record.getServiceDate();
        export.startTime = record.getStartTime();
        export.endTime = record.getEndTime();
        export.serviceMinutes = record.getServiceMinutes();
        export.status = record.getStatus();
        export.checkinRecordId = record.getCheckinRecordId();
        export.adjustReason = record.getAdjustReason();
        export.createTime = record.getCreateTime();
        return export;
    }
}
