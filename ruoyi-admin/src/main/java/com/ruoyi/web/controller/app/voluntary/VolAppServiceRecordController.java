package com.ruoyi.web.controller.app.voluntary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.voluntary.domain.VolServiceRecord;
import com.ruoyi.voluntary.service.IVolServiceRecordService;

/**
 * 用户端服务记录接口
 */
@RestController
@RequestMapping("/app/voluntary/service-records")
public class VolAppServiceRecordController extends BaseController
{
    @Autowired
    private IVolServiceRecordService serviceRecordService;

    /**
     * 查询我的服务记录。
     */
    @GetMapping("/mine")
    public TableDataInfo mine(VolServiceRecord serviceRecord)
    {
        startPage();
        List<VolServiceRecord> list = serviceRecordService.selectMyServiceRecordList(getUserId(), serviceRecord);
        return getDataTable(list);
    }

    /**
     * 查询我的服务时长汇总。
     */
    @GetMapping("/summary")
    public AjaxResult summary()
    {
        return success(serviceRecordService.selectMyServiceSummary(getUserId()));
    }
}
