<template>
  <div class="service-records-page">
    <section class="voluntary-page-title">
      <div>
        <h1>服务记录</h1>
        <p>查看已计入的志愿服务记录、服务次数和累计服务时长。</p>
      </div>
      <div class="voluntary-action-row">
        <el-button icon="el-icon-refresh" :loading="loading || summaryLoading" @click="refreshAll">刷新</el-button>
        <el-button type="primary" icon="el-icon-guide" @click="$router.push('/activities')">活动浏览</el-button>
      </div>
    </section>

    <el-row :gutter="16" class="summary-grid">
      <el-col :xs="24" :sm="8">
        <div class="summary-card">
          <span>累计服务</span>
          <strong>{{ totalHours }}</strong>
          <small>小时</small>
        </div>
      </el-col>
      <el-col :xs="24" :sm="8">
        <div class="summary-card">
          <span>服务分钟</span>
          <strong>{{ totalMinutes }}</strong>
          <small>分钟</small>
        </div>
      </el-col>
      <el-col :xs="24" :sm="8">
        <div class="summary-card">
          <span>有效次数</span>
          <strong>{{ serviceCount }}</strong>
          <small>次</small>
        </div>
      </el-col>
    </el-row>

    <el-card shadow="never" class="filter-panel">
      <el-form :model="queryParams" class="filter-form" @submit.native.prevent>
        <el-input
          v-model.trim="queryParams.activityTitle"
          clearable
          prefix-icon="el-icon-search"
          placeholder="搜索活动名称"
          @keyup.enter.native="handleQuery"
        />
        <el-select v-model="queryParams.status" clearable placeholder="记录状态">
          <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-date-picker
          v-model="dateRange"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
        <div class="filter-actions">
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
        </div>
      </el-form>
    </el-card>

    <div v-loading="loading" class="record-list">
      <el-empty v-if="!loading && records.length === 0" description="暂无服务记录">
        <el-button type="primary" icon="el-icon-s-management" @click="$router.push('/signups')">查看报名</el-button>
      </el-empty>

      <div v-else class="record-grid">
        <article v-for="record in records" :key="record.id" class="record-card">
          <div class="record-main">
            <div class="record-head">
              <div>
                <span class="record-date">{{ record.serviceDate || '服务日期待确认' }}</span>
                <h2>{{ record.activityTitle || '活动已删除或未命名' }}</h2>
              </div>
              <el-tag :type="recordStatusTag(record.status)" effect="plain">{{ recordStatusLabel(record.status) }}</el-tag>
            </div>

            <div class="record-meta">
              <span><i class="el-icon-location-outline"></i>{{ record.serviceLocation || '地点待补充' }}</span>
              <span><i class="el-icon-time"></i>{{ timeRange(record.startTime, record.endTime) }}</span>
              <span><i class="el-icon-collection-tag"></i>来源记录 {{ record.checkinRecordId || '-' }}</span>
            </div>
          </div>

          <div class="record-duration">
            <strong>{{ record.serviceMinutes || 0 }}</strong>
            <span>分钟</span>
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
      @pagination="loadRecords"
    />
  </div>
</template>

<script>
import { getMyServiceSummary, listMyServiceRecords } from '@/api/voluntary/serviceRecord'
import { addDateRange, parseTime } from '@/utils/ruoyi'

const SERVICE_STATUS_MAP = {
  0: { label: '待确认', tag: 'warning' },
  1: { label: '有效', tag: 'success' },
  2: { label: '异常', tag: 'danger' },
  3: { label: '作废', tag: 'info' }
}

