<template>
  <div class="activities-page">
    <section class="voluntary-page-title">
      <div>
        <h1>活动浏览</h1>
        <p>查看已发布的志愿活动，筛选报名中的项目并进入详情提交报名。</p>
      </div>
      <div class="voluntary-action-row">
        <el-button icon="el-icon-refresh" :loading="loading" @click="loadActivities">刷新</el-button>
        <el-button type="primary" icon="el-icon-document" @click="$router.push('/signups')">我的报名</el-button>
      </div>
    </section>

    <el-card shadow="never" class="filter-panel">
      <el-form :model="queryParams" class="filter-form" @submit.native.prevent>
        <el-input
          v-model.trim="queryParams.keyword"
          clearable
          prefix-icon="el-icon-search"
          placeholder="搜索活动标题或地点"
          @keyup.enter.native="handleQuery"
        />
        <el-select v-model="queryParams.activityType" clearable placeholder="活动类型">
          <el-option
            v-for="item in activityTypes"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-checkbox v-model="queryParams.signupOpen" border>只看报名中</el-checkbox>
        <div class="filter-actions">
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
        </div>
      </el-form>
    </el-card>

    <div v-loading="loading" class="activity-list">
      <el-empty v-if="!loading && activities.length === 0" description="暂无已发布活动" />
      <div v-else class="activity-grid">
        <article v-for="activity in activities" :key="activity.id" class="activity-card">
          <div class="activity-cover" :style="coverStyle(activity)">
            <span>{{ activityTypeLabel(activity.activityType) }}</span>
          </div>
          <div class="activity-body">
            <div class="activity-head">
              <h2>{{ activity.title }}</h2>
              <el-tag :type="signupOpen(activity) ? 'success' : 'info'" effect="plain">
                {{ signupOpen(activity) ? '报名中' : signupStatusText(activity) }}
              </el-tag>
            </div>
            <p class="activity-location"><i class="el-icon-location-outline"></i>{{ activity.serviceLocation || '地点待补充' }}</p>
            <div class="activity-meta">
              <span><i class="el-icon-date"></i>{{ timeRange(activity.startTime, activity.endTime) }}</span>
              <span><i class="el-icon-timer"></i>报名至 {{ formatDate(activity.signupEndTime) }}</span>
              <span><i class="el-icon-user"></i>{{ Number(activity.approvedCount || 0) }}/{{ Number(activity.recruitCount || 0) }} 人</span>
            </div>
            <el-progress
              :percentage="recruitPercent(activity)"
              :show-text="false"
              :stroke-width="8"
              :status="recruitPercent(activity) >= 100 ? 'warning' : undefined"
            />
            <p class="activity-summary">{{ activity.serviceTarget || activity.requirements || '活动详情中可查看服务对象、报名要求和负责人信息。' }}</p>
          </div>
          <div class="activity-actions">
            <el-button icon="el-icon-view" @click="openDetail(activity)">查看详情</el-button>
            <el-button
              type="primary"
              icon="el-icon-edit-outline"
              :disabled="!canApply(activity)"
              @click="goSignup(activity)"
            >
              报名
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
      :page-sizes="[6, 9, 12, 18]"
      layout="total, sizes, prev, pager, next"
      @pagination="loadActivities"
    />
  </div>
</template>

<script>
import { listActivities } from '@/api/voluntary/activity'

const ACTIVITY_TYPE_MAP = {
  community: { label: '社区服务', color: '#238276' },
  campus: { label: '校园服务', color: '#3c6f9f' },
  publicity: { label: '公益宣传', color: '#d78a2b' }
}

