package com.ruoyi.web.controller.app.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.domain.VolActivitySignup;
import com.ruoyi.voluntary.service.IVolActivityService;
import com.ruoyi.voluntary.service.IVolActivitySignupService;
import com.ruoyi.voluntary.service.impl.VolActivityServiceImpl;

/**
 * 用户端活动浏览与报名接口
 */
@RestController
@RequestMapping("/app/voluntary/activities")
public class VolAppActivityController extends BaseController
{
    @Autowired
    private IVolActivityService activityService;

    @Autowired
    private IVolActivitySignupService signupService;

    /**
     * 公开查询已发布活动列表。
     */
    @Anonymous
    @GetMapping
    public TableDataInfo list(VolActivity activity)
    {
        startPage();
        VolActivity query = activity == null ? new VolActivity() : activity;
        query.setStatus(VolActivityServiceImpl.STATUS_PUBLISHED);
        List<VolActivity> list = activityService.selectVolActivityList(query);
        return getDataTable(list);
    }

    /**
     * 公开查询已发布活动详情。
     */
    @Anonymous
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(activityService.selectPublishedVolActivityById(id));
    }

    /**
     * 报名活动。
     */
    @Log(title = "活动报名", businessType = BusinessType.INSERT)
    @PostMapping("/{id}/signups")
    public AjaxResult signup(@PathVariable Long id, @RequestBody SignupBody body)
    {
        String applyReason = body == null ? null : body.getApplyReason();
        String experience = body == null ? null : body.getExperience();
        VolActivitySignup signup = signupService.applyForActivity(id, getUserId(), getUsername(), applyReason, experience);
        return success(signup);
    }

    public static class SignupBody
    {
        private String applyReason;

        private String experience;

        public String getApplyReason()
        {
            return applyReason;
        }

        public void setApplyReason(String applyReason)
        {
            this.applyReason = applyReason;
        }

        public String getExperience()
        {
            return experience;
        }

        public void setExperience(String experience)
        {
            this.experience = experience;
        }
    }
}
