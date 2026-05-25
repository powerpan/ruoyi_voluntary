-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2026-05-25 19:36:22
-- 服务器版本： 5.7.28
-- PHP 版本： 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `ruoyi_voluntary`
--

-- --------------------------------------------------------

--
-- 表的结构 `gen_table`
--

CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表';

-- --------------------------------------------------------

--
-- 表的结构 `gen_table_column`
--

CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL COMMENT '编号',
  `table_id` bigint(20) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表字段';

-- --------------------------------------------------------

--
-- 表的结构 `sys_config`
--

CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` text COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';

--
-- 转存表中的数据 `sys_config`
--

INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2026-05-24 22:34:44', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),
(2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2026-05-24 22:34:44', '', NULL, '初始化密码 123456'),
(3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2026-05-24 22:34:44', '', NULL, '深色主题theme-dark，浅色主题theme-light'),
(4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'false', 'Y', 'admin', '2026-05-24 22:34:44', '', NULL, '是否开启验证码功能'),
(5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'true', 'Y', 'admin', '2026-05-24 22:34:44', '', NULL, '是否开启注册用户功能'),
(6, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', '2026-05-24 22:34:44', '', NULL, '设置登录IP黑名单限制');

-- --------------------------------------------------------

--
-- 表的结构 `sys_dept`
--

CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

--
-- 转存表中的数据 `sys_dept`
--

INSERT INTO `sys_dept` (`dept_id`, `parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
(100, 0, '0', '志愿活动管理系统', 0, 'admin', '', '', '0', '0', 'admin', '2026-05-24 22:34:44', '', NULL),
(101, 100, '0,100', '运营中心', 1, 'admin', '', '', '0', '0', 'admin', '2026-05-24 22:34:44', '', NULL),
(102, 100, '0,100', '审核中心', 2, 'admin', '', '', '0', '0', 'admin', '2026-05-24 22:34:44', '', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `sys_dict_data`
--

CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

--
-- 转存表中的数据 `sys_dict_data`
--

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '性别男'),
(2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '性别女'),
(3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '性别未知'),
(4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '显示菜单'),
(5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '隐藏菜单'),
(6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '正常状态'),
(7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '停用状态'),
(8, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '系统默认是'),
(9, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '系统默认否'),
(10, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '通知'),
(11, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '公告'),
(12, 1, '正常', '0', 'sys_common_status', '', 'primary', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '正常'),
(13, 2, '停用', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '停用'),
(101, 1, '社区服务', 'community', 'vol_activity_type', '', 'primary', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '社区服务'),
(102, 2, '校园服务', 'campus', 'vol_activity_type', '', 'success', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '校园服务'),
(103, 3, '公益宣传', 'publicity', 'vol_activity_type', '', 'info', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '公益宣传'),
(120, 1, '待审核', '0', 'vol_audit_status', '', 'warning', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '待审核'),
(121, 2, '通过', '1', 'vol_audit_status', '', 'success', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '通过'),
(122, 3, '驳回', '2', 'vol_audit_status', '', 'danger', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '驳回'),
(123, 4, '禁用', '3', 'vol_audit_status', '', 'danger', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '禁用'),
(130, 1, '草稿', '0', 'vol_activity_status', '', 'info', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '草稿'),
(131, 2, '已发布', '1', 'vol_activity_status', '', 'success', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '已发布'),
(132, 3, '已结束', '2', 'vol_activity_status', '', 'primary', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '已结束'),
(133, 4, '已下架', '3', 'vol_activity_status', '', 'warning', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '已下架'),
(134, 5, '已取消', '4', 'vol_activity_status', '', 'danger', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '已取消'),
(140, 1, '待筛选', '0', 'vol_signup_status', '', 'warning', 'Y', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '待筛选'),
(141, 2, '通过', '1', 'vol_signup_status', '', 'success', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '通过'),
(142, 3, '拒绝', '2', 'vol_signup_status', '', 'danger', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '拒绝'),
(143, 4, '候补', '3', 'vol_signup_status', '', 'info', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '候补'),
(144, 5, '取消', '4', 'vol_signup_status', '', 'info', 'N', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '取消'),
(150, 1, '有效', '0', 'vol_qr_token_status', '', 'success', 'Y', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '签到令牌有效'),
(151, 2, '失效', '1', 'vol_qr_token_status', '', 'danger', 'N', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '签到令牌失效'),
(155, 1, '已签到', '0', 'vol_checkin_status', '', 'warning', 'Y', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '已签到未签退'),
(156, 2, '已签退', '1', 'vol_checkin_status', '', 'success', 'N', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '已完成签退'),
(157, 3, '异常', '2', 'vol_checkin_status', '', 'danger', 'N', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '签到签退异常'),
(158, 4, '人工确认', '3', 'vol_checkin_status', '', 'primary', 'N', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '管理员人工确认'),
(160, 1, '待确认', '0', 'vol_service_record_status', '', 'warning', 'Y', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '服务记录待确认'),
(161, 2, '有效', '1', 'vol_service_record_status', '', 'success', 'N', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '有效服务记录'),
(162, 3, '异常', '2', 'vol_service_record_status', '', 'danger', 'N', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '异常服务记录'),
(163, 4, '作废', '3', 'vol_service_record_status', '', 'info', 'N', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '作废服务记录'),
(170, 1, '志愿者审核', 'volunteer_audit', 'vol_notification_type', '', 'warning', 'Y', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '志愿者审核通知'),
(171, 2, '报名筛选', 'signup_review', 'vol_notification_type', '', 'success', 'N', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '报名筛选通知'),
(172, 3, '活动变更', 'activity_change', 'vol_notification_type', '', 'primary', 'N', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '活动变更通知'),
(173, 4, '签到异常', 'checkin_abnormal', 'vol_notification_type', '', 'danger', 'N', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '签到异常通知'),
(174, 5, '服务记录', 'service_record', 'vol_notification_type', '', 'info', 'N', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '服务记录通知'),
(175, 6, '系统消息', 'system', 'vol_notification_type', '', '', 'N', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '系统消息'),
(180, 1, '未读', '0', 'vol_notification_status', '', 'warning', 'Y', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '未读通知'),
(181, 2, '已读', '1', 'vol_notification_status', '', 'success', 'N', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '已读通知');

-- --------------------------------------------------------

--
-- 表的结构 `sys_dict_type`
--

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

--
-- 转存表中的数据 `sys_dict_type`
--

INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, '用户性别', 'sys_user_sex', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '用户性别列表'),
(2, '菜单状态', 'sys_show_hide', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '菜单状态列表'),
(3, '系统开关', 'sys_normal_disable', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '系统开关列表'),
(4, '任务状态', 'sys_job_status', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '任务状态列表'),
(5, '任务分组', 'sys_job_group', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '任务分组列表'),
(6, '系统是否', 'sys_yes_no', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '系统是否列表'),
(7, '通知类型', 'sys_notice_type', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '通知类型列表'),
(8, '通知状态', 'sys_notice_status', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '通知状态列表'),
(9, '操作类型', 'sys_oper_type', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '操作类型列表'),
(10, '系统状态', 'sys_common_status', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '登录状态列表'),
(101, '活动类型', 'vol_activity_type', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '活动类型'),
(102, '活动状态', 'vol_activity_status', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '活动发布状态'),
(103, '审核状态', 'vol_audit_status', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '志愿业务审核状态'),
(104, '报名状态', 'vol_signup_status', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '活动报名筛选状态'),
(105, '签到令牌状态', 'vol_qr_token_status', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '活动签到令牌状态'),
(106, '签到记录状态', 'vol_checkin_status', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '签到签退记录状态'),
(107, '服务记录状态', 'vol_service_record_status', '0', 'admin', '2026-05-24 23:53:10', '', NULL, '志愿服务记录状态'),
(108, '业务通知类型', 'vol_notification_type', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '志愿业务通知类型'),
(109, '业务通知状态', 'vol_notification_status', '0', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '志愿业务通知阅读状态');

-- --------------------------------------------------------

--
-- 表的结构 `sys_job`
--

CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度表';

--
-- 转存表中的数据 `sys_job`
--

