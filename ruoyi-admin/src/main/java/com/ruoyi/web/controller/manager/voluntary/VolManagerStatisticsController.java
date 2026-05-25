package com.ruoyi.web.controller.manager.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.voluntary.domain.VolActivityStatistics;
import com.ruoyi.voluntary.domain.VolOrganizationStatistics;
import com.ruoyi.voluntary.domain.VolStatisticsQuery;
import com.ruoyi.voluntary.domain.VolTrendStatistics;
import com.ruoyi.voluntary.domain.VolVolunteerStatistics;
import com.ruoyi.voluntary.service.IVolStatisticsService;

/**
 * 管理端志愿业务统计接口。
 */
@RestController
@RequestMapping("/manager/voluntary/statistics")
public class VolManagerStatisticsController extends BaseController
{
    @Autowired
    private IVolStatisticsService statisticsService;

    /**
     * 查询统计总览。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:statistics:view')")
    @GetMapping("/overview")
    public AjaxResult overview(VolStatisticsQuery query)
    {
        return success(statisticsService.selectOverview(query));
    }

    /**
     * 查询活动维度统计。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:statistics:view')")
    @GetMapping("/activities")
    public TableDataInfo activities(VolStatisticsQuery query)
    {
        startPage();
        List<VolActivityStatistics> list = statisticsService.selectActivityStatisticsList(query);
        return getDataTable(list);
    }

    /**
     * 查询志愿者服务排行。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:statistics:view')")
    @GetMapping("/volunteers")
    public TableDataInfo volunteers(VolStatisticsQuery query)
    {
        startPage();
        List<VolVolunteerStatistics> list = statisticsService.selectVolunteerStatisticsList(query);
        return getDataTable(list);
    }

    /**
     * 查询组织维度服务统计。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:statistics:view')")
    @GetMapping("/organizations")
    public TableDataInfo organizations(VolStatisticsQuery query)
    {
        startPage();
        List<VolOrganizationStatistics> list = statisticsService.selectOrganizationStatisticsList(query);
        return getDataTable(list);
    }

    /**
     * 查询服务趋势。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:statistics:view')")
    @GetMapping("/trend")
    public AjaxResult trend(VolStatisticsQuery query)
    {
        List<VolTrendStatistics> list = statisticsService.selectTrendStatisticsList(query);
        return AjaxResult.success(list);
    }
}
