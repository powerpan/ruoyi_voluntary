package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.voluntary.domain.VolCheckinRecord;
import com.ruoyi.voluntary.domain.VolScanInfo;

/**
 * 签到签退记录 Service
 */
public interface IVolCheckinRecordService
{
    VolCheckinRecord selectVolCheckinRecordById(Long id);

    VolCheckinRecord selectVolCheckinRecordByActivityIdAndVolunteerUserId(Long activityId, Long volunteerUserId);

    List<VolCheckinRecord> selectVolCheckinRecordList(VolCheckinRecord checkinRecord);

    List<VolCheckinRecord> selectMyCheckinRecordList(Long userId, VolCheckinRecord checkinRecord);

    VolScanInfo getScanInfo(String token, Long userId);

    VolCheckinRecord checkinByQrToken(String token, Long userId, String username);

    VolCheckinRecord checkoutByQrToken(String token, Long userId, String username);

    int insertVolCheckinRecord(VolCheckinRecord checkinRecord);

    int updateVolCheckinRecord(VolCheckinRecord checkinRecord);

    int deleteVolCheckinRecordById(Long id);

    int deleteVolCheckinRecordByIds(Long[] ids);
}
