package com.ruoyi.web.controller.manager.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.voluntary.domain.VolActivitySignup;
import com.ruoyi.voluntary.service.IVolActivitySignupService;

/**
 * 管理端活动报名筛选接口
 */
@RestController
@RequestMapping("/manager/voluntary/signups")
public class VolManagerSignupController extends BaseController
{
    @Autowired
    private IVolActivitySignupService signupService;

    /**
     * 查询报名分页列表。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:signup:list')")
    @GetMapping("/list")
    public TableDataInfo list(VolActivitySignup signup)
    {
        startPage();
        List<VolActivitySignup> list = signupService.selectVolActivitySignupList(signup);
        return getDataTable(list);
    }

    /**
     * 查询报名详情。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:signup:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(signupService.selectVolActivitySignupById(id));
    }

    /**
     * 筛选报名，通过、拒绝或候补。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:signup:review')")
    @Log(title = "报名筛选", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/review")
    public AjaxResult review(@PathVariable Long id, @RequestBody ReviewBody body)
    {
        Integer status = body == null ? null : body.getStatus();
        String reviewReason = body == null ? null : body.getReviewReason();
        VolActivitySignup signup = signupService.reviewActivitySignup(id, status, reviewReason, getUserId(),
                getUsername());
        return success(signup);
    }

    public static class ReviewBody
    {
        private Integer status;

        private String reviewReason;

        public Integer getStatus()
        {
            return status;
        }

        public void setStatus(Integer status)
        {
            this.status = status;
        }

        public String getReviewReason()
        {
            return reviewReason;
        }

        public void setReviewReason(String reviewReason)
        {
            this.reviewReason = reviewReason;
        }
    }
}
