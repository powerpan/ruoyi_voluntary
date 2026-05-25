<template>
  <div class="notifications-page">
    <section class="voluntary-page-title">
      <div>
        <h1>通知中心</h1>
        <p>接收志愿者审核、报名筛选、活动变更、签到异常和服务记录相关通知。</p>
      </div>
      <div class="voluntary-action-row">
        <el-button icon="el-icon-refresh" :loading="loading || unreadLoading" @click="refreshAll">刷新</el-button>
        <el-button
          type="primary"
          icon="el-icon-check"
          :disabled="unreadCount <= 0"
          :loading="readAllLoading"
          @click="handleReadAll"
        >
          全部已读
        </el-button>
      </div>
    </section>

    <el-row :gutter="16" class="summary-grid">
      <el-col :xs="24" :sm="8">
        <div class="summary-card">
          <span>未读通知</span>
          <strong>{{ unreadCount }}</strong>
          <small>条</small>
        </div>
      </el-col>
      <el-col :xs="24" :sm="8">
        <div class="summary-card">
          <span>当前筛选</span>
          <strong>{{ total }}</strong>
          <small>条</small>
        </div>
      </el-col>
      <el-col :xs="24" :sm="8">
        <div class="summary-card">
          <span>通知类型</span>
          <strong>{{ selectedTypeLabel }}</strong>
          <small>{{ currentStatusLabel }}</small>
        </div>
      </el-col>
    </el-row>

    <el-card shadow="never" class="filter-panel">
      <el-form :model="queryParams" class="filter-form" @submit.native.prevent>
        <el-radio-group v-model="queryParams.status" size="small" @change="handleQuery">
          <el-radio-button label="">全部</el-radio-button>
          <el-radio-button :label="0">未读</el-radio-button>
          <el-radio-button :label="1">已读</el-radio-button>
        </el-radio-group>
        <el-select v-model="queryParams.noticeType" clearable placeholder="通知类型" @change="handleQuery">
          <el-option
            v-for="item in noticeTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <div class="filter-actions">
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
        </div>
      </el-form>
    </el-card>

    <div v-loading="loading" class="notification-list">
      <el-empty v-if="!loading && notifications.length === 0" description="暂无通知">
        <el-button icon="el-icon-refresh" @click="refreshAll">刷新</el-button>
      </el-empty>

      <div v-else class="notification-grid">
        <article
          v-for="item in notifications"
          :key="item.id"
          class="notification-card"
          :class="{ unread: isUnread(item) }"
        >
          <div class="notification-main">
            <div class="notification-head">
              <div class="notification-title-wrap">
                <div class="tag-row">
                  <el-tag :type="noticeTypeTag(item.noticeType)" effect="plain">
                    {{ noticeTypeLabel(item.noticeType) }}
                  </el-tag>
                  <el-tag :type="isUnread(item) ? 'danger' : 'info'" effect="plain">
                    {{ isUnread(item) ? '未读' : '已读' }}
                  </el-tag>
                </div>
                <h2>{{ item.title || '未命名通知' }}</h2>
              </div>
              <span class="notice-time">{{ formatDate(item.createTime) }}</span>
            </div>

            <p class="notice-content">{{ item.content || '暂无通知内容' }}</p>

            <div class="notice-meta">
              <span><i class="el-icon-collection-tag"></i>{{ targetLabel(item.targetType) }}</span>
              <span v-if="item.targetId"><i class="el-icon-document"></i>业务编号 {{ item.targetId }}</span>
              <span v-if="item.readTime"><i class="el-icon-check"></i>阅读时间 {{ formatDate(item.readTime) }}</span>
            </div>
          </div>

          <div class="notification-actions">
            <el-button
              icon="el-icon-check"
              :disabled="!isUnread(item)"
              :loading="readingId === item.id"
              @click="handleRead(item)"
            >
              标为已读
            </el-button>
            <el-button
              v-if="safeActionUrl(item.actionUrl)"
              type="primary"
              icon="el-icon-position"
              :loading="openingId === item.id"
              @click="handleReadAndOpen(item)"
            >
              查看
            </el-button>
          </div>
        </article>
      </div>
    </div>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :page-sizes="[5, 10, 15, 20]"
      layout="total, sizes, prev, pager, next"
      @pagination="loadNotifications"
    />
  </div>
