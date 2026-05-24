package com.ruoyi.voluntary.mapper;

import java.util.List;
import com.ruoyi.voluntary.domain.VolAuditRecord;

/**
 * 审核记录 Mapper
 */
public interface VolAuditRecordMapper
{
    VolAuditRecord selectVolAuditRecordById(Long id);

    List<VolAuditRecord> selectVolAuditRecordList(VolAuditRecord record);

    int insertVolAuditRecord(VolAuditRecord record);
}
