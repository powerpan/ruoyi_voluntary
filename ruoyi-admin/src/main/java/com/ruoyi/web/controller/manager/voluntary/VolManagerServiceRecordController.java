package com.ruoyi.web.controller.manager.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.voluntary.domain.VolServiceRecord;
import com.ruoyi.voluntary.service.IVolServiceRecordService;

/**
 * 管理端服务记录接口
 */
@RestController
@RequestMapping("/manager/voluntary/service-records")
public class VolManagerServiceRecordController extends BaseController
{
    @Autowired
    private IVolServiceRecordService serviceRecordService;

    /**
     * 查询服务记录分页列表。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:serviceRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(VolServiceRecord serviceRecord)
    {
        startPage();
        List<VolServiceRecord> list = serviceRecordService.selectVolServiceRecordList(serviceRecord);
        return getDataTable(list);
    }

    /**
     * 查询服务记录详情。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:serviceRecord:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(serviceRecordService.selectVolServiceRecordById(id));
    }
}
