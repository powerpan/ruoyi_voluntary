package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.voluntary.domain.VolServiceRecord;
import com.ruoyi.voluntary.domain.VolServiceSummary;

/**
 * 服务记录 Service
 */
public interface IVolServiceRecordService
{
    VolServiceRecord selectVolServiceRecordById(Long id);

    VolServiceRecord selectVolServiceRecordByCheckinRecordId(Long checkinRecordId);

    List<VolServiceRecord> selectVolServiceRecordList(VolServiceRecord serviceRecord);

    List<VolServiceRecord> selectMyServiceRecordList(Long userId, VolServiceRecord serviceRecord);

    VolServiceSummary selectMyServiceSummary(Long userId);

    int sumEffectiveServiceMinutesByVolunteerUserId(Long volunteerUserId);

    int countEffectiveServiceRecordByVolunteerUserId(Long volunteerUserId);

    VolServiceRecord generateServiceRecordFromCheckinRecord(Long checkinRecordId, String username);

    int insertVolServiceRecord(VolServiceRecord serviceRecord);

    int updateVolServiceRecord(VolServiceRecord serviceRecord);

    int deleteVolServiceRecordById(Long id);

    int deleteVolServiceRecordByIds(Long[] ids);
}
