<template>
  <div class="activity-detail-page" v-loading="loading">
    <el-button class="back-button" icon="el-icon-arrow-left" @click="$router.push('/activities')">返回活动列表</el-button>

    <el-empty v-if="!loading && !activity.id" description="活动不存在或未发布" />

    <template v-else>
      <section class="detail-hero" :style="coverStyle">
        <div class="hero-content">
          <div class="hero-tags">
            <el-tag effect="dark">{{ activityTypeLabel }}</el-tag>
            <el-tag :type="signupOpen ? 'success' : 'info'" effect="dark">{{ signupOpen ? '报名中' : signupStatusText }}</el-tag>
          </div>
          <h1>{{ activity.title }}</h1>
          <div class="hero-meta">
            <span><i class="el-icon-location-outline"></i>{{ activity.serviceLocation || '地点待补充' }}</span>
            <span><i class="el-icon-date"></i>{{ timeRange(activity.startTime, activity.endTime) }}</span>
            <span><i class="el-icon-user"></i>已通过 {{ Number(activity.approvedCount || 0) }}/{{ Number(activity.recruitCount || 0) }} 人</span>
          </div>
        </div>
      </section>

      <el-row :gutter="18" class="detail-layout">
        <el-col :xs="24" :md="16">
          <el-card shadow="never" class="detail-panel">
            <div slot="header" class="panel-head">
              <span><i class="el-icon-document"></i>活动内容</span>
            </div>
            <p class="rich-text">{{ activity.content || '暂无活动内容说明。' }}</p>
          </el-card>

          <el-card shadow="never" class="detail-panel">
            <div slot="header" class="panel-head">
              <span><i class="el-icon-tickets"></i>报名要求</span>
            </div>
            <p class="rich-text">{{ activity.requirements || '暂无额外报名要求。' }}</p>
          </el-card>
        </el-col>

        <el-col :xs="24" :md="8">
          <el-card shadow="never" class="side-card">
            <div slot="header" class="panel-head">
              <span><i class="el-icon-edit-outline"></i>参与报名</span>
            </div>

            <div class="signup-facts">
              <div>
                <span>报名时间</span>
                <strong>{{ timeRange(activity.signupStartTime, activity.signupEndTime) }}</strong>
              </div>
              <div>
                <span>服务对象</span>
                <strong>{{ activity.serviceTarget || '待补充' }}</strong>
              </div>
              <div>
                <span>服务时长上限</span>
                <strong>{{ maxServiceHours }}</strong>
              </div>
              <div>
                <span>负责人</span>
                <strong>{{ managerText }}</strong>
              </div>
            </div>

            <el-alert
              v-if="mySignup.id"
              class="status-alert"
              :type="signupStatusTag(mySignup.status)"
              :title="'我的报名状态：' + signupStatusLabel(mySignup.status)"
              :description="mySignup.reviewReason ? '处理意见：' + mySignup.reviewReason : ''"
              show-icon
              :closable="false"
            />

            <el-alert
              v-else-if="token && auditLoaded && !approved"
              class="status-alert"
              type="warning"
              title="志愿者档案尚未审核通过"
              description="请先进入个人中心完善资料并等待管理员审核，通过后才能提交报名。"
              show-icon
              :closable="false"
            />

            <el-alert
              v-else-if="!signupOpen"
              class="status-alert"
              type="info"
              :title="signupStatusText"
              description="当前活动不在报名开放时间内，暂不能提交报名。"
              show-icon
              :closable="false"
            />

            <el-button
              v-if="!token"
              class="wide-button"
              type="primary"
              icon="el-icon-user"
              @click="goLogin"
            >
              登录后报名
            </el-button>
            <el-button
              v-else-if="auditLoaded && !approved"
              class="wide-button"
              icon="el-icon-user"
              @click="$router.push('/me')"
            >
              查看个人中心
            </el-button>
            <el-button
              v-else
              class="wide-button"
              type="primary"
              icon="el-icon-edit-outline"
              :disabled="!canOpenSignup"
              :loading="submitting"
              @click="openSignupDialog"
            >
              {{ signupButtonText }}
            </el-button>
            <el-button
              v-if="mySignup.id"
              class="wide-button secondary"
              icon="el-icon-document"
              @click="$router.push('/signups')"
            >
              查看我的报名
            </el-button>
          </el-card>
        </el-col>
      </el-row>
    </template>

    <el-dialog
      title="提交活动报名"
      :visible.sync="signupDialogVisible"
      :width="dialogWidth"
      append-to-body
      @closed="resetSignupForm"
    >
      <el-form ref="signupForm" :model="signupForm" :rules="signupRules" label-width="96px">
        <el-form-item label="报名理由" prop="applyReason">
          <el-input
            v-model="signupForm.applyReason"
            type="textarea"
            :rows="4"
            maxlength="500"
            show-word-limit
            placeholder="请说明你希望参与该活动的原因"
          />
        </el-form-item>
        <el-form-item label="相关经验" prop="experience">
          <el-input
            v-model="signupForm.experience"
            type="textarea"
            :rows="3"
            maxlength="500"
            show-word-limit
            placeholder="可填写相关服务经历、技能或时间安排"
          />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="signupDialogVisible = false">取消</el-button>
        <el-button type="primary" icon="el-icon-check" :loading="submitting" @click="submitSignup">提交报名</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'
