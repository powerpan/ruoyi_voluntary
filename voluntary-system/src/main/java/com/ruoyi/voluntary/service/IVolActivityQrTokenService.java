package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.voluntary.domain.VolActivityQrToken;

/**
 * 活动签到令牌 Service
 */
public interface IVolActivityQrTokenService
{
    VolActivityQrToken selectVolActivityQrTokenById(Long id);

    VolActivityQrToken selectVolActivityQrTokenByToken(String token);

    VolActivityQrToken selectValidVolActivityQrTokenByToken(String token);

    List<VolActivityQrToken> selectVolActivityQrTokenList(VolActivityQrToken qrToken);

    int insertVolActivityQrToken(VolActivityQrToken qrToken);

    int updateVolActivityQrToken(VolActivityQrToken qrToken);

    VolActivityQrToken generateActivityQrToken(Long activityId, String actionType, Integer expireMinutes,
            String username);

    VolActivityQrToken disableVolActivityQrToken(Long id, String username);

    int disableValidTokens(Long activityId, String actionType, String username);

    int deleteVolActivityQrTokenById(Long id);

    int deleteVolActivityQrTokenByIds(Long[] ids);
}
