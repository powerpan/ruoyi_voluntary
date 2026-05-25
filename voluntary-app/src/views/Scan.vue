<template>
  <div class="scan-page">
    <section class="voluntary-page-title">
      <div>
        <h1>{{ pageTitle }}</h1>
        <p>{{ pageSubtitle }}</p>
      </div>
      <div class="voluntary-action-row">
        <el-button icon="el-icon-refresh" :loading="loading" @click="loadScanInfo">刷新</el-button>
        <el-button icon="el-icon-s-management" @click="$router.push('/signups')">我的报名</el-button>
      </div>
    </section>

    <el-alert
      v-if="errorMessage"
      class="scan-alert"
      type="error"
      show-icon
      :closable="false"
      :title="errorMessage"
    />

    <section v-loading="loading" class="scan-layout">
      <div class="scan-status-card" :class="statusClass">
        <div class="status-icon"><i :class="statusIcon"></i></div>
        <span>{{ actionLabel }}</span>
        <h2>{{ scanMessage }}</h2>
        <p>{{ statusHint }}</p>
        <div v-if="!token" class="manual-token-form">
          <el-input
            v-model.trim="manualScanValue"
            clearable
            prefix-icon="el-icon-link"
            placeholder="粘贴扫码地址或令牌"
            @keyup.enter.native="openManualToken"
          />
          <el-button type="primary" icon="el-icon-position" @click="openManualToken">打开</el-button>
        </div>
        <el-button
          v-else
          class="primary-action"
          type="primary"
          size="medium"
          :icon="actionIcon"
          :loading="submitting"
          :disabled="!canSubmit"
          @click="submitAction"
        >
          {{ actionButtonText }}
        </el-button>
      </div>

      <div class="scan-detail-stack">
        <el-card shadow="never" class="scan-card">
          <div slot="header" class="scan-card-head">
            <span><i class="el-icon-guide"></i>活动信息</span>
            <el-tag :type="actionTagType" effect="plain">{{ actionLabel }}</el-tag>
          </div>
          <div v-if="activity" class="activity-panel">
            <div>
              <span class="field-label">活动名称</span>
              <strong>{{ activity.title || '-' }}</strong>
            </div>
            <div>
              <span class="field-label">服务地点</span>
              <p>{{ activity.serviceLocation || '-' }}</p>
            </div>
            <div>
              <span class="field-label">活动时间</span>
              <p>{{ timeRange(activity.startTime, activity.endTime) }}</p>
            </div>
            <div>
              <span class="field-label">可计入上限</span>
              <p>{{ maxMinutesText }}</p>
            </div>
          </div>
          <el-empty v-else description="暂未读取到活动信息" />
        </el-card>

        <el-card shadow="never" class="scan-card">
          <div slot="header" class="scan-card-head">
            <span><i class="el-icon-document-checked"></i>参与状态</span>
            <el-tag :type="recordTagType" effect="plain">{{ recordStatusLabel }}</el-tag>
          </div>
          <div class="record-panel">
            <div>
              <span class="field-label">报名状态</span>
              <p>{{ signupStatusLabel }}</p>
            </div>
            <div>
              <span class="field-label">签到时间</span>
              <p>{{ formatDate(checkinRecord && checkinRecord.checkinTime) }}</p>
            </div>
            <div>
              <span class="field-label">签退时间</span>
              <p>{{ formatDate(checkinRecord && checkinRecord.checkoutTime) }}</p>
            </div>
            <div>
              <span class="field-label">二维码有效期</span>
              <p>{{ formatDate(qrToken && qrToken.expireTime) }}</p>
            </div>
          </div>
        </el-card>

        <el-card v-if="resultRecord" shadow="never" class="scan-card result-card">
          <div slot="header" class="scan-card-head">
            <span><i class="el-icon-circle-check"></i>操作结果</span>
            <el-button v-if="isCheckoutAction" type="text" @click="$router.push('/service-records')">查看服务记录</el-button>
          </div>
          <div class="result-grid">
            <div>
              <span class="field-label">签到时间</span>
              <p>{{ formatDate(resultRecord.checkinTime) }}</p>
            </div>
            <div>
              <span class="field-label">签退时间</span>
              <p>{{ formatDate(resultRecord.checkoutTime) }}</p>
            </div>
            <div>
              <span class="field-label">当前状态</span>
              <p>{{ checkinStatusLabel(resultRecord.status) }}</p>
            </div>
          </div>
        </el-card>
      </div>
    </section>
  </div>
</template>

<script>
import { getScanInfo, scanCheckin, scanCheckout } from '@/api/voluntary/serviceRecord'
import { parseTime } from '@/utils/ruoyi'