export default {
  name: 'ServiceRecords',
  data() {
    return {
      loading: false,
      summaryLoading: false,
      total: 0,
      records: [],
      dateRange: [],
      summary: {
        totalServiceMinutes: 0,
        serviceCount: 0
      },
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        activityTitle: '',
        status: ''
      },
      statusOptions: [
        { value: 0, label: '待确认' },
        { value: 1, label: '有效' },
        { value: 2, label: '异常' },
        { value: 3, label: '作废' }
      ]
    }
  },
  computed: {
    totalMinutes() {
      return Number(this.summary.totalServiceMinutes || 0)
    },
    totalHours() {
      return (this.totalMinutes / 60).toFixed(1)
    },
    serviceCount() {
      return Number(this.summary.serviceCount || 0)
    }
  },
  created() {
    this.refreshAll()
  },
  methods: {
    refreshAll() {
      this.loadSummary()
      this.loadRecords()
    },
    loadSummary() {
      this.summaryLoading = true
      getMyServiceSummary().then((res) => {
        this.summary = res.data || {
          totalServiceMinutes: 0,
          serviceCount: 0
        }
      }).finally(() => {
        this.summaryLoading = false
      })
    },
    loadRecords() {
      this.loading = true
      const query = addDateRange(Object.assign({}, this.queryParams, {
        status: this.queryParams.status === '' ? undefined : this.queryParams.status
      }), this.dateRange)
      listMyServiceRecords(query).then((res) => {
        this.records = res.rows || []
        this.total = Number(res.total || 0)
      }).finally(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.loadRecords()
    },
    resetQuery() {
      this.dateRange = []
      this.queryParams = {
        pageNum: 1,
        pageSize: 5,
        activityTitle: '',
        status: ''
      }
      this.loadRecords()
    },
    recordStatusLabel(status) {
      const value = Number(status)
      return (SERVICE_STATUS_MAP[value] && SERVICE_STATUS_MAP[value].label) || '未知'
    },
    recordStatusTag(status) {
      const value = Number(status)
      return (SERVICE_STATUS_MAP[value] && SERVICE_STATUS_MAP[value].tag) || 'info'
    },
    formatDate(value) {
      return parseTime(value, '{y}-{m}-{d} {h}:{i}') || '-'
    },
    timeRange(start, end) {
      return `${this.formatDate(start)} - ${this.formatDate(end)}`
    }
  }
}
</script>

<style scoped>
.service-records-page {
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
  margin-top: 10px;
  color: var(--voluntary-primary-dark);
  font-size: 34px;
  line-height: 1.1;
}

.summary-card small {
  margin-left: 6px;
  color: var(--voluntary-text-muted);
}

.filter-panel {
  margin-bottom: 18px;
}

.filter-form {
  display: grid;
  grid-template-columns: minmax(220px, 1fr) 150px 260px auto;
  gap: 12px;
  align-items: center;
}

.filter-actions {
  display: flex;
  gap: 10px;
}

.record-list {
  min-height: 340px;
}

.record-grid {
  display: grid;
  gap: 16px;
}

.record-card {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 118px;
  gap: 18px;
  align-items: center;
  padding: 20px;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
  background: #fff;
  box-shadow: var(--voluntary-shadow-soft);
}

.record-main {
  min-width: 0;
}

.record-head {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 14px;
}

.record-date {
  color: var(--voluntary-primary);
  font-size: 13px;
  font-weight: 700;
}

.record-head h2 {
  margin: 6px 0 0;
  color: var(--voluntary-text);
  font-size: 20px;
  line-height: 1.35;
  word-break: break-word;
}

.record-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 8px 18px;
  margin-top: 12px;
  color: var(--voluntary-text-muted);
  font-size: 13px;
}

.record-meta span {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  min-width: 0;
  line-height: 1.5;
}

.record-duration {
  min-height: 86px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background: var(--voluntary-primary-soft);
  color: var(--voluntary-primary-dark);
}

.record-duration strong {
  font-size: 30px;
  line-height: 1;
}

.record-duration span {
  margin-top: 8px;
  font-size: 13px;
}

.pagination-container {
  margin-top: 16px;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
}

@media (max-width: 960px) {
  .filter-form,
  .record-card {
    grid-template-columns: 1fr;
  }

  .filter-form .el-date-editor {
    width: 100%;
  }
}

@media (max-width: 620px) {
  .service-records-page {
    padding: 24px 14px 42px;
  }

  .filter-actions {
    display: grid;
    grid-template-columns: 1fr;
  }
}
</style>
