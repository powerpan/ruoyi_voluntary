package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.voluntary.domain.VolNotification;

/**
 * 业务通知 Service
 */
public interface IVolNotificationService
{
    VolNotification selectVolNotificationById(Long id);

    List<VolNotification> selectVolNotificationList(VolNotification notification);

    List<VolNotification> selectMyNotificationList(Long userId, VolNotification notification);

    int countMyUnread(Long userId);

    int insertVolNotification(VolNotification notification);

    int updateVolNotification(VolNotification notification);

    int markMyNotificationRead(Long userId, Long id, String username);

    int markAllMyNotificationsRead(Long userId, String username);
}
