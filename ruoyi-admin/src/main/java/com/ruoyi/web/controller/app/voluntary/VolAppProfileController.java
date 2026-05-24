package com.ruoyi.web.controller.app.voluntary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;
import com.ruoyi.voluntary.service.IVolVolunteerProfileService;

/**
 * 用户端志愿者档案接口
 */
@RestController
@RequestMapping("/app/voluntary/profile")
public class VolAppProfileController extends BaseController
{
    @Autowired
    private IVolVolunteerProfileService volunteerProfileService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询当前登录用户的志愿者档案。
     */
    @GetMapping
    public AjaxResult profile()
    {
        Long userId = getUserId();
        SysUser user = userService.selectUserById(userId);
        VolVolunteerProfile profile = requireMyProfile(userId);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("profile", profile);
        ajax.put("auditStatus", profile.getAuditStatus());
        ajax.put("approved", volunteerProfileService.isVolunteerApproved(userId));
        return ajax;
    }

    /**
     * 修改当前登录用户的志愿者档案。
     */
    @Log(title = "志愿者档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody VolVolunteerProfile profile)
    {
        VolVolunteerProfile updatedProfile = volunteerProfileService.updateMyVolunteerProfile(getUserId(), getUsername(), profile);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("profile", updatedProfile);
        ajax.put("auditStatus", updatedProfile.getAuditStatus());
        ajax.put("approved", volunteerProfileService.isVolunteerApproved(getUserId()));
        return ajax;
    }

    /**
     * 查询当前登录用户审核状态。
     */
    @GetMapping("/audit-status")
    public AjaxResult auditStatus()
    {
        Long userId = getUserId();
        VolVolunteerProfile profile = requireMyProfile(userId);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("auditStatus", profile.getAuditStatus());
        ajax.put("auditReason", profile.getAuditReason());
        ajax.put("approved", volunteerProfileService.isVolunteerApproved(userId));
        return ajax;
    }

    private VolVolunteerProfile requireMyProfile(Long userId)
    {
        VolVolunteerProfile profile = volunteerProfileService.selectVolVolunteerProfileByUserId(userId);
        if (profile == null)
        {
            throw new ServiceException("志愿者档案不存在，请重新注册或联系管理员");
        }
        return profile;
    }
}
