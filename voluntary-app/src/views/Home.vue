<template>
  <div class="home-page">
    <section class="hero" aria-label="志愿活动首页">
      <div class="hero-copy">
        <p class="eyebrow">RuoYi Voluntary</p>
        <h1>志愿活动管理系统</h1>
        <p>
          当前已推进到 P3 活动与报名阶段。志愿者可浏览已发布活动、提交报名申请，
          管理员可在后台发布活动并筛选报名人员。
        </p>
        <div class="hero-actions">
          <el-button type="primary" icon="el-icon-guide" @click="$router.push('/activities')">活动浏览</el-button>
          <el-button icon="el-icon-user" @click="$router.push('/me')">个人中心</el-button>
        </div>
      </div>
    </section>

    <section class="audit-section">
      <div v-if="!hasToken" class="audit-card audit-card-guest">
        <div class="audit-icon"><i class="el-icon-user"></i></div>
        <div class="audit-copy">
          <span class="audit-kicker">志愿者档案</span>
          <h2>登录后查看审核状态</h2>
          <p>完成注册并维护个人资料后，管理员会在后台进行审核。</p>
        </div>
        <div class="audit-actions">
          <el-button type="primary" icon="el-icon-user" @click="$router.push('/login')">登录</el-button>
          <el-button icon="el-icon-edit-outline" @click="$router.push('/register')">注册</el-button>
        </div>
      </div>

      <div v-else class="audit-card" :class="auditCardClass" v-loading="auditLoading">
        <div class="audit-icon"><i :class="auditIcon"></i></div>
        <div class="audit-copy">
          <span class="audit-kicker">我的审核状态</span>
          <div class="audit-title-row">
            <h2>{{ auditStatusLabel }}</h2>
            <el-tag :type="auditTagType" effect="dark">{{ approved ? '已具备资格' : '需处理' }}</el-tag>
          </div>
          <p>{{ auditStatusText }}</p>
          <p v-if="auditReason" class="audit-reason">
            <strong>审核意见</strong>{{ auditReason }}
          </p>
          <p v-if="auditLoadFailed" class="audit-reason">
            <strong>状态提示</strong>暂时无法读取审核状态，请稍后刷新或进入个人中心查看。
          </p>
        </div>
        <div class="audit-actions">
          <el-button type="primary" icon="el-icon-user" @click="$router.push('/me')">完善资料</el-button>
          <el-button icon="el-icon-refresh" :loading="auditLoading" @click="loadAuditStatus">刷新</el-button>
        </div>
      </div>
    </section>

    <section class="stage-section">
      <div class="stage-title">
        <h2>分阶段推进</h2>
        <p>当前已进入活动发布、浏览、报名和筛选闭环，签到签退与统计仍按后续阶段推进。</p>
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
import { getToken } from '@/utils/auth'
import { getVolunteerAuditStatus } from '@/api/voluntary/profile'

const STATUS_MAP = {
  0: {
    label: '待审核',
    tag: 'warning',
    icon: 'el-icon-time',
    card: 'audit-card-pending',
    text: '资料已提交，等待管理员审核。审核通过后将作为后续报名资格判断依据。'
  },
  1: {
    label: '已通过',
    tag: 'success',
    icon: 'el-icon-circle-check',
    card: 'audit-card-approved',
    text: '档案已通过审核，可继续关注活动浏览和后续报名入口。'
  },
  2: {
    label: '已驳回',
    tag: 'danger',
    icon: 'el-icon-warning-outline',
    card: 'audit-card-rejected',
    text: '档案审核未通过，请根据审核意见修改资料后重新提交。'
  },
  3: {
    label: '已禁用',
    tag: 'info',
    icon: 'el-icon-lock',
    card: 'audit-card-disabled',
    text: '档案当前已被禁用，请联系管理员确认处理原因。'
  }
}

