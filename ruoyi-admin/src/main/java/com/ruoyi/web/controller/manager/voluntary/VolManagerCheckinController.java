package com.ruoyi.web.controller.manager.voluntary;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.voluntary.domain.VolCheckinRecord;
import com.ruoyi.voluntary.domain.export.VolCheckinRecordExport;
import com.ruoyi.voluntary.service.IVolCheckinRecordService;

/**
 * 管理端签到签退记录接口
 */
@RestController
@RequestMapping("/manager/voluntary/checkins")
public class VolManagerCheckinController extends BaseController
{
    @Autowired
    private IVolCheckinRecordService checkinRecordService;

    /**
     * 查询签到签退记录分页列表。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:checkin:list')")
    @GetMapping("/list")
    public TableDataInfo list(VolCheckinRecord checkinRecord)
    {
        startPage();
        List<VolCheckinRecord> list = checkinRecordService.selectVolCheckinRecordList(checkinRecord);
        return getDataTable(list);
    }

    /**
     * 导出签到签退记录。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:checkin:export')")
    @Log(title = "签到签退记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VolCheckinRecord checkinRecord)
    {
        List<VolCheckinRecord> list = checkinRecordService.selectVolCheckinRecordList(checkinRecord);
        ExcelUtil<VolCheckinRecordExport> util = new ExcelUtil<VolCheckinRecordExport>(
                VolCheckinRecordExport.class);
        util.exportExcel(response, VolCheckinRecordExport.fromList(list), "签到签退记录数据");
    }

    /**
     * 查询签到签退记录详情。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:checkin:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(checkinRecordService.selectVolCheckinRecordById(id));
    }
}
