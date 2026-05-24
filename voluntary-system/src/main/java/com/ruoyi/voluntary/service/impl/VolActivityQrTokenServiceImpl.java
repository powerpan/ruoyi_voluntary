package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voluntary.domain.VolActivityQrToken;
import com.ruoyi.voluntary.mapper.VolActivityQrTokenMapper;
import com.ruoyi.voluntary.service.IVolActivityQrTokenService;

/**
 * 活动二维码令牌 Service 实现
 */
@Service
public class VolActivityQrTokenServiceImpl implements IVolActivityQrTokenService
{
    public static final String ACTION_CHECKIN = "checkin";

    public static final String ACTION_CHECKOUT = "checkout";

    public static final Integer STATUS_VALID = 0;

    public static final Integer STATUS_INVALID = 1;

    @Autowired
    private VolActivityQrTokenMapper qrTokenMapper;

    @Override
    public VolActivityQrToken selectVolActivityQrTokenById(Long id)
    {
        return qrTokenMapper.selectVolActivityQrTokenById(id);
    }

    @Override
    public VolActivityQrToken selectVolActivityQrTokenByToken(String token)
    {
        return qrTokenMapper.selectVolActivityQrTokenByToken(token);
    }

    @Override
    public VolActivityQrToken selectValidVolActivityQrTokenByToken(String token)
    {
        return qrTokenMapper.selectValidVolActivityQrTokenByToken(token);
    }

    @Override
    public List<VolActivityQrToken> selectVolActivityQrTokenList(VolActivityQrToken qrToken)
    {
        return qrTokenMapper.selectVolActivityQrTokenList(qrToken == null ? new VolActivityQrToken() : qrToken);
    }

    @Override
    public int insertVolActivityQrToken(VolActivityQrToken qrToken)
    {
        validateQrToken(qrToken);
        if (qrToken.getStatus() == null)
        {
            qrToken.setStatus(STATUS_VALID);
        }
        qrToken.setCreateTime(new Date());
        return qrTokenMapper.insertVolActivityQrToken(qrToken);
    }

    @Override
    public int updateVolActivityQrToken(VolActivityQrToken qrToken)
    {
        if (qrToken == null || qrToken.getId() == null)
        {
            throw new ServiceException("二维码令牌ID不能为空");
        }
        qrToken.setUpdateTime(new Date());
        return qrTokenMapper.updateVolActivityQrToken(qrToken);
    }

    @Override
    public int disableValidTokens(Long activityId, String actionType, String username)
    {
        if (activityId == null)
        {
            throw new ServiceException("活动ID不能为空");
        }
        validateActionType(actionType);
        return qrTokenMapper.disableValidTokens(activityId, actionType, username);
    }

    @Override
    public int deleteVolActivityQrTokenById(Long id)
    {
        return qrTokenMapper.deleteVolActivityQrTokenById(id);
    }

    @Override
    public int deleteVolActivityQrTokenByIds(Long[] ids)
    {
        return qrTokenMapper.deleteVolActivityQrTokenByIds(ids);
    }

    private void validateQrToken(VolActivityQrToken qrToken)
    {
        if (qrToken == null)
        {
            throw new ServiceException("二维码令牌不能为空");
        }
        if (qrToken.getActivityId() == null)
        {
            throw new ServiceException("活动ID不能为空");
        }
        if (qrToken.getToken() == null || qrToken.getToken().trim().length() == 0)
        {
            throw new ServiceException("二维码令牌内容不能为空");
        }
        validateActionType(qrToken.getActionType());
        if (qrToken.getExpireTime() == null)
        {
            throw new ServiceException("二维码过期时间不能为空");
        }
    }

    private void validateActionType(String actionType)
    {
        if (!ACTION_CHECKIN.equals(actionType) && !ACTION_CHECKOUT.equals(actionType))
        {
            throw new ServiceException("二维码操作类型只能为签到或签退");
        }
    }
}