</template>

<script>
import {
  getUnreadNotificationCount,
  listMyNotifications,
  markAllNotificationsRead,
  markNotificationRead
} from '@/api/voluntary/notification'
import { parseTime } from '@/utils/ruoyi'

const NOTICE_TYPE_MAP = {
  volunteer_audit: { label: '志愿者审核', tag: 'success' },
  signup_review: { label: '报名筛选', tag: 'primary' },
  activity_change: { label: '活动变更', tag: 'warning' },
  checkin_abnormal: { label: '签到异常', tag: 'danger' },
  service_record: { label: '服务记录', tag: 'success' },
  system: { label: '系统消息', tag: 'info' }
}

const TARGET_TYPE_MAP = {
  volunteer_profile: '志愿者档案',
  signup: '活动报名',
  activity: '志愿活动',
  checkin: '签到签退',
  service_record: '服务记录',
  system: '系统消息'
}

export default {
  name: 'Notifications',
  data() {
    return {
      loading: false,
      unreadLoading: false,
      readAllLoading: false,
      readingId: null,
      openingId: null,
      unreadCount: 0,
      total: 0,
      notifications: [],
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        status: '',
        noticeType: ''
      },
      noticeTypeOptions: Object.keys(NOTICE_TYPE_MAP).map((key) => ({
        value: key,
        label: NOTICE_TYPE_MAP[key].label
      }))
    }
  },
  computed: {
    currentStatusLabel() {
      if (this.queryParams.status === 0) {
        return '仅未读'
      }
      if (this.queryParams.status === 1) {
        return '仅已读'
      }
      return '全部状态'
    },
    selectedTypeLabel() {
      if (!this.queryParams.noticeType) {
        return '全部'
      }
      return this.noticeTypeLabel(this.queryParams.noticeType)
    }
  },
  created() {
    this.refreshAll()
  },
  methods: {
    refreshAll() {
      this.loadUnreadCount()
      this.loadNotifications()
    },
    loadUnreadCount() {
      this.unreadLoading = true
      getUnreadNotificationCount({ noErrorMessage: true }).then((res) => {
        this.unreadCount = Number(res.data || 0)
      }).catch(() => {
        this.unreadCount = 0
      }).finally(() => {
        this.unreadLoading = false
      })
    },
    loadNotifications() {
      this.loading = true
      const query = Object.assign({}, this.queryParams, {
        status: this.queryParams.status === '' ? undefined : this.queryParams.status,
        noticeType: this.queryParams.noticeType || undefined
      })
      listMyNotifications(query).then((res) => {
        this.notifications = res.rows || []
        this.total = Number(res.total || 0)
      }).finally(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.loadNotifications()
    },
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 5,
        status: '',
        noticeType: ''
      }
      this.loadNotifications()
    },
    handleRead(item) {
      if (!this.isUnread(item)) {
        return
      }
      this.readingId = item.id
      markNotificationRead(item.id).then(() => {
        this.$message.success('通知已标记为已读')
        this.afterReadChange()
      }).finally(() => {
        this.readingId = null
      })
    },
    handleReadAndOpen(item) {
      const actionUrl = this.safeActionUrl(item.actionUrl)
      if (!actionUrl) {
        return
      }
      this.openingId = item.id
      const readTask = this.isUnread(item) ? markNotificationRead(item.id) : Promise.resolve()
      readTask.then(() => {
        this.afterReadChange()
        if (this.$route.path !== actionUrl) {
          this.$router.push(actionUrl)
        }
      }).finally(() => {
        this.openingId = null
      })
    },
    handleReadAll() {
      if (this.unreadCount <= 0) {
        return
      }
      this.readAllLoading = true
      markAllNotificationsRead().then(() => {
        this.$message.success('全部通知已标记为已读')
        this.afterReadChange()
      }).finally(() => {
        this.readAllLoading = false
      })
    },
    afterReadChange() {
      this.loadUnreadCount()
      this.loadNotifications()
      window.dispatchEvent(new CustomEvent('voluntary-notification-updated'))
    },
    isUnread(item) {
      return Number(item && item.status) === 0
    },
    noticeTypeLabel(type) {
      return (NOTICE_TYPE_MAP[type] && NOTICE_TYPE_MAP[type].label) || type || '业务通知'
    },
    noticeTypeTag(type) {
      return (NOTICE_TYPE_MAP[type] && NOTICE_TYPE_MAP[type].tag) || 'info'
    },
    targetLabel(type) {
      return TARGET_TYPE_MAP[type] || type || '业务对象'
    },
    safeActionUrl(url) {
      if (!url || typeof url !== 'string') {
        return ''
      }
      const path = url.trim()
      if (!path || !path.startsWith('/') || path.startsWith('//') || path.indexOf('://') !== -1) {
        return ''
      }
      return path
    },
    formatDate(value) {
      return parseTime(value, '{y}-{m}-{d} {h}:{i}') || '-'
    }
  }
}
</script>

