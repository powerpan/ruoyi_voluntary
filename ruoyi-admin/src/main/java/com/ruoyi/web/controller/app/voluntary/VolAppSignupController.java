package com.ruoyi.web.controller.app.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
 * 用户端我的报名接口
 */
@RestController
@RequestMapping("/app/voluntary/signups")
public class VolAppSignupController extends BaseController
{
    @Autowired
    private IVolActivitySignupService signupService;

    /**
     * 查询我的活动报名列表。
     */
    @GetMapping("/mine")
    public TableDataInfo mine(VolActivitySignup signup)
    {
        startPage();
        List<VolActivitySignup> list = signupService.selectMyActivitySignupList(getUserId(), signup);
        return getDataTable(list);
    }

    /**
     * 取消我的活动报名。
     */
    @Log(title = "报名取消", businessType = BusinessType.UPDATE)
    @PostMapping("/{id}/cancel")
    public AjaxResult cancel(@PathVariable Long id)
    {
        VolActivitySignup signup = signupService.cancelMyActivitySignup(id, getUserId(), getUsername());
        return success(signup);
    }
}
