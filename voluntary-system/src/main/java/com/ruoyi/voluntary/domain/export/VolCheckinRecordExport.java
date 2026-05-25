package com.ruoyi.voluntary.domain.export;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.voluntary.domain.VolCheckinRecord;

/**
 * 签到签退记录导出对象。
 */
public class VolCheckinRecordExport
{
    @Excel(name = "签到记录ID", sort = 1, cellType = ColumnType.NUMERIC)
    private Long id;

    @Excel(name = "活动标题", sort = 2, width = 30)
    private String activityTitle;

    @Excel(name = "服务地点", sort = 3, width = 30)
    private String serviceLocation;

    @Excel(name = "活动开始时间", sort = 4, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date activityStartTime;

    @Excel(name = "活动结束时间", sort = 5, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date activityEndTime;

    @Excel(name = "志愿者姓名", sort = 6)
    private String volunteerRealName;

    @Excel(name = "联系电话", sort = 7)
    private String volunteerPhone;

    @Excel(name = "所属组织", sort = 8)
    private String volunteerOrganization;

    @Excel(name = "签到时间", sort = 9, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date checkinTime;

    @Excel(name = "签退时间", sort = 10, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date checkoutTime;

    @Excel(name = "签到方式", sort = 11, readConverterExp = "qr=二维码,manual=人工")
    private String checkinMethod;

    @Excel(name = "签退方式", sort = 12, readConverterExp = "qr=二维码,manual=人工")
    private String checkoutMethod;

    @Excel(name = "签到状态", sort = 13, readConverterExp = "0=已签到,1=已签退,2=异常,3=人工确认")
    private Integer status;

    @Excel(name = "异常原因", sort = 14, width = 30)
    private String abnormalReason;

    @Excel(name = "人工处理原因", sort = 15, width = 30)
    private String manualReason;

    @Excel(name = "创建时间", sort = 16, dateFormat = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date createTime;

    public static List<VolCheckinRecordExport> fromList(List<VolCheckinRecord> records)
    {
        List<VolCheckinRecordExport> exports = new ArrayList<VolCheckinRecordExport>();
        if (records == null)
        {
            return exports;
        }
        for (VolCheckinRecord record : records)
        {
            exports.add(from(record));
        }
        return exports;
    }

    private static VolCheckinRecordExport from(VolCheckinRecord record)
    {
        VolCheckinRecordExport export = new VolCheckinRecordExport();
        export.id = record.getId();
        export.activityTitle = record.getActivityTitle();
        export.serviceLocation = record.getServiceLocation();
        export.activityStartTime = record.getActivityStartTime();
        export.activityEndTime = record.getActivityEndTime();
        export.volunteerRealName = record.getVolunteerRealName();
        export.volunteerPhone = record.getVolunteerPhone();
        export.volunteerOrganization = record.getVolunteerOrganization();
        export.checkinTime = record.getCheckinTime();
        export.checkoutTime = record.getCheckoutTime();
        export.checkinMethod = record.getCheckinMethod();
        export.checkoutMethod = record.getCheckoutMethod();
        export.status = record.getStatus();
        export.abnormalReason = record.getAbnormalReason();
        export.manualReason = record.getManualReason();
        export.createTime = record.getCreateTime();
        return export;
    }
}
