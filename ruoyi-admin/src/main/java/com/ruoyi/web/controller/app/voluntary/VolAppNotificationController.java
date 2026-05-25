package com.ruoyi.web.controller.app.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.voluntary.domain.VolNotification;
import com.ruoyi.voluntary.service.IVolNotificationService;

/**
 * 用户端我的通知接口
 */
@RestController
@RequestMapping("/app/voluntary/notifications")
public class VolAppNotificationController extends BaseController
{
    @Autowired
    private IVolNotificationService notificationService;

    /**
     * 查询我的通知列表。
     */
    @GetMapping("/mine")
    public TableDataInfo mine(VolNotification notification)
    {
        startPage();
        List<VolNotification> list = notificationService.selectMyNotificationList(getUserId(), notification);
        return getDataTable(list);
    }

    /**
     * 查询我的未读通知数。
     */
    @GetMapping("/unread-count")
    public AjaxResult unreadCount()
    {
        return success(notificationService.countMyUnread(getUserId()));
    }

    /**
     * 标记我的单条通知为已读。
     */
    @Log(title = "通知已读", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/read")
    public AjaxResult read(@PathVariable Long id)
    {
        return toAjax(notificationService.markMyNotificationRead(getUserId(), id, getUsername()));
    }

    /**
     * 标记我的全部通知为已读。
     */
    @Log(title = "通知全部已读", businessType = BusinessType.UPDATE)
    @PutMapping("/read-all")
    public AjaxResult readAll()
    {
        return success(notificationService.markAllMyNotificationsRead(getUserId(), getUsername()));
    }
}
