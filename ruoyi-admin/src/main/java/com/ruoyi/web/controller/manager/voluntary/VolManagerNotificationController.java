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
import com.ruoyi.voluntary.domain.VolNotification;
import com.ruoyi.voluntary.service.IVolNotificationService;

/**
 * 管理端业务通知接口
 */
@RestController
@RequestMapping("/manager/voluntary/notifications")
public class VolManagerNotificationController extends BaseController
{
    @Autowired
    private IVolNotificationService notificationService;

    /**
     * 查询业务通知分页列表。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:notification:list')")
    @GetMapping("/list")
    public TableDataInfo list(VolNotification notification)
    {
        startPage();
        List<VolNotification> list = notificationService.selectVolNotificationList(notification);
        return getDataTable(list);
    }

    /**
     * 查询业务通知详情。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:notification:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(notificationService.selectVolNotificationById(id));
    }
}