export default {
  name: 'Activities',
  data() {
    return {
      loading: false,
      total: 0,
      activities: [],
      queryParams: {
        pageNum: 1,
        pageSize: 6,
        keyword: '',
        activityType: '',
        signupOpen: false
      },
      activityTypes: [
        { value: 'community', label: '社区服务' },
        { value: 'campus', label: '校园服务' },
        { value: 'publicity', label: '公益宣传' }
      ]
    }
  },
  created() {
    this.loadActivities()
  },
  methods: {
    loadActivities() {
      this.loading = true
      const query = Object.assign({}, this.queryParams, {
        signupOpen: this.queryParams.signupOpen ? true : undefined
      })
      listActivities(query).then((res) => {
        this.activities = res.rows || []
        this.total = Number(res.total || 0)
      }).finally(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.loadActivities()
    },
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 6,
        keyword: '',
        activityType: '',
        signupOpen: false
      }
      this.loadActivities()
    },
    openDetail(activity) {
      this.$router.push(`/activities/${activity.id}`)
    },
    goSignup(activity) {
      if (!this.canApply(activity)) {
        return
      }
      this.$router.push({ path: `/activities/${activity.id}`, query: { signup: '1' } })
    },
    activityTypeLabel(type) {
      return (ACTIVITY_TYPE_MAP[type] && ACTIVITY_TYPE_MAP[type].label) || type || '志愿活动'
    },
    coverStyle(activity) {
      if (activity.coverUrl) {
        return {
          backgroundImage: `linear-gradient(180deg, rgba(17, 28, 25, .18), rgba(17, 28, 25, .46)), url(${activity.coverUrl})`
        }
      }
      const cfg = ACTIVITY_TYPE_MAP[activity.activityType] || ACTIVITY_TYPE_MAP.community
      return {
        backgroundImage: `linear-gradient(135deg, ${cfg.color}, #5f9ea0)`
      }
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
    signupOpen(activity) {
      const now = new Date()
      const start = this.parseTime(activity.signupStartTime)
      const end = this.parseTime(activity.signupEndTime)
      return !!start && !!end && start <= now && now <= end
    },
    signupStatusText(activity) {
      const now = new Date()
      const start = this.parseTime(activity.signupStartTime)
      const end = this.parseTime(activity.signupEndTime)
      if (start && now < start) {
        return '未开始'
      }
      if (end && now > end) {
        return '已截止'
      }
      return '暂不可报名'
    },
    recruitPercent(activity) {
      const total = Number(activity.recruitCount || 0)
      if (total <= 0) {
        return 0
      }
      const approved = Number(activity.approvedCount || 0)
      return Math.min(100, Math.round((approved / total) * 100))
    },
    canApply(activity) {
      const total = Number(activity.recruitCount || 0)
      const approved = Number(activity.approvedCount || 0)
      return this.signupOpen(activity) && (total <= 0 || approved < total)
    }
  }
}
</script>

<style scoped>
.activities-page {
  max-width: 1180px;
  margin: 0 auto;
  padding: 32px 20px 52px;
}

.filter-panel {
  margin-bottom: 18px;
}

.filter-form {
  display: grid;
  grid-template-columns: minmax(240px, 1fr) 180px auto auto;
  gap: 12px;
  align-items: center;
}

.filter-actions {
  display: flex;
  gap: 10px;
}

.activity-list {
  min-height: 360px;
}

.activity-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 18px;
}

.activity-card {
  display: grid;
  grid-template-rows: 152px minmax(0, 1fr) auto;
  min-width: 0;
  overflow: hidden;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
  background: #fff;
  box-shadow: var(--voluntary-shadow-soft);
}

.activity-cover {
  display: flex;
  align-items: flex-end;
  padding: 16px;
  background-size: cover;
  background-position: center;
}

.activity-cover span {
  display: inline-flex;
  align-items: center;
  min-height: 28px;
  padding: 0 10px;
  border-radius: 8px;
  color: #fff;
  background: rgba(18, 31, 28, .58);
  font-size: 13px;
  font-weight: 700;
}

.activity-body {
  min-width: 0;
  padding: 18px;
}

.activity-head {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 12px;
}

.activity-head h2 {
  min-width: 0;
  margin: 0;
  color: var(--voluntary-text);
  font-size: 19px;
  line-height: 1.35;
  word-break: break-word;
}

.activity-location,
.activity-summary,
.activity-meta {
  color: var(--voluntary-text-muted);
}

.activity-location {
  display: flex;
  align-items: center;
  gap: 6px;
  margin: 10px 0 0;
  line-height: 1.6;
}

.activity-meta {
  display: grid;
  gap: 7px;
  margin: 12px 0;
  font-size: 13px;
}

.activity-meta span {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  min-width: 0;
  line-height: 1.5;
}

.activity-summary {
  min-height: 44px;
  margin: 13px 0 0;
  line-height: 1.7;
}

.activity-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 14px 18px 18px;
  border-top: 1px solid var(--voluntary-border);
}

.pagination-container {
  margin-top: 8px;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
}

@media (max-width: 1060px) {
  .activity-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .filter-form {
    grid-template-columns: 1fr 170px;
  }
}

@media (max-width: 700px) {
  .activities-page {
    padding: 24px 14px 42px;
  }

  .filter-form,
  .activity-grid {
    grid-template-columns: 1fr;
  }

  .filter-actions,
  .activity-actions {
    justify-content: stretch;
  }

  .filter-actions .el-button,
  .activity-actions .el-button {
    flex: 1;
  }
}
</style>
