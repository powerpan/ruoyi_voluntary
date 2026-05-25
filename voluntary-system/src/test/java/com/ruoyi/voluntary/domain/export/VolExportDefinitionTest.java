package com.ruoyi.voluntary.domain.export;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.poi.ExcelUtil;

@DisplayName("P5-E 导出字段定义")
class VolExportDefinitionTest
{
    @Test
    @DisplayName("四类导出对象包含验收关键列")
    void exportDefinitions_includeRequiredColumns()
    {
        assertHasColumns(VolVolunteerProfileExport.class, "账号", "真实姓名", "审核状态", "累计服务分钟数", "创建时间");
        assertHasColumns(VolActivitySignupExport.class, "活动标题", "志愿者姓名", "报名状态", "筛选意见", "报名时间");
        assertHasColumns(VolCheckinRecordExport.class, "活动标题", "签到时间", "签退时间", "签到状态", "异常原因");
        assertHasColumns(VolServiceRecordExport.class, "活动标题", "服务日期", "服务分钟数", "记录状态", "来源签到记录ID");
    }

    @SuppressWarnings("unchecked")
    private void assertHasColumns(Class<?> exportClass, String... requiredNames)
    {
        List<Object[]> fields = new ExcelUtil<Object>((Class<Object>) exportClass).getFields();
        List<String> names = fields.stream()
                .map(objects -> ((Excel) objects[1]).name()).collect(Collectors.toList());
        for (String requiredName : requiredNames)
        {
            assertTrue(names.contains(requiredName), exportClass.getSimpleName() + " 缺少列：" + requiredName);
        }
    }
}
