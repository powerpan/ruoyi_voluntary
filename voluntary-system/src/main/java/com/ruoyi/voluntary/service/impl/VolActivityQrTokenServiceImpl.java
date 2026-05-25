package com.ruoyi.voluntary.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.voluntary.domain.VolActivity;
import com.ruoyi.voluntary.domain.VolActivityQrToken;
import com.ruoyi.voluntary.mapper.VolActivityMapper;
import com.ruoyi.voluntary.mapper.VolActivityQrTokenMapper;
import com.ruoyi.voluntary.service.IVolActivityQrTokenService;

/**
 * 活动签到令牌 Service 实现
 */
@Service
public class VolActivityQrTokenServiceImpl implements IVolActivityQrTokenService
{
    public static final String ACTION_CHECKIN = "checkin";

    public static final String ACTION_CHECKOUT = "checkout";

    public static final Integer STATUS_VALID = 0;

    public static final Integer STATUS_INVALID = 1;

    private static final Integer ACTIVITY_STATUS_PUBLISHED = 1;

    private static final int DEFAULT_EXPIRE_MINUTES = 120;

    private static final int MAX_EXPIRE_MINUTES = 1440;

    @Autowired
    private VolActivityQrTokenMapper qrTokenMapper;

    @Autowired
    private VolActivityMapper activityMapper;

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
            throw new ServiceException("签到令牌ID不能为空");
        }
        qrToken.setUpdateTime(new Date());
        return qrTokenMapper.updateVolActivityQrToken(qrToken);
    }

    @Override
    @Transactional
    public VolActivityQrToken generateActivityQrToken(Long activityId, String actionType, Integer expireMinutes,
            String username)
    {
        validateActivityForQrToken(activityId);
        validateActionType(actionType);
        int safeExpireMinutes = normalizeExpireMinutes(expireMinutes);

        qrTokenMapper.disableValidTokens(activityId, actionType, username);

        VolActivityQrToken qrToken = new VolActivityQrToken();
        qrToken.setActivityId(activityId);
        qrToken.setActionType(actionType);
        qrToken.setToken(IdUtils.fastSimpleUUID());
        qrToken.setExpireTime(new Date(System.currentTimeMillis() + safeExpireMinutes * 60L * 1000L));
        qrToken.setStatus(STATUS_VALID);
        qrToken.setCreateBy(username);
        qrToken.setCreateTime(new Date());
        qrToken.setRemark("管理端生成签到令牌");
        if (qrTokenMapper.insertVolActivityQrToken(qrToken) <= 0 || qrToken.getId() == null)
        {
            throw new ServiceException("签到令牌生成失败");
        }
        return qrTokenMapper.selectVolActivityQrTokenById(qrToken.getId());
    }

    @Override
    @Transactional
    public VolActivityQrToken disableVolActivityQrToken(Long id, String username)
    {
        if (id == null)
        {
            throw new ServiceException("签到令牌ID不能为空");
        }
        VolActivityQrToken existedToken = qrTokenMapper.selectVolActivityQrTokenById(id);
        if (existedToken == null)
        {
            throw new ServiceException("签到令牌不存在");
        }
        if (STATUS_INVALID.equals(existedToken.getStatus()))
        {
            return existedToken;
        }

        VolActivityQrToken updateToken = new VolActivityQrToken();
        updateToken.setId(id);
        updateToken.setStatus(STATUS_INVALID);
        updateToken.setUpdateBy(username);
        updateToken.setUpdateTime(new Date());
        updateToken.setRemark("管理端停用签到令牌");
        if (qrTokenMapper.updateVolActivityQrToken(updateToken) <= 0)
        {
            throw new ServiceException("签到令牌停用失败");
        }
        return qrTokenMapper.selectVolActivityQrTokenById(id);
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

    private void validateActivityForQrToken(Long activityId)
    {
        if (activityId == null)
        {
            throw new ServiceException("活动ID不能为空");
        }
        VolActivity activity = activityMapper.selectVolActivityById(activityId);
        if (activity == null)
        {
            throw new ServiceException("活动不存在");
        }
        if (!ACTIVITY_STATUS_PUBLISHED.equals(activity.getStatus()))
        {
            throw new ServiceException("只有已发布活动可以生成签到令牌");
        }
    }

    private int normalizeExpireMinutes(Integer expireMinutes)
    {
        if (expireMinutes == null)
        {
            return DEFAULT_EXPIRE_MINUTES;
        }
        if (expireMinutes <= 0)
        {
            throw new ServiceException("令牌有效分钟数必须大于0");
        }
        if (expireMinutes > MAX_EXPIRE_MINUTES)
        {
            throw new ServiceException("令牌有效分钟数不能超过1440");
        }
        return expireMinutes;
    }

    private void validateQrToken(VolActivityQrToken qrToken)
    {
        if (qrToken == null)
        {
            throw new ServiceException("签到令牌不能为空");
        }
        if (qrToken.getActivityId() == null)
        {
            throw new ServiceException("活动ID不能为空");
        }
        if (qrToken.getToken() == null || qrToken.getToken().trim().length() == 0)
        {
            throw new ServiceException("签到令牌内容不能为空");
        }
        validateActionType(qrToken.getActionType());
        if (qrToken.getExpireTime() == null)
        {
            throw new ServiceException("令牌过期时间不能为空");
        }
    }

    private void validateActionType(String actionType)
    {
        if (!ACTION_CHECKIN.equals(actionType) && !ACTION_CHECKOUT.equals(actionType))
        {
            throw new ServiceException("令牌操作类型只能为签到或签退");
        }
    }
}
