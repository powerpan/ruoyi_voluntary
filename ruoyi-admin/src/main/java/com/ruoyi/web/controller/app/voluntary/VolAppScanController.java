package com.ruoyi.web.controller.app.voluntary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.voluntary.domain.VolCheckinRecord;
import com.ruoyi.voluntary.domain.VolScanInfo;
import com.ruoyi.voluntary.service.IVolCheckinRecordService;

/**
 * 用户端二维码扫码接口
 */
@RestController
@RequestMapping("/app/voluntary/scan")
public class VolAppScanController extends BaseController
{
    @Autowired
    private IVolCheckinRecordService checkinRecordService;

    /**
     * 查询扫码令牌对应的活动和当前操作状态。
     */
    @GetMapping("/{token}")
    public AjaxResult info(@PathVariable String token)
    {
        VolScanInfo scanInfo = checkinRecordService.getScanInfo(token, getUserId());
        return success(scanInfo);
    }

    /**
     * 二维码签到。
     */
    @Log(title = "二维码签到", businessType = BusinessType.INSERT)
    @PostMapping("/{token}/checkin")
    public AjaxResult checkin(@PathVariable String token)
    {
        VolCheckinRecord checkinRecord = checkinRecordService.checkinByQrToken(token, getUserId(), getUsername());
        return success(checkinRecord);
    }

    /**
     * 二维码签退。
     */
    @Log(title = "二维码签退", businessType = BusinessType.UPDATE)
    @PostMapping("/{token}/checkout")
    public AjaxResult checkout(@PathVariable String token)
    {
        VolCheckinRecord checkinRecord = checkinRecordService.checkoutByQrToken(token, getUserId(), getUsername());
        return success(checkinRecord);
    }
}
