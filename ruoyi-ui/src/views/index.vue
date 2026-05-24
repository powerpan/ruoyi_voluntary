<template>
  <div class="app-container">
    <el-card shadow="never" class="welcome-card">
      <h2>志愿活动管理系统</h2>
      <p>
        当前处于 P1 工程骨架迁移阶段，后台仅保留 RuoYi 基础能力和系统管理入口。
        志愿者审核、活动管理、报名筛选、签到签退和服务时长统计会在后续阶段逐步接入。
      </p>
      <el-alert
        title="业务菜单暂未开放"
        type="info"
        description="P2 开始将按文档逐步增加志愿者管理、活动管理、报名管理、签到签退和统计菜单。"
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