import { getVolunteerAuditStatus } from '@/api/voluntary/profile'
import { applyActivity, getActivity, listMySignups } from '@/api/voluntary/activity'

const ACTIVITY_TYPE_MAP = {
  community: { label: '社区服务', color: '#238276' },
  campus: { label: '校园服务', color: '#3c6f9f' },
  publicity: { label: '公益宣传', color: '#d78a2b' }
}

const SIGNUP_STATUS_MAP = {
  0: { label: '待筛选', tag: 'warning' },
  1: { label: '通过', tag: 'success' },
  2: { label: '拒绝', tag: 'danger' },
  3: { label: '候补', tag: 'info' },
  4: { label: '取消', tag: 'info' }
}

export default {
  name: 'ActivityDetail',
  data() {
    return {
      loading: false,
      submitting: false,
      auditLoaded: false,
      approved: false,
      activity: {},
      mySignup: {},
      signupDialogVisible: false,
      viewportWidth: typeof window === 'undefined' ? 1024 : window.innerWidth,
      signupForm: this.emptySignupForm(),
      signupRules: {
        applyReason: [
          { required: true, message: '报名理由不能为空', trigger: 'blur' },
          { min: 5, max: 500, message: '报名理由需在 5 到 500 个字符之间', trigger: 'blur' }
        ],
        experience: [
          { max: 500, message: '相关经验最多 500 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    token() {
      return getToken()
    },
    dialogWidth() {
      return this.viewportWidth <= 720 ? '92%' : '620px'
    },
    typeConfig() {
      return ACTIVITY_TYPE_MAP[this.activity.activityType] || ACTIVITY_TYPE_MAP.community
    },
    activityTypeLabel() {
      return this.typeConfig.label || this.activity.activityType || '志愿活动'
    },
    coverStyle() {
      if (this.activity.coverUrl) {
        return {
          backgroundImage: `linear-gradient(180deg, rgba(14, 24, 22, .28), rgba(14, 24, 22, .68)), url(${this.activity.coverUrl})`
        }
      }
      return {
        backgroundImage: `linear-gradient(135deg, ${this.typeConfig.color}, #5f9ea0)`
      }
    },
    signupOpen() {
      const now = new Date()
      const start = this.parseTime(this.activity.signupStartTime)
      const end = this.parseTime(this.activity.signupEndTime)
      return !!start && !!end && start <= now && now <= end
    },
    signupStatusText() {
      const now = new Date()
      const start = this.parseTime(this.activity.signupStartTime)
      const end = this.parseTime(this.activity.signupEndTime)
      if (start && now < start) {
        return '报名未开始'
      }
      if (end && now > end) {
        return '报名已截止'
      }
      return '暂不可报名'
    },
    remainingCount() {
      const total = Number(this.activity.recruitCount || 0)
      const approved = Number(this.activity.approvedCount || 0)
      return total > 0 ? Math.max(total - approved, 0) : 0
    },
    hasActiveSignup() {
      const status = Number(this.mySignup.status)
      return this.mySignup.id && [0, 1, 3].indexOf(status) !== -1
    },
    canOpenSignup() {
      return this.signupOpen && this.approved && !this.hasActiveSignup && this.remainingCount > 0
    },
    signupButtonText() {
      if (this.hasActiveSignup) {
        return '已提交报名'
      }
      if (this.remainingCount <= 0) {
        return '名额已满'
      }
      if (!this.signupOpen) {
        return this.signupStatusText
      }
      return '提交报名'
    },
    maxServiceHours() {
      const minutes = Number(this.activity.maxServiceMinutes || 0)
      if (minutes <= 0) {
        return '按实际签到签退计算'
      }
      return `${(minutes / 60).toFixed(1)} 小时`
    },
    managerText() {
      const name = this.activity.managerName || '待补充'
      const phone = this.activity.managerPhone ? ` / ${this.activity.managerPhone}` : ''
      return `${name}${phone}`
    }
  },
  created() {
    this.loadPage()
  },
  mounted() {
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    emptySignupForm() {
      return {
        applyReason: '',
        experience: ''
      }
    },
    loadPage() {
      this.loading = true
      getActivity(this.$route.params.id).then((res) => {
        this.activity = res.data || res || {}
        return Promise.all([this.loadAuditStatus(), this.loadMySignup()])
      }).then(() => {
        if (this.$route.query.signup === '1' && this.canOpenSignup) {
          this.signupDialogVisible = true
        }
      }).finally(() => {
        this.loading = false
      })
    },
    loadAuditStatus() {
      if (!this.token) {
        this.auditLoaded = false
        this.approved = false
        return Promise.resolve()
      }
      return getVolunteerAuditStatus({ noErrorMessage: true }).then((res) => {
        this.approved = !!res.approved
        this.auditLoaded = true
      }).catch(() => {
        this.approved = false
        this.auditLoaded = true
      })
    },
    loadMySignup() {
      if (!this.token || !this.activity.id) {
        this.mySignup = {}
        return Promise.resolve()
      }
      return listMySignups({
        pageNum: 1,
        pageSize: 1,
        activityId: this.activity.id
      }).then((res) => {
        const rows = res.rows || []
        this.mySignup = rows.length > 0 ? rows[0] : {}
      }).catch(() => {
        this.mySignup = {}
      })
    },
    openSignupDialog() {
      if (!this.canOpenSignup) {
        return
      }
      this.signupDialogVisible = true
    },
    submitSignup() {
      this.$refs.signupForm.validate((valid) => {
        if (!valid) {
          return
        }
        this.submitting = true
        applyActivity(this.activity.id, this.signupForm).then((res) => {
          this.mySignup = res.data || res || {}
          this.signupDialogVisible = false
          this.$message.success('报名已提交，等待管理员筛选')
          this.loadPage()
        }).finally(() => {
          this.submitting = false
        })
      })
    },
    resetSignupForm() {
      this.signupForm = this.emptySignupForm()
      this.$nextTick(() => {
        if (this.$refs.signupForm) {
          this.$refs.signupForm.clearValidate()
        }
      })
    },
    goLogin() {
      this.$router.push({ path: '/login', query: { redirect: this.$route.fullPath } })
    },
    handleResize() {
      this.viewportWidth = window.innerWidth
    },
    parseTime(value) {
      if (!value) {
        return null
      }
      const date = new Date(String(value).replace(/-/g, '/'))
      return Number.isNaN(date.getTime()) ? null : date
    },
    formatDate(value) {
      if (!value) {
        return '待定'
      }
      return String(value).slice(0, 16)
    },
    timeRange(start, end) {
      return `${this.formatDate(start)} - ${this.formatDate(end)}`
    },
    signupStatusLabel(status) {
      const value = Number(status)
      return (SIGNUP_STATUS_MAP[value] && SIGNUP_STATUS_MAP[value].label) || '未知'
    },
    signupStatusTag(status) {
      const value = Number(status)
      return (SIGNUP_STATUS_MAP[value] && SIGNUP_STATUS_MAP[value].tag) || 'info'
    }
  }
}
</script>

<style scoped>
.activity-detail-page {
  max-width: 1180px;
  margin: 0 auto;
  padding: 28px 20px 52px;
}

.back-button {
  margin-bottom: 16px;
}

.detail-hero {
  min-height: 320px;
  display: flex;
  align-items: flex-end;
  overflow: hidden;
  border-radius: 8px;
  background-size: cover;
  background-position: center;
  color: #fff;
}

.hero-content {
  width: 100%;
  padding: 34px;
}

.hero-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.detail-hero h1 {
  max-width: 860px;
  margin: 16px 0 0;
  font-size: 34px;
  line-height: 1.22;
  word-break: break-word;
}

.hero-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 10px 20px;
  margin-top: 16px;
}

.hero-meta span {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  min-width: 0;
  line-height: 1.5;
}

.detail-layout {
  margin-top: 18px;
}

.detail-panel + .detail-panel {
  margin-top: 18px;
}

.detail-panel,
.side-card {
  border-radius: 8px;
}

.panel-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  font-weight: 700;
}

.panel-head span {
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.rich-text {
  margin: 0;
  color: #344440;
  line-height: 1.9;
  white-space: pre-wrap;
  word-break: break-word;
}

.signup-facts {
  display: grid;
  gap: 12px;
}

.signup-facts div {
  padding-bottom: 12px;
  border-bottom: 1px solid var(--voluntary-border);
}

.signup-facts span {
  display: block;
  color: var(--voluntary-text-muted);
  font-size: 13px;
}

.signup-facts strong {
  display: block;
  margin-top: 6px;
  color: var(--voluntary-text);
  line-height: 1.55;
  word-break: break-word;
}

.status-alert {
  margin-top: 16px;
}

.wide-button {
  width: 100%;
  margin-top: 16px;
}

.wide-button.secondary {
  margin-left: 0;
  margin-top: 10px;
}

@media (max-width: 760px) {
  .activity-detail-page {
    padding: 22px 14px 42px;
  }

  .detail-hero {
    min-height: 260px;
  }

  .hero-content {
    padding: 24px;
  }

  .detail-hero h1 {
    font-size: 27px;
  }

  .detail-layout .el-col + .el-col {
    margin-top: 18px;
  }
}
</style>
