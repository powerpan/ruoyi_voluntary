package com.ruoyi.voluntary.domain.export;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.poi.ExcelUtil;

@DisplayName("P5-E 导出字段定义")
class VolExportDefinitionTest
{
    @BeforeAll
    static void useHeadlessGraphics()
    {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    @DisplayName("四类导出对象包含验收关键列")
    void exportDefinitions_includeRequiredColumns()
    {
        assertHasColumns(VolVolunteerProfileExport.class, "账号", "真实姓名", "审核状态", "累计服务分钟数", "创建时间");
        assertHasColumns(VolActivitySignupExport.class, "活动标题", "志愿者姓名", "报名状态", "筛选意见", "报名时间");
        assertHasColumns(VolCheckinRecordExport.class, "活动标题", "签到时间", "签退时间", "签到状态", "异常原因");
        assertHasColumns(VolServiceRecordExport.class, "活动标题", "服务日期", "服务分钟数", "记录状态", "来源签到记录ID");
    }

    @Test
    @DisplayName("四类导出对象能生成可下载xlsx响应")
    void exportDefinitions_generateXlsxResponse()
    {
        assertGeneratesXlsx(VolVolunteerProfileExport.class, new VolVolunteerProfileExport(), "志愿者档案数据");
        assertGeneratesXlsx(VolActivitySignupExport.class, new VolActivitySignupExport(), "报名名单数据");
        assertGeneratesXlsx(VolCheckinRecordExport.class, new VolCheckinRecordExport(), "签到签退记录数据");
        assertGeneratesXlsx(VolServiceRecordExport.class, new VolServiceRecordExport(), "服务记录数据");
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

    private <T> void assertGeneratesXlsx(Class<T> exportClass, T row, String sheetName)
    {
        MockHttpServletResponse response = new MockHttpServletResponse();

        new ExcelUtil<T>(exportClass).exportExcel(response, Collections.singletonList(row), sheetName);

        byte[] payload = response.getContentAsByteArray();
        assertTrue(response.getContentType().startsWith(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
                exportClass.getSimpleName() + " 响应类型不正确");
        assertTrue(payload.length > 1000, exportClass.getSimpleName() + " 导出文件过小");
        assertEquals('P', payload[0], exportClass.getSimpleName() + " 不是xlsx压缩包");
        assertEquals('K', payload[1], exportClass.getSimpleName() + " 不是xlsx压缩包");
    }
}
