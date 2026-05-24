<template>
  <div class="home-page">
    <section class="hero" aria-label="志愿活动首页">
      <div class="hero-copy">
        <p class="eyebrow">RuoYi Voluntary</p>
        <h1>志愿活动管理系统</h1>
        <p>
          当前处于 P1 工程骨架迁移阶段。系统后续将按阶段实现志愿者审核、活动发布、
          活动报名、二维码签到签退和服务时长统计。
        </p>
        <div class="hero-actions">
          <el-button type="primary" icon="el-icon-guide" @click="$router.push('/activities')">活动浏览</el-button>
          <el-button icon="el-icon-user" @click="$router.push('/me')">个人中心</el-button>
        </div>
      </div>
    </section>

    <section class="stage-section">
      <div class="stage-title">
        <h2>分阶段推进</h2>
        <p>当前页面只保留系统入口，不提前实现业务页面。</p>
      </div>
      <div class="stage-grid">
        <div v-for="item in stages" :key="item.name" class="stage-card">
          <span>{{ item.name }}</span>
          <strong>{{ item.title }}</strong>
          <p>{{ item.desc }}</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      stages: [
        { name: 'P1', title: '工程骨架', desc: '迁移 RuoYi 基础结构，保留最小可编译业务模块。' },
        { name: 'P2', title: '志愿者档案', desc: '实现注册、资料维护和管理员审核。' },
        { name: 'P3', title: '活动报名', desc: '实现活动发布、浏览、报名和筛选。' },
        { name: 'P4', title: '签到时长', desc: '实现二维码签到签退和服务记录统计。' }
      ]
    }
  }
}
</script>

<style scoped>
.home-page {
  background: #f6f8f7;
}
.hero {
  min-height: 420px;
  display: flex;
  align-items: center;
  padding: 72px 32px 48px;
  color: #14241f;
  background: linear-gradient(135deg, #e9f5ef, #f9fbfa);
}
.hero-copy {
  width: 100%;
  max-width: 960px;
  margin: 0 auto;
}
.eyebrow {
  margin: 0 0 12px;
  color: #2f8f83;
  font-weight: 700;
}
.hero h1 {
  margin: 0;
  font-size: 42px;
  line-height: 1.2;
}
.hero p {
  max-width: 720px;
  color: #52625d;
  line-height: 1.8;
}
.hero-actions {
  display: flex;
  gap: 12px;
  margin-top: 24px;
}
.stage-section {
  max-width: 1120px;
  margin: 0 auto;
  padding: 34px 24px 56px;
}
.stage-title {
  margin-bottom: 18px;
}
.stage-title h2 {
  margin: 0 0 6px;
}
.stage-title p {
  margin: 0;
  color: #63716d;
}
.stage-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 14px;
}
.stage-card {
  padding: 18px;
  border: 1px solid #dfe8e4;
  border-radius: 8px;
  background: #fff;
}
.stage-card span {
  color: #2f8f83;
  font-weight: 700;
}
.stage-card strong {
  display: block;
  margin-top: 8px;
}
.stage-card p {
  margin: 8px 0 0;
  color: #63716d;
  line-height: 1.7;
}
@media (max-width: 640px) {
  .hero {
    min-height: auto;
    padding: 42px 20px 34px;
  }
  .hero h1 {
    font-size: 30px;
  }
  .hero-actions {
    flex-direction: column;
  }
}
</style>
