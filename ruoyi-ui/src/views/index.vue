<template>
  <div class="app-container">
    <el-card shadow="never" class="welcome-card">
      <h2>志愿活动管理系统</h2>
      <p>
        当前已完成 P3 活动与报名阶段，管理端已开放志愿者审核、活动管理和报名筛选入口。
        二维码签到签退、服务记录和服务时长统计会在 P4-P5 阶段继续接入。
      </p>
      <el-alert
        title="当前已开放活动与报名管理"
        type="info"
        description="可在左侧“志愿管理”中维护志愿者档案，创建发布活动，并对报名人员执行通过、候补或拒绝筛选。"
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
