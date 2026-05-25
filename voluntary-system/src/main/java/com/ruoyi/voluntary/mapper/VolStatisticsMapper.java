package com.ruoyi.voluntary.mapper;

import java.util.List;
import com.ruoyi.voluntary.domain.VolActivityStatistics;
import com.ruoyi.voluntary.domain.VolOrganizationStatistics;
import com.ruoyi.voluntary.domain.VolStatisticsOverview;
import com.ruoyi.voluntary.domain.VolStatisticsQuery;
import com.ruoyi.voluntary.domain.VolTrendStatistics;
import com.ruoyi.voluntary.domain.VolVolunteerStatistics;

/**
 * 志愿业务统计 Mapper。
 */
public interface VolStatisticsMapper
{
    VolStatisticsOverview selectOverview(VolStatisticsQuery query);

    List<VolActivityStatistics> selectActivityStatisticsList(VolStatisticsQuery query);

    List<VolVolunteerStatistics> selectVolunteerStatisticsList(VolStatisticsQuery query);

    List<VolOrganizationStatistics> selectOrganizationStatisticsList(VolStatisticsQuery query);

    List<VolTrendStatistics> selectTrendStatisticsList(VolStatisticsQuery query);
}
