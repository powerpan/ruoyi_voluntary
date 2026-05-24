package com.ruoyi.voluntary.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 志愿业务模块运行时配置。
 *
 * <p>显式纳入业务组件，Mapper 仍由 RuoYi 全局 MapperScan 统一扫描。
 */
@Configuration
@ComponentScan("com.ruoyi.voluntary")
public class VoluntaryModuleConfiguration
{
}
