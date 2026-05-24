<template>
  <div class="signups-page">
    <section class="voluntary-page-title">
      <div>
        <h1>我的报名</h1>
        <p>查看已提交的活动报名、管理员筛选结果和处理意见，可取消未被拒绝的报名。</p>
      </div>
      <div class="voluntary-action-row">
        <el-button icon="el-icon-refresh" :loading="loading" @click="loadSignups">刷新</el-button>
        <el-button type="primary" icon="el-icon-guide" @click="$router.push('/activities')">继续报名</el-button>
      </div>
    </section>

    <el-card shadow="never" class="filter-panel">
      <el-form :model="queryParams" class="filter-form" @submit.native.prevent>
        <el-input
          v-model.trim="queryParams.activityTitle"
          clearable
          prefix-icon="el-icon-search"
          placeholder="搜索活动名称"
          @keyup.enter.native="handleQuery"
        />
        <el-select v-model="queryParams.status" clearable placeholder="报名状态">
          <el-option
            v-for="item in signupStatuses"
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

    <div v-loading="loading" class="signup-list">
      <el-empty v-if="!loading && signups.length === 0" description="暂无报名记录">
        <el-button type="primary" icon="el-icon-guide" @click="$router.push('/activities')">去浏览活动</el-button>
      </el-empty>

      <div v-else class="signup-grid">
        <article v-for="signup in signups" :key="signup.id" class="signup-card">
          <div class="card-main">
            <div class="card-head">
              <div>
                <span class="type-label">{{ activityTypeLabel(signup.activityType) }}</span>
                <h2>{{ signup.activityTitle || '活动已删除或未命名' }}</h2>
              </div>
              <el-tag :type="signupStatusTag(signup.status)" effect="plain">{{ signupStatusLabel(signup.status) }}</el-tag>
            </div>

            <div class="card-meta">
              <span><i class="el-icon-location-outline"></i>{{ signup.serviceLocation || '地点待补充' }}</span>
              <span><i class="el-icon-date"></i>{{ timeRange(signup.activityStartTime, signup.activityEndTime) }}</span>
              <span><i class="el-icon-time"></i>报名时间 {{ formatDate(signup.createTime) }}</span>
            </div>

            <div class="signup-copy">
              <div>
                <span>报名理由</span>
                <p>{{ signup.applyReason || '未填写' }}</p>
              </div>
              <div>
                <span>相关经验</span>
                <p>{{ signup.experience || '未填写' }}</p>
              </div>
              <div v-if="signup.reviewReason">
                <span>处理意见</span>
                <p>{{ signup.reviewReason }}</p>
              </div>
            </div>
          </div>

          <div class="card-actions">
            <el-button icon="el-icon-view" @click="$router.push(`/activities/${signup.activityId}`)">查看活动</el-button>
            <el-button
              type="warning"
              icon="el-icon-close"
              :disabled="!canCancel(signup)"
              :loading="cancelingId === signup.id"
              @click="handleCancel(signup)"
            >
              取消报名
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
      @pagination="loadSignups"
    />
  </div>
</template>

<script>
import { cancelMySignup, listMySignups } from '@/api/voluntary/activity'

const ACTIVITY_TYPE_MAP = {
  community: '社区服务',
  campus: '校园服务',
  publicity: '公益宣传'
}

const SIGNUP_STATUS_MAP = {
  0: { label: '待筛选', tag: 'warning' },
  1: { label: '通过', tag: 'success' },
  2: { label: '拒绝', tag: 'danger' },
  3: { label: '候补', tag: 'info' },
  4: { label: '取消', tag: 'info' }
}