INSERT INTO `sys_job` (`job_id`, `job_name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, '系统默认（无业务动作）', 'DEFAULT', 'ryNoParams', '0/30 * * * * ?', '3', '1', '1', 'admin', '2026-05-24 22:34:44', '', NULL, '本地开发默认停用任务');

-- --------------------------------------------------------

--
-- 表的结构 `sys_job_log`
--

CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度日志表';

-- --------------------------------------------------------

--
-- 表的结构 `sys_logininfor`
--

CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';

--
-- 转存表中的数据 `sys_logininfor`
--

INSERT INTO `sys_logininfor` (`info_id`, `user_name`, `ipaddr`, `login_location`, `browser`, `os`, `status`, `msg`, `login_time`) VALUES
(283, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-24 22:37:57'),
(284, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-24 22:39:31'),
(285, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-24 22:39:59'),
(286, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-24 22:56:28'),
(287, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '注册成功', '2026-05-24 22:57:35'),
(288, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-24 23:01:14'),
(289, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-24 23:09:15'),
(290, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-24 23:10:08'),
(291, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-24 23:10:31'),
(292, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-24 23:12:01'),
(293, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-24 23:15:02'),
(294, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-24 23:38:26'),
(295, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-24 23:39:41'),
(296, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 00:11:45'),
(297, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 00:13:39'),
(298, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 00:14:07'),
(299, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 00:22:26'),
(300, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 00:23:45'),
(301, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 00:28:31'),
(302, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 09:09:30'),
(303, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 09:10:31'),
(304, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 09:10:37'),
(305, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '退出成功', '2026-05-25 09:13:54'),
(306, 'powerpan', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '注册成功', '2026-05-25 09:14:16'),
(307, 'powerpan', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 09:14:26'),
(308, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 09:21:34'),
(309, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 09:22:46'),
(310, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 09:34:36'),
(311, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 09:34:39'),
(312, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 09:36:16'),
(313, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 09:53:56'),
(314, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 09:53:56'),
(315, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 09:56:21'),
(316, 'powerpan', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 12:45:36'),
(317, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 12:45:44'),
(318, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 12:50:09'),
(319, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 12:50:38'),
(320, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 12:53:50'),
(321, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 12:53:54'),
(322, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 12:56:14'),
(323, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 12:58:11'),
(324, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 12:58:12'),
(325, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:03:48'),
(326, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:03:48'),
(331, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:05:17'),
(332, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:05:17'),
(337, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:40:21'),
(338, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:40:45'),
(339, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:42:28'),
(340, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:48:07'),
(341, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 13:57:47'),
(342, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 14:15:07'),
(343, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 14:17:22'),
(345, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 14:28:30'),
(347, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 15:10:18'),
(348, 'powerpan', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 15:15:33'),
(349, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 15:15:48'),
(350, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 15:31:07'),
(351, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 15:45:55'),
(352, 'powerpan', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 15:46:16'),
(353, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 16:59:25'),
(354, 'powerpan', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 16:59:30'),
(355, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 17:19:55'),
(356, 'p3g_demo_volunteer', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 17:20:22'),
(357, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 18:41:43'),
(358, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 18:42:46'),
(359, 'admin', '127.0.0.1', '内网IP', 'Downloading Tool', 'Unknown', '0', '登录成功', '2026-05-25 18:43:21'),
(360, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 18:45:09'),
(361, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Mac OS X', '0', '登录成功', '2026-05-25 19:23:40');

-- --------------------------------------------------------

--
-- 表的结构 `sys_menu`
--

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

--
-- 转存表中的数据 `sys_menu`
--

INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, '系统管理', 0, 4, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2025-12-18 14:30:54', 'admin', '2026-03-30 14:13:30', '系统管理目录'),
(2, '系统监控', 0, 5, 'monitor', NULL, '', 1, 0, 'M', '1', '0', '', 'monitor', 'admin', '2025-12-18 14:30:54', 'admin', '2026-03-30 14:13:55', '系统监控目录'),
(3, '系统工具', 0, 5, 'tool', NULL, '', 1, 0, 'M', '1', '0', '', 'tool', 'admin', '2025-12-18 14:30:55', 'admin', '2026-03-30 14:13:59', '系统工具目录'),
(4, '项目说明', 0, 6, 'https://doc.ruoyi.vip', NULL, '', 0, 0, 'M', '1', '0', '', 'guide', 'admin', '2025-12-18 14:30:55', 'admin', '2026-03-30 14:14:09', '若依官网地址'),
(100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2025-12-18 14:30:55', '', NULL, '用户管理菜单'),
(101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2025-12-18 14:30:55', '', NULL, '角色管理菜单'),
(102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2025-12-18 14:30:55', '', NULL, '菜单管理菜单'),
(103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2025-12-18 14:30:55', '', NULL, '部门管理菜单'),
(104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2025-12-18 14:30:55', '', NULL, '岗位管理菜单'),
(105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '1', '0', 'system:dict:list', 'dict', 'admin', '2025-12-18 14:30:55', 'admin', '2026-04-05 10:14:07', '字典管理菜单'),
(106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2025-12-18 14:30:55', 'admin', '2026-04-04 18:08:21', '参数设置菜单'),
(107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '1', '0', 'system:notice:list', 'message', 'admin', '2025-12-18 14:30:55', 'admin', '2026-03-30 14:11:25', '通知公告菜单'),
(108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '1', '0', '', 'log', 'admin', '2025-12-18 14:30:55', 'admin', '2026-03-30 14:11:09', '日志管理菜单'),
(109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2025-12-18 14:30:55', '', NULL, '在线用户菜单'),
(110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2025-12-18 14:30:55', '', NULL, '定时任务菜单'),
(111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2025-12-18 14:30:55', '', NULL, '数据监控菜单'),
(112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2025-12-18 14:30:55', '', NULL, '服务监控菜单'),
(113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2025-12-18 14:30:55', '', NULL, '缓存监控菜单'),
(114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2025-12-18 14:30:55', '', NULL, '缓存列表菜单'),
(115, '表单构建', 3, 1, 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2025-12-18 14:30:56', '', NULL, '表单构建菜单'),
(116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2025-12-18 14:30:56', '', NULL, '代码生成菜单'),
(117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2025-12-18 14:30:56', '', NULL, '系统接口菜单'),
(500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2025-12-18 14:30:56', '', NULL, '操作日志菜单'),
(501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2025-12-18 14:30:56', '', NULL, '登录日志菜单'),
(1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2025-12-18 14:30:56', '', NULL, ''),
(1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1016, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1017, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1018, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1019, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1020, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1021, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1022, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1023, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1024, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1025, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1026, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1027, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1028, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1029, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1030, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2025-12-18 14:30:57', '', NULL, ''),
(1031, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1032, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1033, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1034, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1035, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1036, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1037, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1038, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1039, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1040, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1041, '日志导出', 500, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1042, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1043, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1044, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1045, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2025-12-18 14:30:58', '', NULL, ''),
(1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1049, '任务查询', 110, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1050, '任务新增', 110, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1051, '任务修改', 110, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1052, '任务删除', 110, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1053, '状态修改', 110, 5, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1054, '任务导出', 110, 6, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1055, '生成查询', 116, 1, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1056, '生成修改', 116, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1057, '生成删除', 116, 3, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1058, '导入代码', 116, 4, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1059, '预览代码', 116, 5, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(1060, '生成代码', 116, 6, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2025-12-18 14:30:59', '', NULL, ''),
(2000, '志愿管理', 0, 2, 'voluntary', NULL, '', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', '2026-05-24 22:34:44', '', NULL, '志愿活动管理目录'),
(2001, '志愿者审核', 2000, 1, 'volunteer', 'voluntary/volunteer/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:volunteer:list', 'people', 'admin', '2026-05-24 22:34:44', '', NULL, '志愿者档案与审核管理'),
(2002, '志愿者查询', 2001, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:volunteer:query', '#', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2003, '志愿者编辑', 2001, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:volunteer:edit', '#', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2004, '志愿者审核', 2001, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:volunteer:audit', '#', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2005, '志愿者导出', 2001, 4, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:volunteer:export', '#', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', ''),
(2010, '活动管理', 2000, 2, 'activity', 'voluntary/activity/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:activity:list', 'date', 'admin', '2026-05-24 22:34:44', '', NULL, '活动创建、发布与维护'),
(2011, '活动查询', 2010, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:activity:query', '#', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2012, '活动新增', 2010, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:activity:add', '#', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2013, '活动编辑', 2010, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:activity:edit', '#', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2020, '报名管理', 2000, 3, 'signup', 'voluntary/signup/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:signup:list', 'form', 'admin', '2026-05-24 22:34:44', '', NULL, '活动报名筛选管理'),
(2021, '报名查询', 2020, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:signup:query', '#', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2022, '报名筛选', 2020, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:signup:review', '#', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2023, '报名导出', 2020, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:signup:export', '#', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', ''),
(2030, '签到管理', 2000, 4, 'checkin', 'voluntary/checkin/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:checkin:list', 'log', 'admin', '2026-05-24 23:53:10', 'admin', '2026-05-25 15:46:43', '活动签到签退记录管理'),
(2031, '签到查询', 2030, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:checkin:query', '#', 'admin', '2026-05-24 23:53:10', '', NULL, ''),
(2032, '令牌生成', 2030, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:checkin:qr', '#', 'admin', '2026-05-24 23:53:10', '', NULL, ''),
(2033, '签到导出', 2030, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:checkin:export', '#', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', ''),
(2040, '服务记录', 2000, 5, 'serviceRecord', 'voluntary/serviceRecord/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:serviceRecord:list', 'documentation', 'admin', '2026-05-24 23:53:10', '', NULL, '志愿服务记录管理'),
(2041, '服务记录查询', 2040, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:serviceRecord:query', '#', 'admin', '2026-05-24 23:53:10', '', NULL, ''),
(2042, '服务记录导出', 2040, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:serviceRecord:export', '#', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', ''),
(2050, '数据统计', 2000, 6, 'statistics', 'voluntary/statistics/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:statistics:view', 'chart', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '志愿活动数据统计'),
(2051, '统计导出', 2050, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:statistics:export', '#', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', ''),
(2060, '通知记录', 2000, 7, 'notification', 'voluntary/notification/index', '', 1, 0, 'C', '0', '0', 'manager:voluntary:notification:list', 'message', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '志愿业务通知记录'),
(2061, '通知查询', 2060, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manager:voluntary:notification:query', '#', 'admin', '2026-05-25 13:27:30', 'admin', '2026-05-25 13:33:28', '');

-- --------------------------------------------------------

--
-- 表的结构 `sys_notice`
--

CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';

-- --------------------------------------------------------

--
-- 表的结构 `sys_oper_log`
--

CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) DEFAULT '0' COMMENT '消耗时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录';

--
-- 转存表中的数据 `sys_oper_log`
--

INSERT INTO `sys_oper_log` (`oper_id`, `title`, `business_type`, `method`, `request_method`, `operator_type`, `oper_name`, `dept_name`, `oper_url`, `oper_ip`, `oper_location`, `oper_param`, `json_result`, `status`, `error_msg`, `oper_time`, `cost_time`) VALUES
(789, '活动报名', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppActivityController.signup()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/activities/1/signups', '127.0.0.1', '内网IP', '1 {\"applyReason\":\"希望参与社区志愿服务，协助现场秩序维护。\",\"experience\":\"具备活动签到引导和信息登记经验。\"}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-10 11:00:00\",\"activityId\":1,\"activityStartTime\":\"2026-06-10 09:00:00\",\"activityStatus\":1,\"activityTitle\":\"P3-G用户端演示活动\",\"activityType\":\"community\",\"applyReason\":\"希望参与社区志愿服务，协助现场秩序维护。\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-24 23:01:21\",\"experience\":\"具备活动签到引导和信息登记经验。\",\"id\":1,\"organization\":\"P3-G演示组织\",\"params\":{},\"phone\":\"13900000001\",\"realName\":\"P3G演示志愿者\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"volunteerUserId\":2}}', 0, NULL, '2026-05-24 23:01:20', 17),
(790, '志愿活动', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerActivityController.add()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities', '127.0.0.1', '内网IP', '{\"activityType\":\"community\",\"approvedCount\":0,\"content\":\"面向社区居民开展秩序维护、咨询指引和现场协助服务。\",\"createBy\":\"admin\",\"createTime\":\"2026-05-24 23:14:32\",\"endTime\":\"2026-06-12 12:00:00\",\"id\":2,\"managerName\":\"P3H管理员\",\"managerPhone\":\"13900000099\",\"maxServiceMinutes\":180,\"params\":{},\"recruitCount\":2,\"requirements\":\"已审核通过志愿者，能够按时到场并服从现场安排。\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"serviceTarget\":\"社区居民\",\"signupEndTime\":\"2026-06-10 18:00:00\",\"signupStartTime\":\"2026-05-24 00:00:00\",\"startTime\":\"2026-06-12 09:00:00\",\"status\":0,\"title\":\"P3-H闭环验收活动\"}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityType\":\"community\",\"approvedCount\":0,\"content\":\"面向社区居民开展秩序维护、咨询指引和现场协助服务。\",\"createBy\":\"admin\",\"createTime\":\"2026-05-24 23:14:32\",\"endTime\":\"2026-06-12 12:00:00\",\"id\":2,\"managerName\":\"P3H管理员\",\"managerPhone\":\"13900000099\",\"maxServiceMinutes\":180,\"params\":{},\"recruitCount\":2,\"requirements\":\"已审核通过志愿者，能够按时到场并服从现场安排。\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"serviceTarget\":\"社区居民\",\"signupEndTime\":\"2026-06-10 18:00:00\",\"signupStartTime\":\"2026-05-24 00:00:00\",\"startTime\":\"2026-06-12 09:00:00\",\"status\":0,\"title\":\"P3-H闭环验收活动\"}}', 0, NULL, '2026-05-24 23:14:32', 5),
(791, '活动状态', 2, 'com.ruoyi.web.controller.manager.voluntary.VolManagerActivityController.status()', 'PUT', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/status', '127.0.0.1', '内网IP', '2 {\"reason\":\"P3-H阶段验收发布\",\"status\":1}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityType\":\"community\",\"approvedCount\":0,\"content\":\"面向社区居民开展秩序维护、咨询指引和现场协助服务。\",\"createBy\":\"admin\",\"createTime\":\"2026-05-24 23:14:32\",\"endTime\":\"2026-06-12 12:00:00\",\"id\":2,\"managerName\":\"P3H管理员\",\"managerPhone\":\"13900000099\",\"maxServiceMinutes\":180,\"params\":{},\"publishTime\":\"2026-05-24 23:14:44\",\"recruitCount\":2,\"remark\":\"P3-H阶段验收发布\",\"requirements\":\"已审核通过志愿者，能够按时到场并服从现场安排。\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"serviceTarget\":\"社区居民\",\"signupEndTime\":\"2026-06-10 18:00:00\",\"signupStartTime\":\"2026-05-24 00:00:00\",\"startTime\":\"2026-06-12 09:00:00\",\"status\":1,\"title\":\"P3-H闭环验收活动\",\"updateBy\":\"admin\",\"updateTime\":\"2026-05-24 23:14:44\"}}', 0, NULL, '2026-05-24 23:14:43', 6),
(792, '活动报名', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppActivityController.signup()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/activities/2/signups', '127.0.0.1', '内网IP', '2 {\"applyReason\":\"参加P3-H闭环验收活动，协助社区现场服务。\",\"experience\":\"已完成P3-G用户端报名演示。\"}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityStatus\":1,\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"applyReason\":\"参加P3-H闭环验收活动，协助社区现场服务。\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-24 23:15:35\",\"experience\":\"已完成P3-G用户端报名演示。\",\"id\":2,\"organization\":\"P3-G演示组织\",\"params\":{},\"phone\":\"13900000001\",\"realName\":\"P3G演示志愿者\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"volunteerUserId\":2}}', 0, NULL, '2026-05-24 23:15:34', 45),
(793, '报名筛选', 2, 'com.ruoyi.web.controller.manager.voluntary.VolManagerSignupController.review()', 'PUT', 1, 'admin', '运营中心', '/manager/voluntary/signups/2/review', '127.0.0.1', '内网IP', '2 {\"reviewReason\":\"P3-H验收通过，进入活动服务名单。\",\"status\":1}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityStatus\":1,\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"applyReason\":\"参加P3-H闭环验收活动，协助社区现场服务。\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-24 23:15:35\",\"experience\":\"已完成P3-G用户端报名演示。\",\"id\":2,\"organization\":\"P3-G演示组织\",\"params\":{},\"phone\":\"13900000001\",\"realName\":\"P3G演示志愿者\",\"reviewReason\":\"P3-H验收通过，进入活动服务名单。\",\"reviewTime\":\"2026-05-24 23:15:46\",\"reviewerId\":1,\"reviewerName\":\"admin\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":1,\"updateBy\":\"admin\",\"updateTime\":\"2026-05-24 23:15:46\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-24 23:15:45', 14),
(794, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkout\",\"expireMinutes\":60}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkout\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 00:14:38\",\"expireTime\":\"2026-05-25 01:14:38\",\"id\":2,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=01bdd351f6f54ce5a3ff33e2f760200c\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"01bdd351f6f54ce5a3ff33e2f760200c\"}}', 0, NULL, '2026-05-25 00:14:37', 25),
(795, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":60}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 00:14:38\",\"expireTime\":\"2026-05-25 01:14:38\",\"id\":1,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=cbb667b1dcff4f86a401fdac782fcabc\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"cbb667b1dcff4f86a401fdac782fcabc\"}}', 0, NULL, '2026-05-25 00:14:37', 24),
(796, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":30}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 00:14:48\",\"expireTime\":\"2026-05-25 00:44:48\",\"id\":3,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=02c5f3a039a74463972b835d1843dc8b\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"02c5f3a039a74463972b835d1843dc8b\"}}', 0, NULL, '2026-05-25 00:14:48', 7),
(797, '活动二维码', 2, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.disable()', 'PUT', 1, 'admin', '运营中心', '/manager/voluntary/qr-tokens/3/disable', '127.0.0.1', '内网IP', '3', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 00:14:48\",\"expireTime\":\"2026-05-25 00:44:48\",\"id\":3,\"params\":{},\"remark\":\"管理端停用二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=02c5f3a039a74463972b835d1843dc8b\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":1,\"token\":\"02c5f3a039a74463972b835d1843dc8b\",\"updateBy\":\"admin\",\"updateTime\":\"2026-05-25 00:15:03\"}}', 0, NULL, '2026-05-25 00:15:02', 5),
(798, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/999999/qr-tokens', '127.0.0.1', '内网IP', '999999 {\"actionType\":\"checkin\",\"expireMinutes\":60}', NULL, 1, '活动不存在', '2026-05-25 00:15:48', 3),
(799, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/900001/qr-tokens', '127.0.0.1', '内网IP', '900001 {\"actionType\":\"checkin\",\"expireMinutes\":60}', NULL, 1, '只有已发布活动可以生成二维码', '2026-05-25 00:15:48', 3),
(800, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"invalid\",\"expireMinutes\":60}', NULL, 1, '二维码操作类型只能为签到或签退', '2026-05-25 00:15:48', 2),
(801, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkout_token/checkout', '127.0.0.1', '内网IP', '\"p4d_checkout_token\"', NULL, 1, '尚未签到，不能签退', '2026-05-25 00:22:45', 14),
(802, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkin_token/checkin', '127.0.0.1', '内网IP', '\"p4d_checkin_token\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 00:22:51\",\"createBy\":\"p3g_demo_volunteer\",\"id\":1,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":0,\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 00:22:51', 15),
(803, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkout_token/checkout', '127.0.0.1', '内网IP', '\"p4d_checkout_token\"', NULL, 1, '尚未签到，不能签退', '2026-05-25 00:24:01', 14),
(804, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkin_token/checkin', '127.0.0.1', '内网IP', '\"p4d_checkin_token\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 00:24:09\",\"createBy\":\"p3g_demo_volunteer\",\"id\":2,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":0,\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 00:24:09', 13),
(805, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkout_token/checkout', '127.0.0.1', '内网IP', '\"p4d_checkout_token\"', NULL, 1, '尚未签到，不能签退', '2026-05-25 09:10:21', 43),
(806, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkin_token/checkin', '127.0.0.1', '内网IP', '\"p4d_checkin_token\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 09:10:31\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 09:10:31\",\"id\":1,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":0,\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 09:10:30', 25),
(807, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkin_token/checkin', '127.0.0.1', '内网IP', '\"p4d_checkin_token\"', NULL, 1, '已完成签到，不能重复签到', '2026-05-25 09:10:40', 10),
(808, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkout_token/checkout', '127.0.0.1', '内网IP', '\"p4d_checkout_token\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 09:10:31\",\"checkoutMethod\":\"qr\",\"checkoutTime\":\"2026-05-25 09:10:59\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 09:10:31\",\"id\":1,\"params\":{},\"remark\":\"二维码签退\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":1,\"updateBy\":\"p3g_demo_volunteer\",\"updateTime\":\"2026-05-25 09:10:59\",\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 09:10:59', 14),
(809, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_checkout_token/checkout', '127.0.0.1', '内网IP', '\"p4d_checkout_token\"', NULL, 1, '已完成签退，不能重复签退', '2026-05-25 09:11:08', 15),
(810, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4d_pending_token/checkin', '127.0.0.1', '内网IP', '\"p4d_pending_token\"', NULL, 1, '报名未通过，不能扫码签到签退', '2026-05-25 09:11:28', 7),
(811, '志愿者档案', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppProfileController.updateProfile()', 'PUT', 1, 'powerpan', NULL, '/app/voluntary/profile', '127.0.0.1', '内网IP', '{\"emergencyContact\":\"\",\"emergencyPhone\":\"\",\"gender\":\"0\",\"idCard\":\"111111111111111111\",\"majorOrClass\":\"4\",\"organization\":\"eric\",\"params\":{},\"phone\":\"13503457292\",\"realName\":\"eric\",\"specialty\":\"\"}', '{\"msg\":\"操作成功\",\"approved\":false,\"code\":200,\"profile\":{\"auditReason\":\"\",\"auditStatus\":0,\"createBy\":\"powerpan\",\"createTime\":\"2026-05-25 09:14:16\",\"email\":\"\",\"emergencyContact\":\"\",\"emergencyPhone\":\"\",\"gender\":\"0\",\"id\":2,\"idCard\":\"111111111111111111\",\"majorOrClass\":\"4\",\"nickName\":\"powerpan\",\"organization\":\"eric\",\"params\":{},\"phone\":\"13503457292\",\"realName\":\"eric\",\"serviceCount\":0,\"specialty\":\"\",\"totalServiceMinutes\":0,\"updateBy\":\"powerpan\",\"updateTime\":\"2026-05-25 09:15:16\",\"userId\":3,\"userName\":\"powerpan\",\"userStatus\":\"0\"},\"auditStatus\":0}', 0, NULL, '2026-05-25 09:15:16', 13),
(812, '志愿者审核', 2, 'com.ruoyi.web.controller.manager.voluntary.VolManagerVolunteerController.audit()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/volunteers/2/audit', '127.0.0.1', '内网IP', '2 {\"auditReason\":\"\",\"auditStatus\":1}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"auditReason\":\"管理员审核通过\",\"auditStatus\":1,\"auditTime\":\"2026-05-25 09:15:34\",\"auditorId\":1,\"auditorName\":\"admin\",\"createBy\":\"powerpan\",\"createTime\":\"2026-05-25 09:14:16\",\"email\":\"\",\"emergencyContact\":\"\",\"emergencyPhone\":\"\",\"gender\":\"0\",\"id\":2,\"idCard\":\"111111111111111111\",\"majorOrClass\":\"4\",\"nickName\":\"powerpan\",\"organization\":\"eric\",\"params\":{},\"phone\":\"13503457292\",\"realName\":\"eric\",\"serviceCount\":0,\"specialty\":\"\",\"totalServiceMinutes\":0,\"updateBy\":\"admin\",\"updateTime\":\"2026-05-25 09:15:34\",\"userId\":3,\"userName\":\"powerpan\",\"userStatus\":\"0\"}}', 0, NULL, '2026-05-25 09:15:34', 11),
(813, '活动报名', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppActivityController.signup()', 'POST', 1, 'powerpan', NULL, '/app/voluntary/activities/2/signups', '127.0.0.1', '内网IP', '2 {\"applyReason\":\"11111\",\"experience\":\"222\"}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityStatus\":1,\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"applyReason\":\"11111\",\"createBy\":\"powerpan\",\"createTime\":\"2026-05-25 09:15:49\",\"experience\":\"222\",\"id\":3,\"organization\":\"eric\",\"params\":{},\"phone\":\"13503457292\",\"realName\":\"eric\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"volunteerUserId\":3}}', 0, NULL, '2026-05-25 09:15:49', 22),
(814, '报名筛选', 2, 'com.ruoyi.web.controller.manager.voluntary.VolManagerSignupController.review()', 'PUT', 1, 'admin', '运营中心', '/manager/voluntary/signups/3/review', '127.0.0.1', '内网IP', '3 {\"reviewReason\":\"\",\"status\":1}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityStatus\":1,\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"applyReason\":\"11111\",\"createBy\":\"powerpan\",\"createTime\":\"2026-05-25 09:15:49\",\"experience\":\"222\",\"id\":3,\"organization\":\"eric\",\"params\":{},\"phone\":\"13503457292\",\"realName\":\"eric\",\"reviewReason\":\"\",\"reviewTime\":\"2026-05-25 09:16:00\",\"reviewerId\":1,\"reviewerName\":\"admin\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":1,\"updateBy\":\"admin\",\"updateTime\":\"2026-05-25 09:16:00\",\"volunteerUserId\":3}}', 0, NULL, '2026-05-25 09:15:59', 20),
(815, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4e_checkin_token/checkin', '127.0.0.1', '内网IP', '\"p4e_checkin_token\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 09:22:05\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 09:22:05\",\"id\":2,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":0,\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 09:22:05', 25),
(816, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4e_checkout_token/checkout', '127.0.0.1', '内网IP', '\"p4e_checkout_token\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 09:22:05\",\"checkoutMethod\":\"qr\",\"checkoutTime\":\"2026-05-25 09:22:19\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 09:22:05\",\"id\":2,\"params\":{},\"remark\":\"二维码签退\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":1,\"updateBy\":\"p3g_demo_volunteer\",\"updateTime\":\"2026-05-25 09:22:19\",\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 09:22:18', 27),
(817, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/p4e_checkout_token/checkout', '127.0.0.1', '内网IP', '\"p4e_checkout_token\"', NULL, 1, '已完成签退，不能重复签退', '2026-05-25 09:23:07', 5),
(818, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 09:34:56\",\"expireTime\":\"2026-05-25 11:34:56\",\"id\":18,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=d6d6e61a01d94c19be7dc08a4702f591\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"d6d6e61a01d94c19be7dc08a4702f591\"}}', 0, NULL, '2026-05-25 09:34:55', 22),
(819, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkout\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkout\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 09:35:02\",\"expireTime\":\"2026-05-25 11:35:02\",\"id\":19,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=2be67b45595e459bb7e67f1d19d641ef\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"2be67b45595e459bb7e67f1d19d641ef\"}}', 0, NULL, '2026-05-25 09:35:02', 6),
(820, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/d6d6e61a01d94c19be7dc08a4702f591/checkin', '127.0.0.1', '内网IP', '\"d6d6e61a01d94c19be7dc08a4702f591\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 09:35:12\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 09:35:12\",\"id\":3,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":0,\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 09:35:11', 15),
(821, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/2be67b45595e459bb7e67f1d19d641ef/checkout', '127.0.0.1', '内网IP', '\"2be67b45595e459bb7e67f1d19d641ef\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 09:35:12\",\"checkoutMethod\":\"qr\",\"checkoutTime\":\"2026-05-25 09:35:21\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 09:35:12\",\"id\":3,\"params\":{},\"remark\":\"二维码签退\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":1,\"updateBy\":\"p3g_demo_volunteer\",\"updateTime\":\"2026-05-25 09:35:21\",\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 09:35:20', 29),
(822, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 09:36:25\",\"expireTime\":\"2026-05-25 11:36:25\",\"id\":20,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=7105523110574cd9b1221fc76fe0aa25\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"7105523110574cd9b1221fc76fe0aa25\"}}', 0, NULL, '2026-05-25 09:36:25', 16),
(823, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 09:36:38\",\"expireTime\":\"2026-05-25 11:36:38\",\"id\":21,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=07ad34fb544348538018ab0e8c32c29b\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"07ad34fb544348538018ab0e8c32c29b\"}}', 0, NULL, '2026-05-25 09:36:37', 7),
(824, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 09:54:03\",\"expireTime\":\"2026-05-25 11:54:03\",\"id\":22,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=7665b05a6d3b4c5498ef69f1dde3b6a3\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"7665b05a6d3b4c5498ef69f1dde3b6a3\"}}', 0, NULL, '2026-05-25 09:54:03', 5),
(825, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkout\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkout\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 09:54:12\",\"expireTime\":\"2026-05-25 11:54:12\",\"id\":23,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=ecad25fad3164eac9bd31fddf6aa63f7\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"ecad25fad3164eac9bd31fddf6aa63f7\"}}', 0, NULL, '2026-05-25 09:54:11', 5),
(826, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/7665b05a6d3b4c5498ef69f1dde3b6a3/checkin', '127.0.0.1', '内网IP', '\"7665b05a6d3b4c5498ef69f1dde3b6a3\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 09:56:39\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 09:56:39\",\"id\":4,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":0,\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 09:56:39', 10),
(827, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/ecad25fad3164eac9bd31fddf6aa63f7/checkout', '127.0.0.1', '内网IP', '\"ecad25fad3164eac9bd31fddf6aa63f7\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 09:56:39\",\"checkoutMethod\":\"qr\",\"checkoutTime\":\"2026-05-25 09:56:58\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 09:56:39\",\"id\":4,\"params\":{},\"remark\":\"二维码签退\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":2,\"status\":1,\"updateBy\":\"p3g_demo_volunteer\",\"updateTime\":\"2026-05-25 09:56:58\",\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 09:56:57', 15),
(828, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 09:59:17\",\"expireTime\":\"2026-05-25 11:59:17\",\"id\":24,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=1a325484822c4261b7195d01d63cbfbe\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"1a325484822c4261b7195d01d63cbfbe\"}}', 0, NULL, '2026-05-25 09:59:17', 12),
(829, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 10:01:02\",\"expireTime\":\"2026-05-25 12:01:02\",\"id\":25,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=e87e8fb1401948b69107b96f42bd0240\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"e87e8fb1401948b69107b96f42bd0240\"}}', 0, NULL, '2026-05-25 10:01:02', 16),
(830, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'powerpan', NULL, '/app/voluntary/scan/e87e8fb1401948b69107b96f42bd0240/checkin', '127.0.0.1', '内网IP', '\"e87e8fb1401948b69107b96f42bd0240\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 10:01:19\",\"createBy\":\"powerpan\",\"createTime\":\"2026-05-25 10:01:19\",\"id\":5,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":3,\"status\":0,\"volunteerOrganization\":\"eric\",\"volunteerPhone\":\"13503457292\",\"volunteerRealName\":\"eric\",\"volunteerUserId\":3}}', 0, NULL, '2026-05-25 10:01:19', 9),
(831, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkout\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkout\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 10:01:38\",\"expireTime\":\"2026-05-25 12:01:38\",\"id\":26,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=d3edc71772144f6ab01724cd2e030ba6\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"d3edc71772144f6ab01724cd2e030ba6\"}}', 0, NULL, '2026-05-25 10:01:37', 6),
(832, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'powerpan', NULL, '/app/voluntary/scan/d3edc71772144f6ab01724cd2e030ba6/checkout', '127.0.0.1', '内网IP', '\"d3edc71772144f6ab01724cd2e030ba6\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-06-12 12:00:00\",\"activityId\":2,\"activityStartTime\":\"2026-06-12 09:00:00\",\"activityTitle\":\"P3-H闭环验收活动\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 10:01:19\",\"checkoutMethod\":\"qr\",\"checkoutTime\":\"2026-05-25 10:01:45\",\"createBy\":\"powerpan\",\"createTime\":\"2026-05-25 10:01:19\",\"id\":5,\"params\":{},\"remark\":\"二维码签退\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"signupId\":3,\"status\":1,\"updateBy\":\"powerpan\",\"updateTime\":\"2026-05-25 10:01:45\",\"volunteerOrganization\":\"eric\",\"volunteerPhone\":\"13503457292\",\"volunteerRealName\":\"eric\",\"volunteerUserId\":3}}', 0, NULL, '2026-05-25 10:01:44', 37),
(833, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/3/qr-tokens', '127.0.0.1', '内网IP', '3 {\"actionType\":\"checkin\",\"expireMinutes\":60}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":3,\"activityTitle\":\"P4-H验收临时活动-p4h_20260525130348\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 13:03:49\",\"expireTime\":\"2026-05-25 14:03:49\",\"id\":27,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=ed6d3aefb0d14064897ecd2e914e3cc4\",\"serviceLocation\":\"P4-H验收点\",\"status\":0,\"token\":\"ed6d3aefb0d14064897ecd2e914e3cc4\"}}', 0, NULL, '2026-05-25 13:03:49', 9),
(834, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/3/qr-tokens', '127.0.0.1', '内网IP', '3 {\"actionType\":\"checkout\",\"expireMinutes\":60}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkout\",\"activityId\":3,\"activityTitle\":\"P4-H验收临时活动-p4h_20260525130348\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 13:03:49\",\"expireTime\":\"2026-05-25 14:03:49\",\"id\":28,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=5c3323360f754b3b865777b1f0d98f85\",\"serviceLocation\":\"P4-H验收点\",\"status\":0,\"token\":\"5c3323360f754b3b865777b1f0d98f85\"}}', 0, NULL, '2026-05-25 13:03:49', 11),
(835, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/ed6d3aefb0d14064897ecd2e914e3cc4/checkin', '127.0.0.1', '内网IP', '\"ed6d3aefb0d14064897ecd2e914e3cc4\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-05-26 15:03:49\",\"activityId\":3,\"activityStartTime\":\"2026-05-26 13:03:49\",\"activityTitle\":\"P4-H验收临时活动-p4h_20260525130348\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 13:03:50\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 13:03:50\",\"id\":6,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"P4-H验收点\",\"signupId\":4,\"status\":0,\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 13:03:49', 10),
(836, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/ed6d3aefb0d14064897ecd2e914e3cc4/checkin', '127.0.0.1', '内网IP', '\"ed6d3aefb0d14064897ecd2e914e3cc4\"', NULL, 1, '已完成签到，不能重复签到', '2026-05-25 13:03:49', 8),
(837, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/5c3323360f754b3b865777b1f0d98f85/checkout', '127.0.0.1', '内网IP', '\"5c3323360f754b3b865777b1f0d98f85\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-05-26 15:03:49\",\"activityId\":3,\"activityStartTime\":\"2026-05-26 13:03:49\",\"activityTitle\":\"P4-H验收临时活动-p4h_20260525130348\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 13:03:50\",\"checkoutMethod\":\"qr\",\"checkoutTime\":\"2026-05-25 13:03:50\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 13:03:50\",\"id\":6,\"params\":{},\"remark\":\"二维码签退\",\"serviceLocation\":\"P4-H验收点\",\"signupId\":4,\"status\":1,\"updateBy\":\"p3g_demo_volunteer\",\"updateTime\":\"2026-05-25 13:03:50\",\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 13:03:49', 9),
(838, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/5c3323360f754b3b865777b1f0d98f85/checkout', '127.0.0.1', '内网IP', '\"5c3323360f754b3b865777b1f0d98f85\"', NULL, 1, '已完成签退，不能重复签退', '2026-05-25 13:03:49', 5),
(839, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/4/qr-tokens', '127.0.0.1', '内网IP', '4 {\"actionType\":\"checkin\",\"expireMinutes\":60}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":4,\"activityTitle\":\"P4-H验收临时活动-p4h_20260525130517\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 13:05:19\",\"expireTime\":\"2026-05-25 14:05:19\",\"id\":29,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=64614b027ea548318bbc7295abb32374\",\"serviceLocation\":\"P4-H验收点\",\"status\":0,\"token\":\"64614b027ea548318bbc7295abb32374\"}}', 0, NULL, '2026-05-25 13:05:18', 8),
(840, '活动二维码', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/4/qr-tokens', '127.0.0.1', '内网IP', '4 {\"actionType\":\"checkout\",\"expireMinutes\":60}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkout\",\"activityId\":4,\"activityTitle\":\"P4-H验收临时活动-p4h_20260525130517\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 13:05:19\",\"expireTime\":\"2026-05-25 14:05:19\",\"id\":30,\"params\":{},\"remark\":\"管理端生成二维码令牌\",\"scanUrl\":\"http://localhost:8088/#/scan?token=8d722bcb0b8546d398f0198a60372462\",\"serviceLocation\":\"P4-H验收点\",\"status\":0,\"token\":\"8d722bcb0b8546d398f0198a60372462\"}}', 0, NULL, '2026-05-25 13:05:18', 11),
(841, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/64614b027ea548318bbc7295abb32374/checkin', '127.0.0.1', '内网IP', '\"64614b027ea548318bbc7295abb32374\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-05-26 15:05:18\",\"activityId\":4,\"activityStartTime\":\"2026-05-26 13:05:18\",\"activityTitle\":\"P4-H验收临时活动-p4h_20260525130517\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 13:05:19\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 13:05:19\",\"id\":7,\"params\":{},\"remark\":\"二维码签到\",\"serviceLocation\":\"P4-H验收点\",\"signupId\":6,\"status\":0,\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 13:05:19', 9),
(842, '二维码签到', 1, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkin()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/64614b027ea548318bbc7295abb32374/checkin', '127.0.0.1', '内网IP', '\"64614b027ea548318bbc7295abb32374\"', NULL, 1, '已完成签到，不能重复签到', '2026-05-25 13:05:19', 12),
(843, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/8d722bcb0b8546d398f0198a60372462/checkout', '127.0.0.1', '内网IP', '\"8d722bcb0b8546d398f0198a60372462\"', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"activityEndTime\":\"2026-05-26 15:05:18\",\"activityId\":4,\"activityStartTime\":\"2026-05-26 13:05:18\",\"activityTitle\":\"P4-H验收临时活动-p4h_20260525130517\",\"activityType\":\"community\",\"checkinMethod\":\"qr\",\"checkinTime\":\"2026-05-25 13:03:19\",\"checkoutMethod\":\"qr\",\"checkoutTime\":\"2026-05-25 13:05:19\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-25 13:03:19\",\"id\":7,\"params\":{},\"remark\":\"二维码签退\",\"serviceLocation\":\"P4-H验收点\",\"signupId\":6,\"status\":1,\"updateBy\":\"p3g_demo_volunteer\",\"updateTime\":\"2026-05-25 13:05:19\",\"volunteerOrganization\":\"P3-G演示组织\",\"volunteerPhone\":\"13900000001\",\"volunteerRealName\":\"P3G演示志愿者\",\"volunteerUserId\":2}}', 0, NULL, '2026-05-25 13:05:19', 11),
(844, '二维码签退', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppScanController.checkout()', 'POST', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/scan/8d722bcb0b8546d398f0198a60372462/checkout', '127.0.0.1', '内网IP', '\"8d722bcb0b8546d398f0198a60372462\"', NULL, 1, '已完成签退，不能重复签退', '2026-05-25 13:05:19', 10),
(845, '通知已读', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppNotificationController.read()', 'PUT', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/notifications/2/read', '127.0.0.1', '内网IP', '2', NULL, 1, '通知不存在或无权操作', '2026-05-25 14:18:51', 6),
(846, '通知已读', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppNotificationController.read()', 'PUT', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/notifications/1/read', '127.0.0.1', '内网IP', '1', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2026-05-25 14:19:11', 9),
(847, '通知已读', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppNotificationController.read()', 'PUT', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/notifications/1/read', '127.0.0.1', '内网IP', '1', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2026-05-25 14:19:40', 4),
(848, '通知全部已读', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppNotificationController.readAll()', 'PUT', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/notifications/read-all', '127.0.0.1', '内网IP', '', '{\"msg\":\"操作成功\",\"code\":200,\"data\":0}', 0, NULL, '2026-05-25 14:21:44', 3),
(850, '志愿者审核', 2, 'com.ruoyi.web.controller.manager.voluntary.VolManagerVolunteerController.audit()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/volunteers/1/audit', '127.0.0.1', '内网IP', '1 {\"auditReason\":\"P5-G 通知中心浏览器验证临时驳回，随后恢复通过。\",\"auditStatus\":2}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"auditReason\":\"P5-G 通知中心浏览器验证临时驳回，随后恢复通过。\",\"auditStatus\":2,\"auditTime\":\"2026-05-25 15:33:40\",\"auditorId\":1,\"auditorName\":\"admin\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-24 22:57:35\",\"email\":\"\",\"emergencyContact\":\"演示联系人\",\"emergencyPhone\":\"13900000002\",\"gender\":\"2\",\"id\":1,\"idCard\":\"110101199001011234\",\"majorOrClass\":\"软件工程演示班\",\"nickName\":\"P3G演示志愿者\",\"organization\":\"P3-G演示组织\",\"params\":{},\"phone\":\"13900000001\",\"realName\":\"P3G演示志愿者\",\"remark\":\"P3-G 用户端页面演示数据\",\"serviceCount\":0,\"specialty\":\"秩序维护、信息登记\",\"totalServiceMinutes\":0,\"updateBy\":\"admin\",\"updateTime\":\"2026-05-25 15:33:40\",\"userId\":2,\"userName\":\"p3g_demo_volunteer\",\"userStatus\":\"0\"}}', 0, NULL, '2026-05-25 15:33:40', 27),
(851, '志愿者审核', 2, 'com.ruoyi.web.controller.manager.voluntary.VolManagerVolunteerController.audit()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/volunteers/1/audit', '127.0.0.1', '内网IP', '1 {\"auditReason\":\"P5-G 通知中心浏览器验证后恢复通过。\",\"auditStatus\":1}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"auditReason\":\"P5-G 通知中心浏览器验证后恢复通过。\",\"auditStatus\":1,\"auditTime\":\"2026-05-25 15:34:59\",\"auditorId\":1,\"auditorName\":\"admin\",\"createBy\":\"p3g_demo_volunteer\",\"createTime\":\"2026-05-24 22:57:35\",\"email\":\"\",\"emergencyContact\":\"演示联系人\",\"emergencyPhone\":\"13900000002\",\"gender\":\"2\",\"id\":1,\"idCard\":\"110101199001011234\",\"majorOrClass\":\"软件工程演示班\",\"nickName\":\"P3G演示志愿者\",\"organization\":\"P3-G演示组织\",\"params\":{},\"phone\":\"13900000001\",\"realName\":\"P3G演示志愿者\",\"remark\":\"P3-G 用户端页面演示数据\",\"serviceCount\":0,\"specialty\":\"秩序维护、信息登记\",\"totalServiceMinutes\":0,\"updateBy\":\"admin\",\"updateTime\":\"2026-05-25 15:34:59\",\"userId\":2,\"userName\":\"p3g_demo_volunteer\",\"userStatus\":\"0\"}}', 0, NULL, '2026-05-25 15:34:58', 17),
(852, '通知已读', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppNotificationController.read()', 'PUT', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/notifications/5/read', '127.0.0.1', '内网IP', '5', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2026-05-25 15:35:28', 14),
(853, '通知全部已读', 2, 'com.ruoyi.web.controller.app.voluntary.VolAppNotificationController.readAll()', 'PUT', 1, 'p3g_demo_volunteer', NULL, '/app/voluntary/notifications/read-all', '127.0.0.1', '内网IP', '', '{\"msg\":\"操作成功\",\"code\":200,\"data\":1}', 0, NULL, '2026-05-25 15:36:02', 4),
(854, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '运营中心', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"voluntary/checkin/index\",\"createTime\":\"2026-05-24 23:53:10\",\"icon\":\"log\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2030,\"menuName\":\"签到管理\",\"menuType\":\"C\",\"orderNum\":4,\"params\":{},\"parentId\":2000,\"path\":\"checkin\",\"perms\":\"manager:voluntary:checkin:list\",\"query\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2026-05-25 15:46:43', 9),
(855, '活动签到令牌', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/1/qr-tokens', '127.0.0.1', '内网IP', '1 {\"actionType\":\"checkin\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":1,\"activityTitle\":\"P3-G用户端演示活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 19:24:58\",\"expireTime\":\"2026-05-25 21:24:58\",\"id\":31,\"params\":{},\"remark\":\"管理端生成签到令牌\",\"scanUrl\":\"http://localhost:8088/#/token-checkin?token=2626bbe68ec14feda791d7a7a98a0706\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"2626bbe68ec14feda791d7a7a98a0706\"}}', 0, NULL, '2026-05-25 19:24:57', 143),
(856, '活动签到令牌', 1, 'com.ruoyi.web.controller.manager.voluntary.VolManagerQrTokenController.generate()', 'POST', 1, 'admin', '运营中心', '/manager/voluntary/activities/2/qr-tokens', '127.0.0.1', '内网IP', '2 {\"actionType\":\"checkin\",\"expireMinutes\":120}', '{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"actionType\":\"checkin\",\"activityId\":2,\"activityTitle\":\"P3-H闭环验收活动\",\"createBy\":\"admin\",\"createTime\":\"2026-05-25 19:25:43\",\"expireTime\":\"2026-05-25 21:25:43\",\"id\":32,\"params\":{},\"remark\":\"管理端生成签到令牌\",\"scanUrl\":\"http://localhost:8088/#/token-checkin?token=5bad7cc4a730404c83bbacdd62e34022\",\"serviceLocation\":\"社区服务中心一楼大厅\",\"status\":0,\"token\":\"5bad7cc4a730404c83bbacdd62e34022\"}}', 0, NULL, '2026-05-25 19:25:43', 51);

-- --------------------------------------------------------

--
-- 表的结构 `sys_post`
--

CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';

--
-- 转存表中的数据 `sys_post`
--

INSERT INTO `sys_post` (`post_id`, `post_code`, `post_name`, `post_sort`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, 'ceo', '平台负责人', 1, '0', 'admin', '2026-05-24 22:34:44', '', NULL, ''),
(2, 'audit', '审核员', 2, '0', 'admin', '2026-05-24 22:34:44', '', NULL, '');

-- --------------------------------------------------------

--
-- 表的结构 `sys_read_range`
--

CREATE TABLE `sys_read_range` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `type_id` bigint(20) NOT NULL COMMENT '兼容旧用户编辑页的类型ID',
  `allow_preview` char(1) DEFAULT '1' COMMENT '允许预览',
  `allow_download` char(1) DEFAULT '1' COMMENT '允许下载'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='兼容旧用户编辑页的阅读范围';

-- --------------------------------------------------------

--
-- 表的结构 `sys_role`
--

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

--
-- 转存表中的数据 `sys_role`
--

INSERT INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `menu_check_strictly`, `dept_check_strictly`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '超级管理员'),
(2, '平台管理员', 'voluntary_admin', 2, '1', 1, 1, '0', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '志愿活动管理系统运营管理'),
(3, '普通用户', 'volunteer', 3, '1', 1, 1, '0', '0', 'admin', '2026-05-24 22:34:44', '', NULL, '志愿者');

-- --------------------------------------------------------

--
-- 表的结构 `sys_role_dept`
--

CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和部门关联表';

-- --------------------------------------------------------

--
-- 表的结构 `sys_role_menu`
--

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';

--
-- 转存表中的数据 `sys_role_menu`
--

INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(2, 2000),
(2, 2001),
(2, 2002),
(2, 2003),
(2, 2004),
(2, 2005),
(2, 2010),
(2, 2011),
(2, 2012),
(2, 2013),
(2, 2020),
(2, 2021),
(2, 2022),
(2, 2023),
(2, 2030),
(2, 2031),
(2, 2032),
(2, 2033),
(2, 2040),
(2, 2041),
(2, 2042),
(2, 2050),
(2, 2051),
(2, 2060),
(2, 2061);

-- --------------------------------------------------------

--
-- 表的结构 `sys_user`
--

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

--
-- 转存表中的数据 `sys_user`
--

INSERT INTO `sys_user` (`user_id`, `dept_id`, `user_name`, `nick_name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `status`, `del_flag`, `login_ip`, `login_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, 101, 'admin', '管理员', '00', '', '', '2', '', '$2a$10$Devb1IJNsf0hw1EhWo7/9.ulSduf6PXdMtM6YOR3u6WK0hS4os30W', '0', '0', '127.0.0.1', '2026-05-25 19:23:40', 'admin', '2026-05-24 22:34:44', '', '2026-05-25 19:23:40', '本地管理员，密码 admin123'),
(2, NULL, 'p3g_demo_volunteer', 'P3G演示志愿者', '00', '', '13900000001', '0', '', '$2a$10$g3ze0kwvrJjS8wKxdVOQi./pk/W6blfDW4iL63RexD6B5hw286dUC', '0', '0', '127.0.0.1', '2026-05-25 17:20:23', '', '2026-05-24 22:57:35', 'admin', '2026-05-25 17:20:22', NULL),
(3, NULL, 'powerpan', 'powerpan', '00', '', '', '0', '', '$2a$10$LSI0dN0u8OFPHlIk0h/qxenOGj0pXlsAbbJsfR67Yz06f08pB24.a', '0', '0', '127.0.0.1', '2026-05-25 16:59:30', '', '2026-05-25 09:14:16', '', '2026-05-25 16:59:30', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `sys_user_post`
--

CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与岗位关联表';

--
-- 转存表中的数据 `sys_user_post`
--

INSERT INTO `sys_user_post` (`user_id`, `post_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- 表的结构 `sys_user_role`
--

CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

--
-- 转存表中的数据 `sys_user_role`
--

INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 3),
(3, 3);

