package com.ruoyi.voluntary.mapper;

import java.util.List;
import com.ruoyi.voluntary.domain.VolVolunteerProfile;

/**
 * 志愿者档案 Mapper
 */
public interface VolVolunteerProfileMapper
{
    VolVolunteerProfile selectVolVolunteerProfileById(Long id);

    VolVolunteerProfile selectVolVolunteerProfileByUserId(Long userId);

    List<VolVolunteerProfile> selectVolVolunteerProfileList(VolVolunteerProfile profile);

    int insertVolVolunteerProfile(VolVolunteerProfile profile);

    int updateVolVolunteerProfile(VolVolunteerProfile profile);
}