export default {
  name: 'Home',
  data() {
    return {
      auditLoading: false,
      auditLoadFailed: false,
      auditStatus: null,
      auditReason: '',
      approved: false,
      stages: [
        { name: 'P1', title: '工程骨架', desc: '迁移 RuoYi 基础结构，完成编译、构建和三端启动验证。' },
        { name: 'P2', title: '志愿者档案', desc: '实现注册、资料维护、审核状态展示和管理员审核。' },
        { name: 'P3', title: '活动报名', desc: '接入活动浏览、活动详情、报名申请和我的报名。' },
        { name: 'P4', title: '签到时长', desc: '实现二维码签到签退和服务记录统计。' }
      ]
    }
  },
  computed: {
    hasToken() {
      return !!getToken()
    },
    auditStatusValue() {
      const value = Number(this.auditStatus)
      return Number.isFinite(value) ? value : 0
    },
    statusConfig() {
      return STATUS_MAP[this.auditStatusValue] || STATUS_MAP[0]
    },
    auditStatusLabel() {
      if (this.auditLoadFailed) {
        return '状态读取失败'
      }
      return this.statusConfig.label
    },
    auditStatusText() {
      return this.statusConfig.text
    },
    auditTagType() {
      return this.statusConfig.tag
    },
    auditIcon() {
      return this.auditLoadFailed ? 'el-icon-warning-outline' : this.statusConfig.icon
    },
    auditCardClass() {
      return this.auditLoadFailed ? 'audit-card-rejected' : this.statusConfig.card
    }
  },
  created() {
    this.loadAuditStatus()
  },
  methods: {
    loadAuditStatus() {
      if (!this.hasToken) {
        return
      }
      this.auditLoading = true
      this.auditLoadFailed = false
      getVolunteerAuditStatus({ noErrorMessage: true }).then((res) => {
        this.auditStatus = res.auditStatus
        this.auditReason = res.auditReason || ''
        this.approved = !!res.approved
      }).catch(() => {
        this.auditLoadFailed = true
      }).finally(() => {
        this.auditLoading = false
      })
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
.audit-section {
  max-width: 1120px;
  margin: -28px auto 0;
  padding: 0 24px;
  position: relative;
  z-index: 2;
}
.audit-card {
  display: grid;
  grid-template-columns: auto minmax(0, 1fr) auto;
  align-items: center;
  gap: 18px;
  min-height: 128px;
  padding: 22px;
  border: 1px solid #dfe8e4;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 12px 28px rgba(23, 33, 31, .07);
}
.audit-card-pending {
  border-left: 5px solid #e6a23c;
}
.audit-card-approved {
  border-left: 5px solid #2f8f83;
}
.audit-card-rejected {
  border-left: 5px solid #f56c6c;
}
.audit-card-disabled {
  border-left: 5px solid #909399;
}
.audit-card-guest {
  border-left: 5px solid #5f9ea0;
}
.audit-icon {
  width: 54px;
  height: 54px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  color: #fff;
  background: #2f8f83;
  font-size: 24px;
}
.audit-card-pending .audit-icon {
  background: #e6a23c;
}
.audit-card-rejected .audit-icon {
  background: #f56c6c;
}
.audit-card-disabled .audit-icon {
  background: #909399;
}
.audit-copy {
  min-width: 0;
}
.audit-kicker {
  display: block;
  color: #2f8f83;
  font-size: 13px;
  font-weight: 700;
}
.audit-title-row {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}
.audit-copy h2 {
  margin: 4px 0 0;
  color: #17211f;
  font-size: 24px;
  line-height: 1.25;
}
.audit-copy p {
  margin: 8px 0 0;
  color: #52625d;
  line-height: 1.7;
}
.audit-reason {
  padding-top: 2px;
}
.audit-reason strong {
  margin-right: 8px;
  color: #17211f;
}
.audit-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  justify-content: flex-end;
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
  .audit-section {
    margin-top: -16px;
    padding: 0 18px;
  }
  .audit-card {
    grid-template-columns: 1fr;
  }
  .audit-actions {
    justify-content: flex-start;
  }
}
</style>