-- --------------------------------------------------------

--
-- 表的结构 `vol_activity`
--

CREATE TABLE `vol_activity` (
  `id` bigint(20) NOT NULL COMMENT '活动ID',
  `title` varchar(160) NOT NULL COMMENT '活动标题',
  `activity_type` varchar(64) DEFAULT '' COMMENT '活动类型',
  `cover_url` varchar(255) DEFAULT '' COMMENT '封面图',
  `service_location` varchar(255) NOT NULL COMMENT '服务地点',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `signup_start_time` datetime NOT NULL COMMENT '报名开始时间',
  `signup_end_time` datetime NOT NULL COMMENT '报名截止时间',
  `recruit_count` int(11) NOT NULL DEFAULT '0' COMMENT '招募人数',
  `approved_count` int(11) NOT NULL DEFAULT '0' COMMENT '已通过报名人数',
  `service_target` varchar(255) DEFAULT '' COMMENT '服务对象',
  `content` text COMMENT '活动内容',
  `requirements` varchar(1000) DEFAULT '' COMMENT '报名要求',
  `manager_name` varchar(64) DEFAULT '' COMMENT '活动负责人',
  `manager_phone` varchar(32) DEFAULT '' COMMENT '负责人联系电话',
  `max_service_minutes` int(11) DEFAULT NULL COMMENT '最大可计入服务分钟数',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '活动状态（0草稿 1已发布 2已结束 3已下架 4已取消）',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='志愿活动表';

--
-- 转存表中的数据 `vol_activity`
--

INSERT INTO `vol_activity` (`id`, `title`, `activity_type`, `cover_url`, `service_location`, `start_time`, `end_time`, `signup_start_time`, `signup_end_time`, `recruit_count`, `approved_count`, `service_target`, `content`, `requirements`, `manager_name`, `manager_phone`, `max_service_minutes`, `status`, `publish_time`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, 'P3-G用户端演示活动', 'community', '', '社区服务中心一楼大厅', '2026-06-10 09:00:00', '2026-06-10 11:00:00', '2026-05-24 00:00:00', '2026-06-09 18:00:00', 6, 0, '社区老人和居民', '为社区居民提供秩序引导、信息登记和便民服务支持。该活动用于 P3-G 用户端页面验收，可作为演示数据保留。', '需完成志愿者档案审核，能够按时到场并服从现场安排。', '志愿服务部', '13800000000', 120, 1, '2026-05-24 22:59:06', 'admin', '2026-05-24 22:59:06', 'admin', '2026-05-24 22:59:06', 'P3-G 用户端页面演示数据'),
(2, 'P3-H闭环验收活动', 'community', NULL, '社区服务中心一楼大厅', '2026-06-12 09:00:00', '2026-06-12 12:00:00', '2026-05-24 00:00:00', '2026-06-10 18:00:00', 2, 2, '社区居民', '面向社区居民开展秩序维护、咨询指引和现场协助服务。', '已审核通过志愿者，能够按时到场并服从现场安排。', 'P3H管理员', '13900000099', 180, 1, '2026-05-24 23:14:44', 'admin', '2026-05-24 23:14:32', 'admin', '2026-05-25 09:16:00', 'P3-H阶段验收发布');

-- --------------------------------------------------------

--
-- 表的结构 `vol_activity_qr_token`
--

CREATE TABLE `vol_activity_qr_token` (
  `id` bigint(20) NOT NULL COMMENT '二维码令牌ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `token` varchar(128) NOT NULL COMMENT '二维码随机令牌',
  `action_type` varchar(16) NOT NULL COMMENT '操作类型（checkin签到 checkout签退）',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '令牌状态（0有效 1失效）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动二维码令牌表';

--
-- 转存表中的数据 `vol_activity_qr_token`
--

INSERT INTO `vol_activity_qr_token` (`id`, `activity_id`, `token`, `action_type`, `expire_time`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(25, 2, 'e87e8fb1401948b69107b96f42bd0240', 'checkin', '2026-05-25 12:01:02', 1, 'admin', '2026-05-25 10:01:02', 'admin', '2026-05-25 19:25:43', '管理端生成签到令牌'),
(26, 2, 'd3edc71772144f6ab01724cd2e030ba6', 'checkout', '2026-05-25 12:01:38', 0, 'admin', '2026-05-25 10:01:38', NULL, NULL, '管理端生成签到令牌'),
(31, 1, '2626bbe68ec14feda791d7a7a98a0706', 'checkin', '2026-05-25 21:24:58', 0, 'admin', '2026-05-25 19:24:58', NULL, NULL, '管理端生成签到令牌'),
(32, 2, '5bad7cc4a730404c83bbacdd62e34022', 'checkin', '2026-05-25 21:25:43', 0, 'admin', '2026-05-25 19:25:43', NULL, NULL, '管理端生成签到令牌');

-- --------------------------------------------------------

--
-- 表的结构 `vol_activity_signup`
--

CREATE TABLE `vol_activity_signup` (
  `id` bigint(20) NOT NULL COMMENT '报名ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `volunteer_user_id` bigint(20) NOT NULL COMMENT '志愿者用户ID',
  `real_name` varchar(64) DEFAULT '' COMMENT '报名时姓名快照',
  `phone` varchar(32) DEFAULT '' COMMENT '报名时联系电话快照',
  `organization` varchar(128) DEFAULT '' COMMENT '报名时组织快照',
  `apply_reason` varchar(1000) DEFAULT '' COMMENT '报名理由',
  `experience` varchar(1000) DEFAULT '' COMMENT '相关经验',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '报名状态（0待筛选 1通过 2拒绝 3候补 4取消）',
  `review_reason` varchar(500) DEFAULT '' COMMENT '筛选意见',
  `reviewer_id` bigint(20) DEFAULT NULL COMMENT '处理人ID',
  `reviewer_name` varchar(64) DEFAULT '' COMMENT '处理人姓名快照',
  `review_time` datetime DEFAULT NULL COMMENT '处理时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '报名时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动报名表';

--
-- 转存表中的数据 `vol_activity_signup`
--

INSERT INTO `vol_activity_signup` (`id`, `activity_id`, `volunteer_user_id`, `real_name`, `phone`, `organization`, `apply_reason`, `experience`, `status`, `review_reason`, `reviewer_id`, `reviewer_name`, `review_time`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, 1, 2, 'P3G演示志愿者', '13900000001', 'P3-G演示组织', '希望参与社区志愿服务，协助现场秩序维护。', '具备活动签到引导和信息登记经验。', 0, NULL, NULL, NULL, NULL, 'p3g_demo_volunteer', '2026-05-24 23:01:21', NULL, NULL, NULL),
(2, 2, 2, 'P3G演示志愿者', '13900000001', 'P3-G演示组织', '参加P3-H闭环验收活动，协助社区现场服务。', '已完成P3-G用户端报名演示。', 1, 'P3-H验收通过，进入活动服务名单。', 1, 'admin', '2026-05-24 23:15:46', 'p3g_demo_volunteer', '2026-05-24 23:15:35', 'admin', '2026-05-24 23:15:46', NULL),
(3, 2, 3, 'eric', '13503457292', 'eric', '11111', '222', 1, '', 1, 'admin', '2026-05-25 09:16:00', 'powerpan', '2026-05-25 09:15:49', 'admin', '2026-05-25 09:16:00', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `vol_audit_record`
--

CREATE TABLE `vol_audit_record` (
  `id` bigint(20) NOT NULL COMMENT '审核记录ID',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `auditor_name` varchar(64) DEFAULT '' COMMENT '审核人姓名快照',
  `target_type` varchar(32) NOT NULL COMMENT '目标类型（volunteer activity service_record）',
  `target_id` bigint(20) NOT NULL COMMENT '目标ID',
  `target_user_id` bigint(20) DEFAULT NULL COMMENT '目标用户ID',
  `before_status` varchar(16) DEFAULT '' COMMENT '操作前状态',
  `audit_status` varchar(16) NOT NULL COMMENT '审核或处理结果',
  `audit_reason` varchar(500) DEFAULT '' COMMENT '审核意见或处理原因',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审核记录表';

--
-- 转存表中的数据 `vol_audit_record`
--

INSERT INTO `vol_audit_record` (`id`, `auditor_id`, `auditor_name`, `target_type`, `target_id`, `target_user_id`, `before_status`, `audit_status`, `audit_reason`, `create_by`, `create_time`, `remark`) VALUES
(1, NULL, NULL, 'volunteer', 1, 2, '', '0', '志愿者注册后进入待审核状态', 'p3g_demo_volunteer', '2026-05-24 22:57:35', NULL),
(2, 1, 'admin', 'volunteer', 1, 2, '0', '1', 'P3-G 页面验证通过', 'admin', '2026-05-24 22:59:06', 'P3-G 用户端页面演示数据'),
(3, NULL, NULL, 'volunteer', 2, 3, '', '0', '志愿者注册后进入待审核状态', 'powerpan', '2026-05-25 09:14:16', NULL),
(4, 1, 'admin', 'volunteer', 2, 3, '0', '1', '管理员审核通过', 'admin', '2026-05-25 09:15:34', NULL),
(11, 1, 'admin', 'volunteer', 1, 2, '1', '2', 'P5-G 通知中心浏览器验证临时驳回，随后恢复通过。', 'admin', '2026-05-25 15:33:40', NULL),
(12, 1, 'admin', 'volunteer', 1, 2, '2', '1', 'P5-G 通知中心浏览器验证后恢复通过。', 'admin', '2026-05-25 15:34:59', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `vol_checkin_record`
--

CREATE TABLE `vol_checkin_record` (
  `id` bigint(20) NOT NULL COMMENT '签到签退记录ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `signup_id` bigint(20) NOT NULL COMMENT '报名ID',
  `volunteer_user_id` bigint(20) NOT NULL COMMENT '志愿者用户ID',
  `checkin_time` datetime DEFAULT NULL COMMENT '签到时间',
  `checkout_time` datetime DEFAULT NULL COMMENT '签退时间',
  `checkin_method` varchar(32) DEFAULT '' COMMENT '签到方式（qr manual）',
  `checkout_method` varchar(32) DEFAULT '' COMMENT '签退方式（qr manual）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '签到状态（0已签到 1已签退 2异常 3人工确认）',
  `abnormal_reason` varchar(500) DEFAULT '' COMMENT '异常原因',
  `manual_reason` varchar(500) DEFAULT '' COMMENT '人工处理原因',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '人工处理人ID',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到签退记录表';

--
-- 转存表中的数据 `vol_checkin_record`
--

INSERT INTO `vol_checkin_record` (`id`, `activity_id`, `signup_id`, `volunteer_user_id`, `checkin_time`, `checkout_time`, `checkin_method`, `checkout_method`, `status`, `abnormal_reason`, `manual_reason`, `operator_id`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(5, 2, 3, 3, '2026-05-25 10:01:19', '2026-05-25 10:01:45', 'qr', 'qr', 1, NULL, NULL, NULL, 'powerpan', '2026-05-25 10:01:19', 'powerpan', '2026-05-25 10:01:45', '令牌签退');

-- --------------------------------------------------------

--
-- 表的结构 `vol_notification`
--

CREATE TABLE `vol_notification` (
  `id` bigint(20) NOT NULL COMMENT '通知ID',
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
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务通知表';

--
-- 转存表中的数据 `vol_notification`
--

INSERT INTO `vol_notification` (`id`, `receiver_user_id`, `actor_user_id`, `notice_type`, `target_type`, `target_id`, `title`, `content`, `action_url`, `status`, `read_time`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(4, 2, 1, 'volunteer_audit', 'volunteer', 1, '志愿者档案审核驳回', '你的志愿者档案审核未通过，请根据审核意见修改资料后重新提交。 审核意见：P5-G 通知中心浏览器验证临时驳回，随后恢复通过。', '/me', 1, '2026-05-25 15:36:02', 'admin', '2026-05-25 15:33:40', 'p3g_demo_volunteer', '2026-05-25 15:36:02', NULL),
(5, 2, 1, 'volunteer_audit', 'volunteer', 1, '志愿者档案审核通过', '你的志愿者档案已审核通过，可以报名参与活动。 审核意见：P5-G 通知中心浏览器验证后恢复通过。', '/me', 1, '2026-05-25 15:35:28', 'admin', '2026-05-25 15:34:59', 'p3g_demo_volunteer', '2026-05-25 15:35:28', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `vol_service_record`
--

CREATE TABLE `vol_service_record` (
  `id` bigint(20) NOT NULL COMMENT '服务记录ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `signup_id` bigint(20) NOT NULL COMMENT '报名ID',
  `checkin_record_id` bigint(20) NOT NULL COMMENT '签到签退记录ID',
  `volunteer_user_id` bigint(20) NOT NULL COMMENT '志愿者用户ID',
  `service_date` date NOT NULL COMMENT '服务日期',
  `start_time` datetime NOT NULL COMMENT '计入开始时间',
  `end_time` datetime NOT NULL COMMENT '计入结束时间',
  `service_minutes` int(11) NOT NULL DEFAULT '0' COMMENT '计入服务分钟数',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '服务记录状态（0待确认 1有效 2异常 3作废）',
  `confirm_user_id` bigint(20) DEFAULT NULL COMMENT '确认人ID',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认时间',
  `adjust_reason` varchar(500) DEFAULT '' COMMENT '修正原因',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务记录表';

--
-- 转存表中的数据 `vol_service_record`
--

INSERT INTO `vol_service_record` (`id`, `activity_id`, `signup_id`, `checkin_record_id`, `volunteer_user_id`, `service_date`, `start_time`, `end_time`, `service_minutes`, `status`, `confirm_user_id`, `confirm_time`, `adjust_reason`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(4, 2, 3, 5, 3, '2026-05-25', '2026-05-25 10:01:19', '2026-05-25 10:01:45', 1, 1, NULL, NULL, NULL, 'powerpan', '2026-05-25 10:01:45', NULL, NULL, '令牌签退自动生成服务记录');

-- --------------------------------------------------------

--
-- 表的结构 `vol_volunteer_profile`
--

CREATE TABLE `vol_volunteer_profile` (
  `id` bigint(20) NOT NULL COMMENT '档案ID',
  `user_id` bigint(20) NOT NULL COMMENT '系统用户ID',
  `real_name` varchar(64) DEFAULT '' COMMENT '真实姓名',
  `gender` char(1) DEFAULT '2' COMMENT '性别（0男 1女 2未知）',
  `id_card` varchar(64) DEFAULT '' COMMENT '证件号',
  `phone` varchar(32) DEFAULT '' COMMENT '联系电话',
  `organization` varchar(128) DEFAULT '' COMMENT '所属组织',
  `major_or_class` varchar(128) DEFAULT '' COMMENT '学院班级或社区分组',
  `specialty` varchar(500) DEFAULT '' COMMENT '服务特长',
  `emergency_contact` varchar(64) DEFAULT '' COMMENT '紧急联系人',
  `emergency_phone` varchar(32) DEFAULT '' COMMENT '紧急联系电话',
  `audit_status` tinyint(1) DEFAULT '0' COMMENT '审核状态（0待审核 1通过 2驳回 3禁用）',
  `audit_reason` varchar(500) DEFAULT '' COMMENT '审核意见',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `auditor_name` varchar(64) DEFAULT '' COMMENT '审核人姓名快照',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `total_service_minutes` int(11) DEFAULT '0' COMMENT '累计有效服务分钟数',
  `service_count` int(11) DEFAULT '0' COMMENT '有效服务次数',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='志愿者档案表';

--
-- 转存表中的数据 `vol_volunteer_profile`
--

INSERT INTO `vol_volunteer_profile` (`id`, `user_id`, `real_name`, `gender`, `id_card`, `phone`, `organization`, `major_or_class`, `specialty`, `emergency_contact`, `emergency_phone`, `audit_status`, `audit_reason`, `auditor_id`, `auditor_name`, `audit_time`, `total_service_minutes`, `service_count`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(1, 2, 'P3G演示志愿者', '2', '110101199001011234', '13900000001', 'P3-G演示组织', '软件工程演示班', '秩序维护、信息登记', '演示联系人', '13900000002', 1, 'P5-G 通知中心浏览器验证后恢复通过。', 1, 'admin', '2026-05-25 15:34:59', 0, 0, 'p3g_demo_volunteer', '2026-05-24 22:57:35', 'admin', '2026-05-25 15:34:59', 'P3-G 用户端页面演示数据'),
(2, 3, 'eric', '0', '111111111111111111', '13503457292', 'eric', '4', '', '', '', 1, '管理员审核通过', 1, 'admin', '2026-05-25 09:15:34', 1, 1, 'powerpan', '2026-05-25 09:14:16', 'powerpan', '2026-05-25 10:01:45', NULL);

--
-- 转储表的索引
--

--
-- 表的索引 `gen_table`
--
ALTER TABLE `gen_table`
  ADD PRIMARY KEY (`table_id`);

--
-- 表的索引 `gen_table_column`
--
ALTER TABLE `gen_table_column`
  ADD PRIMARY KEY (`column_id`);

--
-- 表的索引 `sys_config`
--
ALTER TABLE `sys_config`
  ADD PRIMARY KEY (`config_id`);

--
-- 表的索引 `sys_dept`
--
ALTER TABLE `sys_dept`
  ADD PRIMARY KEY (`dept_id`);

--
-- 表的索引 `sys_dict_data`
--
ALTER TABLE `sys_dict_data`
  ADD PRIMARY KEY (`dict_code`);

--
-- 表的索引 `sys_dict_type`
--
ALTER TABLE `sys_dict_type`
  ADD PRIMARY KEY (`dict_id`),
  ADD UNIQUE KEY `dict_type` (`dict_type`);

--
-- 表的索引 `sys_job`
--
ALTER TABLE `sys_job`
  ADD PRIMARY KEY (`job_id`,`job_name`,`job_group`);

--
-- 表的索引 `sys_job_log`
--
ALTER TABLE `sys_job_log`
  ADD PRIMARY KEY (`job_log_id`);

--
-- 表的索引 `sys_logininfor`
--
ALTER TABLE `sys_logininfor`
  ADD PRIMARY KEY (`info_id`),
  ADD KEY `idx_sys_logininfor_s` (`status`),
  ADD KEY `idx_sys_logininfor_lt` (`login_time`);

--
-- 表的索引 `sys_menu`
--
ALTER TABLE `sys_menu`
  ADD PRIMARY KEY (`menu_id`);

--
-- 表的索引 `sys_notice`
--
ALTER TABLE `sys_notice`
  ADD PRIMARY KEY (`notice_id`);

--
-- 表的索引 `sys_oper_log`
--
ALTER TABLE `sys_oper_log`
  ADD PRIMARY KEY (`oper_id`),
  ADD KEY `idx_sys_oper_log_bt` (`business_type`),
  ADD KEY `idx_sys_oper_log_s` (`status`),
  ADD KEY `idx_sys_oper_log_ot` (`oper_time`);

--
-- 表的索引 `sys_post`
--
ALTER TABLE `sys_post`
  ADD PRIMARY KEY (`post_id`);

--
-- 表的索引 `sys_read_range`
--
ALTER TABLE `sys_read_range`
  ADD PRIMARY KEY (`user_id`,`type_id`);

--
-- 表的索引 `sys_role`
--
ALTER TABLE `sys_role`
  ADD PRIMARY KEY (`role_id`);

--
-- 表的索引 `sys_role_dept`
--
ALTER TABLE `sys_role_dept`
  ADD PRIMARY KEY (`role_id`,`dept_id`);

--
-- 表的索引 `sys_role_menu`
--
ALTER TABLE `sys_role_menu`
  ADD PRIMARY KEY (`role_id`,`menu_id`);

--
-- 表的索引 `sys_user`
--
ALTER TABLE `sys_user`
  ADD PRIMARY KEY (`user_id`);

--
-- 表的索引 `sys_user_post`
--
ALTER TABLE `sys_user_post`
  ADD PRIMARY KEY (`user_id`,`post_id`);

--
-- 表的索引 `sys_user_role`
--
ALTER TABLE `sys_user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`);

--
-- 表的索引 `vol_activity`
--
ALTER TABLE `vol_activity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_activity_public` (`status`,`start_time`,`activity_type`),
  ADD KEY `idx_activity_signup_time` (`signup_start_time`,`signup_end_time`),
  ADD KEY `idx_activity_title` (`title`);

--
-- 表的索引 `vol_activity_qr_token`
--
ALTER TABLE `vol_activity_qr_token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_qr_token` (`token`),
  ADD KEY `idx_qr_activity_action` (`activity_id`,`action_type`,`status`,`expire_time`);

--
-- 表的索引 `vol_activity_signup`
--
ALTER TABLE `vol_activity_signup`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_signup_activity_user` (`activity_id`,`volunteer_user_id`),
  ADD KEY `idx_signup_activity_status` (`activity_id`,`status`,`create_time`),
  ADD KEY `idx_signup_user_status` (`volunteer_user_id`,`status`,`create_time`);

--
-- 表的索引 `vol_audit_record`
--
ALTER TABLE `vol_audit_record`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_audit_target` (`target_type`,`target_id`),
  ADD KEY `idx_audit_user` (`target_user_id`),
  ADD KEY `idx_audit_time` (`create_time`);

--
-- 表的索引 `vol_checkin_record`
--
ALTER TABLE `vol_checkin_record`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_checkin_activity_user` (`activity_id`,`volunteer_user_id`),
  ADD KEY `idx_checkin_activity_status` (`activity_id`,`status`,`checkin_time`),
  ADD KEY `idx_checkin_user` (`volunteer_user_id`,`checkin_time`);

--
-- 表的索引 `vol_notification`
--
ALTER TABLE `vol_notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_notification_receiver_status` (`receiver_user_id`,`status`,`create_time`),
  ADD KEY `idx_notification_target` (`target_type`,`target_id`),
  ADD KEY `idx_notification_type` (`notice_type`,`create_time`);

--
-- 表的索引 `vol_service_record`
--
ALTER TABLE `vol_service_record`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_service_checkin` (`checkin_record_id`),
  ADD KEY `idx_service_user_date` (`volunteer_user_id`,`service_date`),
  ADD KEY `idx_service_activity` (`activity_id`,`status`),
  ADD KEY `idx_service_status` (`status`,`service_date`);

--
-- 表的索引 `vol_volunteer_profile`
--
ALTER TABLE `vol_volunteer_profile`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_volunteer_user` (`user_id`),
  ADD KEY `idx_volunteer_audit` (`audit_status`,`create_time`),
  ADD KEY `idx_volunteer_phone` (`phone`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `gen_table`
--
ALTER TABLE `gen_table`
  MODIFY `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号';

--
-- 使用表AUTO_INCREMENT `gen_table_column`
--
ALTER TABLE `gen_table_column`
  MODIFY `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号';

--
-- 使用表AUTO_INCREMENT `sys_config`
--
ALTER TABLE `sys_config`
  MODIFY `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键', AUTO_INCREMENT=7;

--
-- 使用表AUTO_INCREMENT `sys_dept`
--
ALTER TABLE `sys_dept`
  MODIFY `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id', AUTO_INCREMENT=103;

--
-- 使用表AUTO_INCREMENT `sys_dict_data`
--
ALTER TABLE `sys_dict_data`
  MODIFY `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码', AUTO_INCREMENT=182;

--
-- 使用表AUTO_INCREMENT `sys_dict_type`
--
ALTER TABLE `sys_dict_type`
  MODIFY `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键', AUTO_INCREMENT=110;

--
-- 使用表AUTO_INCREMENT `sys_job`
--
ALTER TABLE `sys_job`
  MODIFY `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID', AUTO_INCREMENT=2;

--
-- 使用表AUTO_INCREMENT `sys_job_log`
--
ALTER TABLE `sys_job_log`
  MODIFY `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID';

--
-- 使用表AUTO_INCREMENT `sys_logininfor`
--
ALTER TABLE `sys_logininfor`
  MODIFY `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID', AUTO_INCREMENT=362;

--
-- 使用表AUTO_INCREMENT `sys_menu`
--
ALTER TABLE `sys_menu`
  MODIFY `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID', AUTO_INCREMENT=2062;

--
-- 使用表AUTO_INCREMENT `sys_notice`
--
ALTER TABLE `sys_notice`
  MODIFY `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID';

--
-- 使用表AUTO_INCREMENT `sys_oper_log`
--
ALTER TABLE `sys_oper_log`
  MODIFY `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键', AUTO_INCREMENT=857;

--
-- 使用表AUTO_INCREMENT `sys_post`
--
ALTER TABLE `sys_post`
  MODIFY `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID', AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `sys_role`
--
ALTER TABLE `sys_role`
  MODIFY `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID', AUTO_INCREMENT=4;

--
-- 使用表AUTO_INCREMENT `sys_user`
--
ALTER TABLE `sys_user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID', AUTO_INCREMENT=9;

--
-- 使用表AUTO_INCREMENT `vol_activity`
--
ALTER TABLE `vol_activity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '活动ID', AUTO_INCREMENT=6;

--
-- 使用表AUTO_INCREMENT `vol_activity_qr_token`
--
ALTER TABLE `vol_activity_qr_token`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '二维码令牌ID', AUTO_INCREMENT=33;

--
-- 使用表AUTO_INCREMENT `vol_activity_signup`
--
ALTER TABLE `vol_activity_signup`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报名ID', AUTO_INCREMENT=9;

--
-- 使用表AUTO_INCREMENT `vol_audit_record`
--
ALTER TABLE `vol_audit_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '审核记录ID', AUTO_INCREMENT=13;

--
-- 使用表AUTO_INCREMENT `vol_checkin_record`
--
ALTER TABLE `vol_checkin_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到签退记录ID', AUTO_INCREMENT=8;

--
-- 使用表AUTO_INCREMENT `vol_notification`
--
ALTER TABLE `vol_notification`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID', AUTO_INCREMENT=6;

--
-- 使用表AUTO_INCREMENT `vol_service_record`
--
ALTER TABLE `vol_service_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '服务记录ID', AUTO_INCREMENT=7;

--
-- 使用表AUTO_INCREMENT `vol_volunteer_profile`
--
ALTER TABLE `vol_volunteer_profile`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '档案ID', AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
