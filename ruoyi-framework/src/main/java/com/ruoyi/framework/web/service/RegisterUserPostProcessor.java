package com.ruoyi.framework.web.service;

import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 注册用户后的业务扩展点。
 *
 * <p>框架层只负责账号注册，具体业务模块可实现该接口补充档案、角色或审计数据。
 */
public interface RegisterUserPostProcessor
{
    /**
     * 用户账号写入成功后执行。
     *
     * @param user 已注册的系统用户，包含数据库生成的 userId
     */
    void afterRegister(SysUser user);
}
