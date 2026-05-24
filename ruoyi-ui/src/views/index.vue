<template>
  <div class="app-container">
    <el-card shadow="never" class="welcome-card">
      <h2>志愿活动管理系统</h2>
      <p>
        当前处于 P2 志愿者账号与档案阶段，管理端已开放志愿者审核入口。
        活动管理、报名筛选、签到签退和服务时长统计会在后续阶段逐步接入。
      </p>
      <el-alert
        title="当前已开放志愿者审核"
        type="info"
        description="可在左侧“志愿管理 / 志愿者审核”中查询档案、维护资料、审核通过、驳回、禁用和启用志愿者。"
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
