package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.voluntary.domain.VolNotification;
import com.ruoyi.voluntary.mapper.VolNotificationMapper;
import com.ruoyi.voluntary.service.IVolNotificationService;

/**
 * 业务通知 Service 实现
 */
@Service
public class VolNotificationServiceImpl implements IVolNotificationService
{
    public static final Integer STATUS_UNREAD = 0;

    public static final Integer STATUS_READ = 1;

    @Autowired
    private VolNotificationMapper notificationMapper;

    @Override
    public VolNotification selectVolNotificationById(Long id)
    {
        return notificationMapper.selectVolNotificationById(id);
    }

    @Override
    public List<VolNotification> selectVolNotificationList(VolNotification notification)
    {
        return notificationMapper.selectVolNotificationList(notification == null ? new VolNotification() : notification);
    }

    @Override
    public List<VolNotification> selectMyNotificationList(Long userId, VolNotification notification)
    {
        VolNotification query = notification == null ? new VolNotification() : notification;
        query.setReceiverUserId(requireUserId(userId));
        return notificationMapper.selectVolNotificationList(query);
    }

    @Override
    public int countMyUnread(Long userId)
    {
        return notificationMapper.countUnreadByReceiverUserId(requireUserId(userId));
    }

    @Override
    public int insertVolNotification(VolNotification notification)
    {
        validateNotification(notification);
        if (notification.getStatus() == null)
        {
            notification.setStatus(STATUS_UNREAD);
        }
        notification.setCreateTime(new Date());
        return notificationMapper.insertVolNotification(notification);
    }

    @Override
    public int updateVolNotification(VolNotification notification)
    {
        if (notification == null || notification.getId() == null)
        {
            throw new ServiceException("通知ID不能为空");
        }
        validateActionUrl(notification.getActionUrl());
        notification.setUpdateTime(new Date());
        return notificationMapper.updateVolNotification(notification);
    }

    @Override
    public int markMyNotificationRead(Long userId, Long id, String username)
    {
        requireUserId(userId);
        if (id == null)
        {
            throw new ServiceException("通知ID不能为空");
        }
        VolNotification notification = notificationMapper.selectVolNotificationById(id);
        if (notification == null || !userId.equals(notification.getReceiverUserId()))
        {
            throw new ServiceException("通知不存在或无权操作");
        }
        if (STATUS_READ.equals(notification.getStatus()))
        {
            return 1;
        }
        int rows = notificationMapper.markReadByIdAndReceiverUserId(id, userId, username);
        if (rows <= 0)
        {
            throw new ServiceException("通知不存在或无权操作");
        }
        return rows;
    }

    @Override
    public int markAllMyNotificationsRead(Long userId, String username)
    {
        return notificationMapper.markAllReadByReceiverUserId(requireUserId(userId), username);
    }

    private Long requireUserId(Long userId)
    {
        if (userId == null)
        {
            throw new ServiceException("用户ID不能为空");
        }
        return userId;
    }

    private void validateNotification(VolNotification notification)
    {
        if (notification == null)
        {
            throw new ServiceException("通知内容不能为空");
        }
        requireUserId(notification.getReceiverUserId());
        if (StringUtils.isBlank(notification.getNoticeType()))
        {
            throw new ServiceException("通知类型不能为空");
        }
        if (StringUtils.isBlank(notification.getTitle()))
        {
            throw new ServiceException("通知标题不能为空");
        }
        validateActionUrl(notification.getActionUrl());
    }

    private void validateActionUrl(String actionUrl)
    {
        if (StringUtils.isBlank(actionUrl))
        {
            return;
        }
        if (!actionUrl.startsWith("/") || actionUrl.startsWith("//") || actionUrl.contains("://"))
        {
            throw new ServiceException("通知跳转地址只能使用用户端内部路由");
        }
    }
}
