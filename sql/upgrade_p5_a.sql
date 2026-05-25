-- P5-A database skeleton upgrade.
-- Apply to an existing ruoyi_voluntary database with:
-- mysql -h127.0.0.1 -P3306 -uroot -p ruoyi_voluntary < sql/upgrade_p5_a.sql

CREATE TABLE IF NOT EXISTS `vol_notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `receiver_user_id` bigint(20) NOT NULL COMMENT '接收人用户ID',
  `actor_user_id` bigint(20) DEFAULT NULL COMMENT '触发人用户ID',
  `notice_type` varchar(32) NOT NULL COMMENT '通知类型',
  `target_type` varchar(32) DEFAULT '' COMMENT '业务对象类型',
  `target_id` bigint(20) DEFAULT NULL COMMENT '业务对象ID',
  `title` varchar(160) NOT NULL COMMENT '通知标题',
  `content` varchar(1000) DEFAULT '' COMMENT '通知内容',
  `action_url` varchar(255) DEFAULT '' COMMENT '用户端跳转地址',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '通知状态（0未读 1已读）',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_notification_receiver_status` (`receiver_user_id`,`status`,`create_time`),
  KEY `idx_notification_target` (`target_type`,`target_id`),
  KEY `idx_notification_type` (`notice_type`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务通知表';

INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(108, '业务通知类型', 'vol_notification_type', '0', 'admin', NOW(), '', NULL, '志愿业务通知类型'),
(109, '业务通知状态', 'vol_notification_status', '0', 'admin', NOW(), '', NULL, '志愿业务通知阅读状态')
ON DUPLICATE KEY UPDATE
  `dict_name` = VALUES(`dict_name`),
  `status` = VALUES(`status`),
  `update_by` = 'admin',
  `update_time` = NOW(),
  `remark` = VALUES(`remark`);

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(170, 1, '志愿者审核', 'volunteer_audit', 'vol_notification_type', '', 'warning', 'Y', '0', 'admin', NOW(), '', NULL, '志愿者审核通知'),
(171, 2, '报名筛选', 'signup_review', 'vol_notification_type', '', 'success', 'N', '0', 'admin', NOW(), '', NULL, '报名筛选通知'),
(172, 3, '活动变更', 'activity_change', 'vol_notification_type', '', 'primary', 'N', '0', 'admin', NOW(), '', NULL, '活动变更通知'),
(173, 4, '签到异常', 'checkin_abnormal', 'vol_notification_type', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '签到异常通知'),
(174, 5, '服务记录', 'service_record', 'vol_notification_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '服务记录通知'),
(175, 6, '系统消息', 'system', 'vol_notification_type', '', '', 'N', '0', 'admin', NOW(), '', NULL, '系统消息'),
(180, 1, '未读', '0', 'vol_notification_status', '', 'warning', 'Y', '0', 'admin', NOW(), '', NULL, '未读通知'),
(181, 2, '已读', '1', 'vol_notification_status', '', 'success', 'N', '0', 'admin', NOW(), '', NULL, '已读通知')
ON DUPLICATE KEY UPDATE
  `dict_sort` = VALUES(`dict_sort`),
  `dict_label` = VALUES(`dict_label`),
  `dict_value` = VALUES(`dict_value`),
  `dict_type` = VALUES(`dict_type`),
  `list_class` = VALUES(`list_class`),
  `is_default` = VALUES(`is_default`),
  `status` = VALUES(`status`),
  `update_by` = 'admin',
  `update_time` = NOW(),
  `remark` = VALUES(`remark`);

INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(2005, '志愿者导出', 2001, 4, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:volunteer:export', '#', 'admin', NOW(), '', NULL, ''),
(2023, '报名导出', 2020, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:signup:export', '#', 'admin', NOW(), '', NULL, ''),
(2033, '签到导出', 2030, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:checkin:export', '#', 'admin', NOW(), '', NULL, ''),
(2042, '服务记录导出', 2040, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:serviceRecord:export', '#', 'admin', NOW(), '', NULL, ''),
(2050, '数据统计', 2000, 6, 'statistics', 'voluntary/statistics/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:statistics:view', 'chart', 'admin', NOW(), '', NULL, '志愿活动数据统计'),
(2051, '统计导出', 2050, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:statistics:export', '#', 'admin', NOW(), '', NULL, ''),
(2060, '通知记录', 2000, 7, 'notification', 'voluntary/notification/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:notification:list', 'message', 'admin', NOW(), '', NULL, '志愿业务通知记录'),
(2061, '通知查询', 2060, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:notification:query', '#', 'admin', NOW(), '', NULL, '')
ON DUPLICATE KEY UPDATE
  `menu_name` = VALUES(`menu_name`),
  `parent_id` = VALUES(`parent_id`),
  `order_num` = VALUES(`order_num`),
  `path` = VALUES(`path`),
  `component` = VALUES(`component`),
  `menu_type` = VALUES(`menu_type`),
  `visible` = VALUES(`visible`),
  `status` = VALUES(`status`),
  `perms` = VALUES(`perms`),
  `icon` = VALUES(`icon`),
  `update_by` = 'admin',
  `update_time` = NOW(),
  `remark` = VALUES(`remark`);

INSERT IGNORE INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(2, 2005),
(2, 2023),
(2, 2033),
(2, 2042),
(2, 2050),
(2, 2051),
(2, 2060),
(2, 2061);
