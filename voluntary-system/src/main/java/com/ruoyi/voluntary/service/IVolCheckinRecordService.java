package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.voluntary.domain.VolCheckinRecord;

/**
 * 签到签退记录 Service
 */
public interface IVolCheckinRecordService
{
    VolCheckinRecord selectVolCheckinRecordById(Long id);

    VolCheckinRecord selectVolCheckinRecordByActivityIdAndVolunteerUserId(Long activityId, Long volunteerUserId);

    List<VolCheckinRecord> selectVolCheckinRecordList(VolCheckinRecord checkinRecord);

    List<VolCheckinRecord> selectMyCheckinRecordList(Long userId, VolCheckinRecord checkinRecord);

    int insertVolCheckinRecord(VolCheckinRecord checkinRecord);

    int updateVolCheckinRecord(VolCheckinRecord checkinRecord);

    int deleteVolCheckinRecordById(Long id);

    int deleteVolCheckinRecordByIds(Long[] ids);
}
