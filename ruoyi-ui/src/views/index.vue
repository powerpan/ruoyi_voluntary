<template>
  <div class="app-container">
    <el-card shadow="never" class="welcome-card">
      <h2>志愿活动管理系统</h2>
      <p>
        集中管理志愿者档案、活动发布、报名筛选、二维码签到签退、服务记录、
        数据统计和业务通知。
      </p>
      <el-alert
        title="欢迎使用志愿活动管理系统"
        type="info"
        description="可在左侧“志愿管理”中处理志愿者审核、活动维护、报名人员筛选、签到记录和服务时长统计。"
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
