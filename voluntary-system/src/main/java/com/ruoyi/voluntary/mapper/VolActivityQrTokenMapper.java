package com.ruoyi.voluntary.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voluntary.domain.VolActivityQrToken;

/**
 * 活动二维码令牌 Mapper
 */
public interface VolActivityQrTokenMapper
{
    VolActivityQrToken selectVolActivityQrTokenById(Long id);

    VolActivityQrToken selectVolActivityQrTokenByToken(String token);

    VolActivityQrToken selectValidVolActivityQrTokenByToken(String token);

    List<VolActivityQrToken> selectVolActivityQrTokenList(VolActivityQrToken qrToken);

    int insertVolActivityQrToken(VolActivityQrToken qrToken);

    int updateVolActivityQrToken(VolActivityQrToken qrToken);

    int disableValidTokens(@Param("activityId") Long activityId, @Param("actionType") String actionType,
            @Param("updateBy") String updateBy);

    int deleteVolActivityQrTokenById(Long id);

    int deleteVolActivityQrTokenByIds(Long[] ids);
}
