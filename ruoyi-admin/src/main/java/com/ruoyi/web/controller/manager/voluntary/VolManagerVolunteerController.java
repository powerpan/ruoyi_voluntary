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
import com.ruoyi.voluntary.domain.VolAuditRecord;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;
import com.ruoyi.voluntary.service.IVolVolunteerProfileService;

/**
 * 管理端志愿者档案与审核接口
 */
@RestController
@RequestMapping("/manager/voluntary/volunteers")
public class VolManagerVolunteerController extends BaseController
{
    @Autowired
    private IVolVolunteerProfileService volunteerProfileService;

    /**
     * 查询志愿者档案列表。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:volunteer:list')")
    @GetMapping("/list")
    public TableDataInfo list(VolVolunteerProfile profile)
    {
        startPage();
        List<VolVolunteerProfile> list = volunteerProfileService.selectVolVolunteerProfileList(profile);
        return getDataTable(list);
    }

    /**
     * 查询志愿者档案详情。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:volunteer:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(volunteerProfileService.selectVolVolunteerProfileById(id));
    }

    /**
     * 管理员维护志愿者档案。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:volunteer:edit')")
    @Log(title = "志愿者档案", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    public AjaxResult edit(@PathVariable Long id, @RequestBody VolVolunteerProfile profile)
    {
        VolVolunteerProfile updatedProfile = volunteerProfileService.updateVolunteerProfileByManager(id, getUsername(), profile);
        return success(updatedProfile);
    }

    /**
     * 审核志愿者档案，通过或驳回。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:volunteer:audit')")
    @Log(title = "志愿者审核", businessType = BusinessType.UPDATE)
    @PostMapping("/{id}/audit")
    public AjaxResult audit(@PathVariable Long id, @RequestBody AuditBody body)
    {
        VolVolunteerProfile updatedProfile = volunteerProfileService.auditVolunteerProfile(id, body.getAuditStatus(),
                body.getAuditReason(), getUserId(), getUsername());
        return success(updatedProfile);
    }

    /**
     * 禁用或启用志愿者档案。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:volunteer:edit')")
    @Log(title = "志愿者状态", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/status")
    public AjaxResult status(@PathVariable Long id, @RequestBody AuditBody body)
    {
        VolVolunteerProfile updatedProfile = volunteerProfileService.changeVolunteerProfileStatus(id, body.getAuditStatus(),
                body.getAuditReason(), getUserId(), getUsername());
        return success(updatedProfile);
    }

    /**
     * 查询志愿者审核记录。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:volunteer:query')")
    @GetMapping("/{id}/audit-records")
    public AjaxResult auditRecords(@PathVariable Long id)
    {
        List<VolAuditRecord> records = volunteerProfileService.selectVolunteerAuditRecords(id);
        return success(records);
    }

    public static class AuditBody
    {
        private Integer auditStatus;

        private String auditReason;

        public Integer getAuditStatus()
        {
            return auditStatus;
        }

        public void setAuditStatus(Integer auditStatus)
        {
            this.auditStatus = auditStatus;
        }

        public String getAuditReason()
        {
            return auditReason;
        }

        public void setAuditReason(String auditReason)
        {
            this.auditReason = auditReason;
        }
    }
}
