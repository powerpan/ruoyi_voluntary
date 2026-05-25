<template>
  <div class="app-container">
    <el-card shadow="never" class="welcome-card">
      <h2>志愿活动管理系统</h2>
      <p>
        当前核心业务闭环已完成，管理端已开放志愿者审核、活动管理、报名筛选、
        二维码签到签退、服务记录、数据统计、通知记录和导出入口。
      </p>
      <el-alert
        title="当前进入最终验证与交付整理"
        type="info"
        description="可在左侧“志愿管理”中完成志愿者审核、活动发布、报名管理、签到签退、服务时长统计和业务通知查询。"
        show-icon
        :closable="false"
      />
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Index'
}
</script>

<style scoped>
.welcome-card {
  max-width: 920px;
}
.welcome-card h2 {
  margin: 0 0 12px;
}
.welcome-card p {
  margin: 0 0 18px;
  line-height: 1.8;
  color: #606266;
}
</style>
