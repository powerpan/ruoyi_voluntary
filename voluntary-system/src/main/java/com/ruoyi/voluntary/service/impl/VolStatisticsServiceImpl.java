package com.ruoyi.voluntary.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.voluntary.domain.VolActivityStatistics;
import com.ruoyi.voluntary.domain.VolOrganizationStatistics;
import com.ruoyi.voluntary.domain.VolStatisticsOverview;
import com.ruoyi.voluntary.domain.VolStatisticsQuery;
import com.ruoyi.voluntary.domain.VolTrendStatistics;
import com.ruoyi.voluntary.domain.VolVolunteerStatistics;
import com.ruoyi.voluntary.mapper.VolStatisticsMapper;
import com.ruoyi.voluntary.service.IVolStatisticsService;

/**
 * 志愿业务统计服务实现。
 */
@Service
public class VolStatisticsServiceImpl implements IVolStatisticsService
{
    @Autowired
    private VolStatisticsMapper statisticsMapper;

    @Override
    public VolStatisticsOverview selectOverview(VolStatisticsQuery query)
    {
        return statisticsMapper.selectOverview(normalizeQuery(query));
    }

    @Override
    public List<VolActivityStatistics> selectActivityStatisticsList(VolStatisticsQuery query)
    {
        return statisticsMapper.selectActivityStatisticsList(normalizeQuery(query));
    }

    @Override
    public List<VolVolunteerStatistics> selectVolunteerStatisticsList(VolStatisticsQuery query)
    {
        return statisticsMapper.selectVolunteerStatisticsList(normalizeQuery(query));
    }

    @Override
    public List<VolOrganizationStatistics> selectOrganizationStatisticsList(VolStatisticsQuery query)
    {
        return statisticsMapper.selectOrganizationStatisticsList(normalizeQuery(query));
    }

    @Override
    public List<VolTrendStatistics> selectTrendStatisticsList(VolStatisticsQuery query)
    {
        VolStatisticsQuery normalizedQuery = normalizeQuery(query);
        if (!"month".equals(normalizedQuery.getTrendType()))
        {
            normalizedQuery.setTrendType("day");
        }
        return statisticsMapper.selectTrendStatisticsList(normalizedQuery);
    }

    private VolStatisticsQuery normalizeQuery(VolStatisticsQuery query)
    {
        VolStatisticsQuery normalizedQuery = query == null ? new VolStatisticsQuery() : query;
        if (normalizedQuery.getBeginDate() != null && normalizedQuery.getEndDate() != null
                && normalizedQuery.getBeginDate().after(normalizedQuery.getEndDate()))
        {
            throw new ServiceException("统计开始日期不能晚于结束日期");
        }
        if (StringUtils.isBlank(normalizedQuery.getTrendType()))
        {
            normalizedQuery.setTrendType("day");
        }
        return normalizedQuery;
    }
}
