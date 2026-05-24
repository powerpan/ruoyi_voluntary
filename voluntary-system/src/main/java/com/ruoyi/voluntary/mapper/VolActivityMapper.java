package com.ruoyi.voluntary.mapper;

import java.util.List;
import com.ruoyi.voluntary.domain.VolActivity;

/**
 * 志愿活动 Mapper
 */
public interface VolActivityMapper
{
    VolActivity selectVolActivityById(Long id);

    List<VolActivity> selectVolActivityList(VolActivity activity);

    int insertVolActivity(VolActivity activity);

    int updateVolActivity(VolActivity activity);

    int deleteVolActivityById(Long id);

    int deleteVolActivityByIds(Long[] ids);
}