const SIGNUP_STATUS_MAP = {
  0: '待筛选',
  1: '已通过',
  2: '已拒绝',
  3: '候补',
  4: '已取消'
}

const CHECKIN_STATUS_MAP = {
  0: { label: '已签到', tag: 'warning' },
  1: { label: '已签退', tag: 'success' },
  2: { label: '异常', tag: 'danger' },
  3: { label: '人工确认', tag: 'info' }
}

export default {
  name: 'Scan',
  data() {
    return {
      loading: false,
      submitting: false,
      scanInfo: null,
      resultRecord: null,
      errorMessage: '',
      manualScanValue: ''
    }
  },
  computed: {
    token() {
      return this.$route.query.token || ''
    },
    qrToken() {
      return this.scanInfo && this.scanInfo.qrToken
    },
    activity() {
      return this.scanInfo && this.scanInfo.activity
    },
    signup() {
      return this.scanInfo && this.scanInfo.signup
    },
    checkinRecord() {
      return this.scanInfo && this.scanInfo.checkinRecord
    },
    actionType() {
      return this.qrToken && this.qrToken.actionType
    },
    isCheckinAction() {
      return this.actionType === 'checkin'
    },
    isCheckoutAction() {
      return this.actionType === 'checkout'
    },
    actionLabel() {
      if (this.isCheckinAction) {
        return '签到二维码'
      }
      if (this.isCheckoutAction) {
        return '签退二维码'
      }
      return '扫码入口'
    },
    actionIcon() {
      return this.isCheckoutAction ? 'el-icon-finished' : 'el-icon-location-outline'
    },
    actionTagType() {
      return this.isCheckoutAction ? 'warning' : 'success'
    },
    pageTitle() {
      if (this.isCheckoutAction) {
        return '活动签退'
      }
      if (this.isCheckinAction) {
        return '活动签到'
      }
      return '扫码签到签退'
    },
    pageSubtitle() {
      if (!this.token) {
        return '粘贴后台生成的扫码地址或二维码令牌后继续。'
      }
      return '核对活动和报名状态后完成本次现场参与记录。'
    },
    scanMessage() {
      if (!this.token) {
        return '缺少扫码令牌'
      }
      return (this.scanInfo && this.scanInfo.message) || '正在读取扫码信息'
    },
    canSubmit() {
      return !!(this.token && this.scanInfo && this.scanInfo.actionable && !this.loading && !this.submitting)
    },
    actionButtonText() {
      if (this.isCheckoutAction) {
        return '确认签退'
      }
      if (this.isCheckinAction) {
        return '确认签到'
      }
      return '等待扫码信息'
    },
    statusClass() {
      if (this.errorMessage) {
        return 'status-error'
      }
      if (this.canSubmit) {
        return 'status-ready'
      }
      return 'status-waiting'
    },
    statusIcon() {
      if (this.errorMessage) {
        return 'el-icon-warning-outline'
      }
      if (this.canSubmit) {
        return this.isCheckoutAction ? 'el-icon-finished' : 'el-icon-location-outline'
      }
      return 'el-icon-info'
    },
    statusHint() {
      if (this.errorMessage) {
        return '请检查二维码是否过期，或联系活动管理员重新生成。'
      }
      if (this.canSubmit) {
        return this.isCheckoutAction ? '签退成功后会生成服务记录并计入时长。' : '签到成功后请在活动结束时继续扫码签退。'
      }
      return '当前状态不允许执行该扫码动作。'
    },
    signupStatusLabel() {
      if (!this.signup) {
        return '未报名'
      }
      const value = Number(this.signup.status)
      return SIGNUP_STATUS_MAP[value] || '未知'
    },
    recordStatusLabel() {
      if (!this.checkinRecord) {
        return '未签到'
      }
      return this.checkinStatusLabel(this.checkinRecord.status)
    },
    recordTagType() {
      if (!this.checkinRecord) {
        return 'info'
      }
      const value = Number(this.checkinRecord.status)
      return (CHECKIN_STATUS_MAP[value] && CHECKIN_STATUS_MAP[value].tag) || 'info'
    },
    maxMinutesText() {
      const minutes = Number(this.activity && this.activity.maxServiceMinutes)
      if (!minutes) {
        return '未设置上限'
      }
      return `${minutes} 分钟`
    }
  },
  watch: {
    '$route.query.token'() {
      this.resultRecord = null
      this.loadScanInfo()
    }
  },
  created() {
    this.loadScanInfo()
  },
  methods: {
    loadScanInfo() {
      this.errorMessage = ''
      if (!this.token) {
        this.scanInfo = null
        return
      }
      this.loading = true
      getScanInfo(this.token, { noErrorMessage: true }).then((res) => {
        this.scanInfo = res.data || null
      }).catch((err) => {
        this.scanInfo = null
        this.errorMessage = (err && err.message) || '扫码信息读取失败'
      }).finally(() => {
        this.loading = false
      })
    },
    submitAction() {
      if (!this.canSubmit) {
        return
      }
      const request = this.isCheckoutAction ? scanCheckout : scanCheckin
      this.submitting = true
      request(this.token).then((res) => {
        this.resultRecord = res.data
        this.$message.success(this.isCheckoutAction ? '签退成功' : '签到成功')
        this.loadScanInfo()
      }).catch((err) => {
        this.errorMessage = (err && err.message) || '扫码操作失败'
      }).finally(() => {
        this.submitting = false
      })
    },
    openManualToken() {
      const value = this.manualScanValue
      if (!value) {
        this.$message.warning('请输入扫码地址或令牌')
        return
      }
      const token = this.extractToken(value)
      if (!token) {
        this.$message.error('未识别到有效令牌')
        return
      }
      this.$router.push({ path: '/scan', query: { token } })
    },
    extractToken(value) {
      if (!value) {
        return ''
      }
      const text = String(value).trim()
      const match = text.match(/[?&]token=([^&#]+)/)
      if (match && match[1]) {
        return decodeURIComponent(match[1])
      }
      const hashMatch = text.match(/token=([^&#]+)/)
      if (hashMatch && hashMatch[1]) {
        return decodeURIComponent(hashMatch[1])
      }
      return text
    },
    formatDate(value) {
      return parseTime(value, '{y}-{m}-{d} {h}:{i}') || '-'
    },
    timeRange(start, end) {
      return `${this.formatDate(start)} - ${this.formatDate(end)}`
    },
    checkinStatusLabel(status) {
      const value = Number(status)
      return (CHECKIN_STATUS_MAP[value] && CHECKIN_STATUS_MAP[value].label) || '未知'
    }
  }
}
</script>

<style scoped>
.scan-page {
  max-width: 1120px;
  margin: 0 auto;
  padding: 32px 20px 52px;
}

.scan-alert {
  margin-bottom: 16px;
}

.scan-layout {
  display: grid;
  grid-template-columns: 320px minmax(0, 1fr);
  gap: 18px;
  min-height: 420px;
}

.scan-status-card {
  min-height: 360px;
  padding: 26px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
  background: #fff;
  box-shadow: var(--voluntary-shadow-soft);
}

.scan-status-card span {
  color: var(--voluntary-text-muted);
  font-size: 14px;
  font-weight: 700;
}

.scan-status-card h2 {
  margin: 16px 0 10px;
  color: var(--voluntary-text);
  font-size: 24px;
  line-height: 1.35;
}

.scan-status-card p {
  min-height: 48px;
  margin: 0;
  color: var(--voluntary-text-muted);
  line-height: 1.7;
}

.status-icon {
  width: 64px;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 18px;
  border-radius: 8px;
  color: #fff;
  font-size: 30px;
}

.status-ready {
  border-left: 5px solid var(--voluntary-primary);
}

.status-ready .status-icon {
  background: var(--voluntary-primary);
}

.status-waiting {
  border-left: 5px solid var(--voluntary-info);
}

.status-waiting .status-icon {
  background: var(--voluntary-info);
}

.status-error {
  border-left: 5px solid var(--voluntary-danger);
}

.status-error .status-icon {
  background: var(--voluntary-danger);
}

.primary-action {
  width: 100%;
  margin-top: 26px;
}

.manual-token-form {
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 10px;
  margin-top: 26px;
}

.scan-detail-stack {
  display: grid;
  gap: 16px;
  min-width: 0;
}

.scan-card {
  border-radius: 8px;
}

.scan-card-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.scan-card-head span {
  display: inline-flex;
  align-items: center;
  gap: 7px;
  font-weight: 700;
}

.activity-panel,
.record-panel,
.result-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.field-label {
  display: block;
  margin-bottom: 6px;
  color: var(--voluntary-text-muted);
  font-size: 13px;
}

.activity-panel strong,
.activity-panel p,
.record-panel p,
.result-grid p {
  margin: 0;
  color: #2d3c38;
  line-height: 1.6;
  word-break: break-word;
}

.activity-panel strong {
  font-size: 18px;
}

.result-card {
  border-color: #cbe8df;
}

@media (max-width: 900px) {
  .scan-layout {
    grid-template-columns: 1fr;
  }

  .scan-status-card {
    min-height: auto;
  }
}

@media (max-width: 620px) {
  .scan-page {
    padding: 24px 14px 42px;
  }

  .activity-panel,
  .record-panel,
  .result-grid,
  .manual-token-form {
    grid-template-columns: 1fr;
  }
}
</style>
