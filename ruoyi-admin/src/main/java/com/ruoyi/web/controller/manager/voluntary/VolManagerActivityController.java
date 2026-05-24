package com.ruoyi.web.controller.manager.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.service.IVolActivityService;

/**
 * 管理端志愿活动接口
 */
@RestController
@RequestMapping("/manager/voluntary/activities")
public class VolManagerActivityController extends BaseController
{
    @Autowired
    private IVolActivityService activityService;

    /**
     * 查询活动列表。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(VolActivity activity)
    {
        startPage();
        List<VolActivity> list = activityService.selectVolActivityList(activity);
        return getDataTable(list);
    }

    /**
     * 查询活动详情。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:activity:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(activityService.selectVolActivityById(id));
    }

    /**
     * 新增活动草稿。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:activity:add')")
    @Log(title = "志愿活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VolActivity activity)
    {
        VolActivity createdActivity = activityService.createVolActivityByManager(getUsername(), activity);
        return success(createdActivity);
    }

    /**
     * 编辑草稿或已下架活动。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:activity:edit')")
    @Log(title = "志愿活动", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    public AjaxResult edit(@PathVariable Long id, @RequestBody VolActivity activity)
    {
        VolActivity updatedActivity = activityService.updateVolActivityByManager(id, getUsername(), activity);
        return success(updatedActivity);
    }

    /**
     * 发布、结束、下架或取消活动。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:activity:edit')")
    @Log(title = "活动状态", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/status")
    public AjaxResult status(@PathVariable Long id, @RequestBody ActivityStatusBody body)
    {
        Integer status = body == null ? null : body.getStatus();
        String reason = body == null ? null : body.getReason();
        VolActivity updatedActivity = activityService.changeVolActivityStatus(id, status, reason, getUsername());
        return success(updatedActivity);
    }

    public static class ActivityStatusBody
    {
        private Integer status;

        private String reason;

        public Integer getStatus()
        {
            return status;
        }

        public void setStatus(Integer status)
        {
            this.status = status;
        }

        public String getReason()
        {
            return reason;
        }

        public void setReason(String reason)
        {
            this.reason = reason;
        }
    }
}
