package com.ruoyi.voluntary.service.impl;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.domain.VolActivitySignup;
import com.ruoyi.voluntary.domain.VolServiceRecord;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;
import com.ruoyi.voluntary.mapper.VolActivityMapper;
import com.ruoyi.voluntary.mapper.VolActivitySignupMapper;
import com.ruoyi.voluntary.mapper.VolAuditRecordMapper;
import com.ruoyi.voluntary.mapper.VolServiceRecordMapper;
import com.ruoyi.voluntary.mapper.VolVolunteerProfileMapper;
import com.ruoyi.voluntary.service.IVolNotificationService;

@ExtendWith(MockitoExtension.class)
@DisplayName("P5-D 业务事件通知")
class VolBusinessNotificationServiceTest
{
    @Mock
    private VolVolunteerProfileMapper volunteerProfileMapper;

    @Mock
    private VolAuditRecordMapper auditRecordMapper;

    @Mock
    private IVolNotificationService notificationService;

    @InjectMocks
    private VolVolunteerProfileServiceImpl volunteerProfileService;

    @Mock
    private VolActivitySignupMapper signupMapper;

    @Mock
    private VolActivityMapper activityMapper;

    @InjectMocks
    private VolActivitySignupServiceImpl signupService;

    @Mock
    private VolServiceRecordMapper serviceRecordMapper;

    @InjectMocks
    private VolServiceRecordServiceImpl serviceRecordService;

    @Test
    @DisplayName("志愿者审核通过后写入审核通知")
    void auditVolunteerProfile_sendsAuditNotification()
    {
        VolVolunteerProfile existedProfile = new VolVolunteerProfile();
        existedProfile.setId(7L);
        existedProfile.setUserId(8L);
        existedProfile.setAuditStatus(VolVolunteerProfileServiceImpl.AUDIT_STATUS_PENDING);

        VolVolunteerProfile updatedProfile = new VolVolunteerProfile();
        updatedProfile.setId(7L);
        updatedProfile.setUserId(8L);
        updatedProfile.setAuditStatus(VolVolunteerProfileServiceImpl.AUDIT_STATUS_APPROVED);

        when(volunteerProfileMapper.selectVolVolunteerProfileById(7L)).thenReturn(existedProfile, updatedProfile);
        when(volunteerProfileMapper.updateVolVolunteerProfile(org.mockito.ArgumentMatchers.any())).thenReturn(1);
        when(auditRecordMapper.insertVolAuditRecord(org.mockito.ArgumentMatchers.any())).thenReturn(1);

        volunteerProfileService.auditVolunteerProfile(7L, VolVolunteerProfileServiceImpl.AUDIT_STATUS_APPROVED,
                "资料完整", 1L, "admin");

        verify(notificationService).sendBusinessNotification(eq(8L), eq(1L), eq("volunteer_audit"),
                eq("volunteer"), eq(7L), eq("志愿者档案审核通过"), contains("资料完整"), eq("/me"),
                eq("admin"));
    }

    @Test
    @DisplayName("报名筛选通过后写入报名通知")
    void reviewActivitySignup_sendsSignupNotification()
    {
        VolActivitySignup pendingSignup = new VolActivitySignup();
        pendingSignup.setId(11L);
        pendingSignup.setActivityId(5L);
        pendingSignup.setVolunteerUserId(8L);
        pendingSignup.setStatus(VolActivitySignupServiceImpl.STATUS_PENDING);

        VolActivitySignup reviewedSignup = new VolActivitySignup();
        reviewedSignup.setId(11L);
        reviewedSignup.setActivityId(5L);
        reviewedSignup.setVolunteerUserId(8L);
        reviewedSignup.setActivityTitle("社区清洁");
        reviewedSignup.setStatus(VolActivitySignupServiceImpl.STATUS_APPROVED);

        VolActivity activity = new VolActivity();
        activity.setId(5L);
        activity.setRecruitCount(2);

        when(signupMapper.selectVolActivitySignupById(11L)).thenReturn(pendingSignup, reviewedSignup);
        when(activityMapper.selectVolActivityById(5L)).thenReturn(activity);
        when(signupMapper.countApprovedSignupByActivityId(5L)).thenReturn(0);
        when(signupMapper.updateVolActivitySignup(org.mockito.ArgumentMatchers.any())).thenReturn(1);
        when(activityMapper.updateVolActivity(org.mockito.ArgumentMatchers.any())).thenReturn(1);

        signupService.reviewActivitySignup(11L, VolActivitySignupServiceImpl.STATUS_APPROVED, "名额确认", 1L,
                "admin");

        verify(notificationService).sendBusinessNotification(eq(8L), eq(1L), eq("signup_review"), eq("signup"),
                eq(11L), eq("报名已通过：社区清洁"), contains("名额确认"), eq("/signups"), eq("admin"));
    }

    @Test
    @DisplayName("服务记录作废后写入服务记录通知")
    void updateVolServiceRecord_sendsServiceRecordNotificationWhenStatusChanges()
    {
        VolServiceRecord existedRecord = new VolServiceRecord();
        existedRecord.setId(9L);
        existedRecord.setStatus(VolServiceRecordServiceImpl.STATUS_EFFECTIVE);

        VolServiceRecord updatedRecord = new VolServiceRecord();
        updatedRecord.setId(9L);
        updatedRecord.setVolunteerUserId(8L);
        updatedRecord.setActivityTitle("社区清洁");
        updatedRecord.setStatus(VolServiceRecordServiceImpl.STATUS_VOIDED);

        VolServiceRecord updateInput = new VolServiceRecord();
        updateInput.setId(9L);
        updateInput.setStatus(VolServiceRecordServiceImpl.STATUS_VOIDED);
        updateInput.setUpdateBy("admin");

        when(serviceRecordMapper.selectVolServiceRecordById(9L)).thenReturn(existedRecord, updatedRecord);
        when(serviceRecordMapper.updateVolServiceRecord(updateInput)).thenReturn(1);

        serviceRecordService.updateVolServiceRecord(updateInput);

        verify(notificationService).sendBusinessNotification(eq(8L), isNull(), eq("service_record"),
                eq("service_record"), eq(9L), eq("服务记录已作废"), contains("已作废"), eq("/service-records"),
                eq("admin"));
    }
}
