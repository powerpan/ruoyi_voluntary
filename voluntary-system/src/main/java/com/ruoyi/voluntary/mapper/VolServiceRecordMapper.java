package com.ruoyi.voluntary.mapper;

import java.util.List;
import com.ruoyi.voluntary.domain.VolServiceRecord;

/**
 * 服务记录 Mapper
 */
public interface VolServiceRecordMapper
{
    VolServiceRecord selectVolServiceRecordById(Long id);

    VolServiceRecord selectVolServiceRecordByCheckinRecordId(Long checkinRecordId);

    List<VolServiceRecord> selectVolServiceRecordList(VolServiceRecord serviceRecord);

    int sumEffectiveServiceMinutesByVolunteerUserId(Long volunteerUserId);

    int countEffectiveServiceRecordByVolunteerUserId(Long volunteerUserId);

    int insertVolServiceRecord(VolServiceRecord serviceRecord);

    int updateVolServiceRecord(VolServiceRecord serviceRecord);

    int deleteVolServiceRecordById(Long id);

    int deleteVolServiceRecordByIds(Long[] ids);
}
