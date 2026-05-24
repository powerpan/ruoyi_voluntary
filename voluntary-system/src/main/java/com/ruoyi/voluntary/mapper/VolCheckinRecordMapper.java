package com.ruoyi.voluntary.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voluntary.domain.VolCheckinRecord;

/**
 * 签到签退记录 Mapper
 */
public interface VolCheckinRecordMapper
{
    VolCheckinRecord selectVolCheckinRecordById(Long id);

    VolCheckinRecord selectVolCheckinRecordByActivityIdAndVolunteerUserId(@Param("activityId") Long activityId,
            @Param("volunteerUserId") Long volunteerUserId);

    List<VolCheckinRecord> selectVolCheckinRecordList(VolCheckinRecord checkinRecord);

    int insertVolCheckinRecord(VolCheckinRecord checkinRecord);

    int updateVolCheckinRecord(VolCheckinRecord checkinRecord);

    int deleteVolCheckinRecordById(Long id);

    int deleteVolCheckinRecordByIds(Long[] ids);
}
