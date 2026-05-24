<template>
  <div class="page notifications-page">
    <div class="page-head">
      <div>
        <h1>通知中心</h1>
        <p>后续用于接收报名审核、活动提醒、签到异常和服务时长确认通知。</p>
      </div>
      <el-button icon="el-icon-refresh" disabled>刷新</el-button>
    </div>

    <el-card shadow="never" class="placeholder-card">
      <el-empty description="P2 之后接入通知接口" />
      <p>
        当前 P1 仅保留页面入口，避免沿用参考项目的旧业务接口。
        通知类型会随志愿者审核、报名管理和签到签退模块逐步补齐。
      </p>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Notifications'
}
</script>

<style scoped>
.notifications-page {
  max-width: 980px;
  margin: 0 auto;
  padding: 32px 20px 56px;
}
.page-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 14px;
  margin-bottom: 18px;
}
.page-head h1 {
  margin: 0 0 6px;
}
.page-head p {
  margin: 0;
  color: #63716d;
}
.placeholder-card {
  border: 1px solid #dfe8e4;
  border-radius: 8px;
}
.placeholder-card p {
  max-width: 620px;
  margin: 0 auto 12px;
  color: #63716d;
  line-height: 1.8;
  text-align: center;
}
@media (max-width: 640px) {
  .page-head {
    align-items: flex-start;
    flex-direction: column;
  }
}
</style>