<style scoped>
.notifications-page {
  max-width: 1120px;
  margin: 0 auto;
  padding: 32px 20px 52px;
}

.summary-grid {
  margin-bottom: 18px;
}

.summary-card {
  min-height: 122px;
  padding: 20px;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
  background: #fff;
  box-shadow: var(--voluntary-shadow-soft);
}

.summary-card span {
  display: block;
  color: var(--voluntary-text-muted);
  font-size: 14px;
}

.summary-card strong {
  display: inline-block;
  max-width: 100%;
  margin-top: 10px;
  color: var(--voluntary-primary-dark);
  font-size: 34px;
  line-height: 1.1;
  overflow-wrap: anywhere;
}

.summary-card small {
  display: block;
  margin-top: 6px;
  color: var(--voluntary-text-muted);
}

.filter-panel {
  margin-bottom: 18px;
}

.filter-form {
  display: grid;
  grid-template-columns: auto 220px auto;
  gap: 12px;
  align-items: center;
}

.filter-actions {
  display: flex;
  gap: 10px;
}

.notification-list {
  min-height: 340px;
}

.notification-grid {
  display: grid;
  gap: 16px;
}

.notification-card {
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 18px;
  padding: 20px;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
  background: #fff;
  box-shadow: var(--voluntary-shadow-soft);
}

.notification-card.unread {
  border-color: rgba(35, 130, 118, .38);
  box-shadow: 0 15px 34px rgba(35, 130, 118, .11);
}

.notification-main {
  min-width: 0;
}

.notification-head {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 14px;
}

.notification-title-wrap {
  min-width: 0;
}

.tag-row {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.notification-head h2 {
  margin: 8px 0 0;
  color: var(--voluntary-text);
  font-size: 20px;
  line-height: 1.35;
  overflow-wrap: anywhere;
}

.notice-time {
  flex: 0 0 auto;
  color: var(--voluntary-text-muted);
  font-size: 13px;
  white-space: nowrap;
}

.notice-content {
  margin: 14px 0 0;
  color: #45534f;
  line-height: 1.8;
  overflow-wrap: anywhere;
}

.notice-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 14px;
  color: var(--voluntary-text-muted);
  font-size: 13px;
}

.notice-meta span {
  display: inline-flex;
  align-items: center;
  gap: 5px;
}

.notification-actions {
  display: flex;
  align-items: flex-start;
  gap: 10px;
}

@media (max-width: 920px) {
  .filter-form,
  .notification-card {
    grid-template-columns: 1fr;
  }

  .notification-actions,
  .filter-actions {
    justify-content: flex-start;
  }
}

@media (max-width: 640px) {
  .notification-head {
    flex-direction: column;
  }

  .notice-time {
    white-space: normal;
  }

  .filter-form {
    gap: 10px;
  }

  .filter-form .el-select {
    width: 100%;
  }

  .notification-actions {
    flex-wrap: wrap;
  }
}
</style>