export default {
  name: 'Signups',
  data() {
    return {
      loading: false,
      cancelingId: null,
      total: 0,
      signups: [],
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        activityTitle: '',
        status: ''
      },
      signupStatuses: [
        { value: 0, label: '待筛选' },
        { value: 1, label: '通过' },
        { value: 2, label: '拒绝' },
        { value: 3, label: '候补' },
        { value: 4, label: '取消' }
      ]
    }
  },
  created() {
    this.loadSignups()
  },
  methods: {
    loadSignups() {
      this.loading = true
      const query = Object.assign({}, this.queryParams, {
        status: this.queryParams.status === '' ? undefined : this.queryParams.status
      })
      listMySignups(query).then((res) => {
        this.signups = res.rows || []
        this.total = Number(res.total || 0)
      }).finally(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.loadSignups()
    },
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 5,
        activityTitle: '',
        status: ''
      }
      this.loadSignups()
    },
    canCancel(signup) {
      return signup && signup.id && [0, 1, 3].indexOf(Number(signup.status)) !== -1
    },
    handleCancel(signup) {
      if (!this.canCancel(signup)) {
        return
      }
      this.$confirm('确认取消该活动报名吗？取消后如需重新报名，需要在报名时间内再次提交。', '取消报名', {
        confirmButtonText: '确认取消',
        cancelButtonText: '再想想',
        type: 'warning'
      }).then(() => {
        this.cancelingId = signup.id
        return cancelMySignup(signup.id).then(() => {
          this.$message.success('报名已取消')
          this.loadSignups()
        }).finally(() => {
          this.cancelingId = null
        })
      }).catch(() => {})
    },
    activityTypeLabel(type) {
      return ACTIVITY_TYPE_MAP[type] || type || '志愿活动'
    },
    signupStatusLabel(status) {
      const value = Number(status)
      return (SIGNUP_STATUS_MAP[value] && SIGNUP_STATUS_MAP[value].label) || '未知'
    },
    signupStatusTag(status) {
      const value = Number(status)
      return (SIGNUP_STATUS_MAP[value] && SIGNUP_STATUS_MAP[value].tag) || 'info'
    },
    formatDate(value) {
      if (!value) {
        return '待定'
      }
      return String(value).slice(0, 16)
    },
    timeRange(start, end) {
      return `${this.formatDate(start)} - ${this.formatDate(end)}`
    }
  }
}
</script>

<style scoped>
.signups-page {
  max-width: 1120px;
  margin: 0 auto;
  padding: 32px 20px 52px;
}

.filter-panel {
  margin-bottom: 18px;
}

.filter-form {
  display: grid;
  grid-template-columns: minmax(240px, 1fr) 180px auto;
  gap: 12px;
  align-items: center;
}

.filter-actions {
  display: flex;
  gap: 10px;
}

.signup-list {
  min-height: 340px;
}

.signup-grid {
  display: grid;
  gap: 16px;
}

.signup-card {
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 18px;
  padding: 20px;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
  background: #fff;
  box-shadow: var(--voluntary-shadow-soft);
}

.card-main {
  min-width: 0;
}

.card-head {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 14px;
}

.type-label {
  color: var(--voluntary-primary);
  font-size: 13px;
  font-weight: 700;
}

.card-head h2 {
  margin: 6px 0 0;
  color: var(--voluntary-text);
  font-size: 20px;
  line-height: 1.35;
  word-break: break-word;
}

.card-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 8px 18px;
  margin-top: 12px;
  color: var(--voluntary-text-muted);
  font-size: 13px;
}

.card-meta span {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  min-width: 0;
  line-height: 1.5;
}

.signup-copy {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
  margin-top: 16px;
}

.signup-copy div {
  min-width: 0;
  padding: 12px;
  border-radius: 8px;
  background: var(--voluntary-surface-soft);
}

.signup-copy span {
  color: var(--voluntary-text-muted);
  font-size: 13px;
}

.signup-copy p {
  margin: 6px 0 0;
  color: #344440;
  line-height: 1.65;
  word-break: break-word;
}

.card-actions {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 10px;
  width: 126px;
}

.card-actions .el-button + .el-button {
  margin-left: 0;
}

.pagination-container {
  margin-top: 16px;
  border: 1px solid var(--voluntary-border);
  border-radius: 8px;
}

@media (max-width: 860px) {
  .filter-form,
  .signup-card {
    grid-template-columns: 1fr;
  }

  .signup-copy {
    grid-template-columns: 1fr;
  }

  .card-actions {
    width: 100%;
    flex-direction: row;
  }

  .card-actions .el-button {
    flex: 1;
  }
}

@media (max-width: 620px) {
  .signups-page {
    padding: 24px 14px 42px;
  }

  .filter-actions,
  .card-actions {
    display: grid;
    grid-template-columns: 1fr;
  }
}
</style>
