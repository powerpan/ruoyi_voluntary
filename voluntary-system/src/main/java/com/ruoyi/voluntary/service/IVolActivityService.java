package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.voluntary.domain.VolActivity;

/**
 * 志愿活动 Service
 */
public interface IVolActivityService
{
    VolActivity selectVolActivityById(Long id);

    List<VolActivity> selectVolActivityList(VolActivity activity);

    int insertVolActivity(VolActivity activity);

    int updateVolActivity(VolActivity activity);

    VolActivity createVolActivityByManager(String username, VolActivity activity);

    VolActivity updateVolActivityByManager(Long id, String username, VolActivity activity);

    VolActivity changeVolActivityStatus(Long id, Integer status, String reason, String username);

    int deleteVolActivityById(Long id);

    int deleteVolActivityByIds(Long[] ids);
}
