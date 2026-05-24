package com.ruoyi.web.controller.manager.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ruoyi.voluntary.domain.VolActivityQrToken;
import com.ruoyi.voluntary.service.IVolActivityQrTokenService;

/**
 * 管理端活动二维码令牌接口
 */
@RestController
@RequestMapping("/manager/voluntary")
public class VolManagerQrTokenController extends BaseController
{
    @Autowired
    private IVolActivityQrTokenService qrTokenService;

    @Value("${voluntary.scan-url-prefix:http://localhost:8088/#/scan?token=}")
    private String scanUrlPrefix;

    /**
     * 查询活动二维码令牌。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:checkin:qr')")
    @GetMapping("/activities/{activityId}/qr-tokens")
    public TableDataInfo list(@PathVariable Long activityId, VolActivityQrToken qrToken)
    {
        VolActivityQrToken query = qrToken == null ? new VolActivityQrToken() : qrToken;
        query.setActivityId(activityId);
        startPage();
        List<VolActivityQrToken> list = qrTokenService.selectVolActivityQrTokenList(query);
        list.forEach(this::fillScanUrl);
        return getDataTable(list);
    }

    /**
     * 生成签到或签退二维码令牌。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:checkin:qr')")
    @Log(title = "活动二维码", businessType = BusinessType.INSERT)
    @PostMapping("/activities/{activityId}/qr-tokens")
    public AjaxResult generate(@PathVariable Long activityId, @RequestBody QrTokenBody body)
    {
        String actionType = body == null ? null : body.getActionType();
        Integer expireMinutes = body == null ? null : body.getExpireMinutes();
        VolActivityQrToken qrToken = qrTokenService.generateActivityQrToken(activityId, actionType, expireMinutes,
                getUsername());
        fillScanUrl(qrToken);
        return success(qrToken);
    }

    /**
     * 停用二维码令牌。
     */
    @PreAuthorize("@ss.hasPermi('manager:voluntary:checkin:qr')")
    @Log(title = "活动二维码", businessType = BusinessType.UPDATE)
    @PutMapping("/qr-tokens/{id}/disable")
    public AjaxResult disable(@PathVariable Long id)
    {
        VolActivityQrToken qrToken = qrTokenService.disableVolActivityQrToken(id, getUsername());
        fillScanUrl(qrToken);
        return success(qrToken);
    }

    private void fillScanUrl(VolActivityQrToken qrToken)
    {
        if (qrToken != null && qrToken.getToken() != null)
        {
            qrToken.setScanUrl(scanUrlPrefix + qrToken.getToken());
        }
    }

    public static class QrTokenBody
    {
        private String actionType;

        private Integer expireMinutes;

        public String getActionType()
        {
            return actionType;
        }

        public void setActionType(String actionType)
        {
            this.actionType = actionType;
        }

        public Integer getExpireMinutes()
        {
            return expireMinutes;
        }

        public void setExpireMinutes(Integer expireMinutes)
        {
            this.expireMinutes = expireMinutes;
        }
    }
}
