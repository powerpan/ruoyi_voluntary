package com.ruoyi.voluntary.service;

import java.util.List;
import com.ruoyi.voluntary.domain.VolActivityQrToken;

/**
 * 活动二维码令牌 Service
 */
public interface IVolActivityQrTokenService
{
    VolActivityQrToken selectVolActivityQrTokenById(Long id);

    VolActivityQrToken selectVolActivityQrTokenByToken(String token);

    VolActivityQrToken selectValidVolActivityQrTokenByToken(String token);

    List<VolActivityQrToken> selectVolActivityQrTokenList(VolActivityQrToken qrToken);

    int insertVolActivityQrToken(VolActivityQrToken qrToken);

    int updateVolActivityQrToken(VolActivityQrToken qrToken);

    int disableValidTokens(Long activityId, String actionType, String username);

    int deleteVolActivityQrTokenById(Long id);

    int deleteVolActivityQrTokenByIds(Long[] ids);
}
