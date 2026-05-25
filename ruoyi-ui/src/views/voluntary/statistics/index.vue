<template>
  <div class="voluntary-admin-page statistics-page">
    <el-card class="voluntary-admin-card" shadow="never">
      <div slot="header" class="voluntary-admin-title">
        <div>
          <h2>数据统计</h2>
          <p>按活动、志愿者、组织和服务日期汇总志愿服务数据。</p>
        </div>
        <span class="meta">数据统计</span>
      </div>

      <el-form
        ref="queryForm"
        class="voluntary-admin-filter"
        :model="queryParams"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="统计日期">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </el-form-item>
        <el-form-item label="活动" prop="activityTitle">
          <el-input
            v-model="queryParams.activityTitle"
            placeholder="请输入活动标题"
            clearable
            style="width: 190px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="组织" prop="organization">
          <el-input
            v-model="queryParams.organization"
            placeholder="请输入组织"
            clearable
            style="width: 170px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="volunteerRealName">
          <el-input
            v-model="queryParams.volunteerRealName"
            placeholder="请输入志愿者姓名"
            clearable
            style="width: 160px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="趋势" prop="trendType">
          <el-select v-model="queryParams.trendType" style="width: 110px">
            <el-option label="按日" value="day" />
            <el-option label="按月" value="month" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <div class="statistics-toolbar">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="refreshAll"></right-toolbar>
      </div>

      <div v-loading="overviewLoading" class="statistics-summary-grid">
        <div v-for="card in overviewCards" :key="card.key" class="statistics-summary-item">
          <div class="statistics-summary-head">
            <i :class="card.icon"></i>
            <span>{{ card.label }}</span>
          </div>
          <strong>{{ card.value }}</strong>
          <small>{{ card.extra }}</small>
        </div>
      </div>

      <el-tabs v-model="activeTab" class="statistics-tabs" @tab-click="handleTabClick">
        <el-tab-pane label="活动统计" name="activities">
          <el-table v-loading="activityLoading" class="voluntary-admin-table" :data="activityList">
            <el-table-column label="活动" prop="activityTitle" min-width="220" :show-overflow-tooltip="true" />
            <el-table-column label="招募人数" prop="recruitCount" width="100" align="center" />
            <el-table-column label="报名" prop="signupCount" width="90" align="center" />
            <el-table-column label="通过" prop="approvedSignupCount" width="90" align="center" />
            <el-table-column label="签到" prop="checkinCount" width="90" align="center" />
            <el-table-column label="签退" prop="checkoutCount" width="90" align="center" />
            <el-table-column label="服务记录" prop="serviceRecordCount" width="110" align="center" />
            <el-table-column label="服务时长" prop="serviceMinutes" width="140" align="center">
              <template slot-scope="scope">
                <span>{{ formatMinutes(scope.row.serviceMinutes) }}</span>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="activityTotal > 0"
            :total="activityTotal"
            :page.sync="activityQueryParams.pageNum"
            :limit.sync="activityQueryParams.pageSize"
            @pagination="getActivityList"
          />
        </el-tab-pane>

        <el-tab-pane label="志愿者排行" name="volunteers">
          <el-table v-loading="volunteerLoading" class="voluntary-admin-table" :data="volunteerList">
            <el-table-column
              label="排名"
              type="index"
              width="70"
              align="center"
              :index="volunteerIndex"
            />
            <el-table-column label="志愿者" prop="realName" min-width="150" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <div class="statistics-main-cell">
                  <span>{{ scope.row.realName || '-' }}</span>
                  <small>{{ scope.row.phone || '-' }}</small>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="组织" prop="organization" min-width="160" :show-overflow-tooltip="true" />
            <el-table-column label="服务次数" prop="serviceCount" width="100" align="center" />
            <el-table-column label="服务时长" prop="serviceMinutes" width="140" align="center">
              <template slot-scope="scope">
                <span>{{ formatMinutes(scope.row.serviceMinutes) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="最近服务" prop="latestServiceDate" width="130" align="center" />
          </el-table>
          <pagination
            v-show="volunteerTotal > 0"
            :total="volunteerTotal"
            :page.sync="volunteerQueryParams.pageNum"
            :limit.sync="volunteerQueryParams.pageSize"
            @pagination="getVolunteerList"
          />
        </el-tab-pane>

        <el-tab-pane label="组织统计" name="organizations">
          <el-table v-loading="organizationLoading" class="voluntary-admin-table" :data="organizationList">
            <el-table-column label="组织" prop="organization" min-width="180" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span>{{ scope.row.organization || '未设置组织' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="志愿者数" prop="volunteerCount" width="110" align="center" />
            <el-table-column label="服务次数" prop="serviceCount" width="110" align="center" />
            <el-table-column label="服务时长" prop="serviceMinutes" width="140" align="center">
              <template slot-scope="scope">
                <span>{{ formatMinutes(scope.row.serviceMinutes) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="最近服务" prop="latestServiceDate" width="130" align="center" />
          </el-table>
          <pagination
            v-show="organizationTotal > 0"
            :total="organizationTotal"
            :page.sync="organizationQueryParams.pageNum"
            :limit.sync="organizationQueryParams.pageSize"
            @pagination="getOrganizationList"
          />
        </el-tab-pane>

        <el-tab-pane label="服务趋势" name="trend">
          <div v-loading="trendLoading" ref="trendChart" class="statistics-trend-chart"></div>
          <el-table class="voluntary-admin-table statistics-trend-table" :data="trendList">
            <el-table-column label="日期" prop="statDate" min-width="130" />
            <el-table-column label="服务记录" prop="serviceCount" width="120" align="center" />
            <el-table-column label="服务时长" prop="serviceMinutes" width="140" align="center">
              <template slot-scope="scope">
                <span>{{ formatMinutes(scope.row.serviceMinutes) }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import {
  getStatisticsOverview,
  listActivityStatistics,
  listVolunteerStatistics,
  listOrganizationStatistics,
  listTrendStatistics
} from '@/api/voluntary/statistics'

export default {
  name: 'VoluntaryStatistics',
  data() {
    return {
      showSearch: true,
      activeTab: 'activities',
      dateRange: [],
      queryParams: {
        activityTitle: undefined,
        organization: undefined,
        volunteerRealName: undefined,
        trendType: 'day'
      },
      overviewLoading: false,
      overview: {},
      activityLoading: false,
      activityList: [],
      activityTotal: 0,
      activityQueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      volunteerLoading: false,
      volunteerList: [],
      volunteerTotal: 0,
      volunteerQueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      organizationLoading: false,
      organizationList: [],
      organizationTotal: 0,
      organizationQueryParams: {
        pageNum: 1,
        pageSize: 10
      },
      trendLoading: false,
      trendList: [],
      trendChart: null
    }
  },
  computed: {
    overviewCards() {
      const data = this.overview || {}
      return [
        {
          key: 'volunteers',
          icon: 'el-icon-user',
          label: '志愿者总数',
          value: this.formatNumber(data.volunteerTotal),
          extra: '已审核 ' + this.formatNumber(data.approvedVolunteerTotal) + ' 人'
        },
        {
          key: 'activities',
          icon: 'el-icon-date',
          label: '活动总数',
          value: this.formatNumber(data.activityTotal),
          extra: '已发布 ' + this.formatNumber(data.publishedActivityTotal) + ' 场'
        },
        {
          key: 'signups',
          icon: 'el-icon-tickets',
          label: '报名总数',
          value: this.formatNumber(data.signupTotal),
          extra: '通过率 ' + this.formatRate(data.approvedSignupTotal, data.signupTotal)
        },
        {
          key: 'checkins',
          icon: 'el-icon-finished',
          label: '签到签退',
          value: this.formatNumber(data.checkinTotal) + ' / ' + this.formatNumber(data.checkoutTotal),
          extra: '签到 / 签退'
        },
        {
          key: 'services',
          icon: 'el-icon-time',
          label: '有效服务记录',
          value: this.formatNumber(data.serviceRecordTotal),
          extra: '累计 ' + this.formatMinutes(data.serviceMinutesTotal)
        },
        {
          key: 'abnormal',
          icon: 'el-icon-warning-outline',
          label: '异常记录',
          value: this.formatNumber(data.abnormalTotal),
          extra: '需复核签到或服务时长'
        }
      ]
    }
  },
  created() {
    this.refreshAll()
  },
  mounted() {
    window.addEventListener('resize', this.resizeTrendChart)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeTrendChart)
    if (this.trendChart) {
      this.trendChart.dispose()
      this.trendChart = null
    }
  },
  methods: {
    refreshAll() {
      this.getOverview()
      this.getActivityList()
      this.getVolunteerList()
      this.getOrganizationList()
      this.getTrendList()
    },
    buildQuery(extra) {
      const params = Object.assign({}, this.queryParams, extra || {})
      if (this.dateRange && this.dateRange.length === 2) {
        params.beginDate = this.dateRange[0]
        params.endDate = this.dateRange[1]
      }
      return params
    },
    getOverview() {
      this.overviewLoading = true
      getStatisticsOverview(this.buildQuery()).then(response => {
        this.overview = response.data || {}
        this.overviewLoading = false
      }).catch(() => {
        this.overviewLoading = false
      })
    },
    getActivityList() {
      this.activityLoading = true
      listActivityStatistics(this.buildQuery(this.activityQueryParams)).then(response => {
        this.activityList = response.rows || []
        this.activityTotal = response.total || 0
        this.activityLoading = false
      }).catch(() => {
        this.activityLoading = false
      })
    },
    getVolunteerList() {
      this.volunteerLoading = true
      listVolunteerStatistics(this.buildQuery(this.volunteerQueryParams)).then(response => {
        this.volunteerList = response.rows || []
        this.volunteerTotal = response.total || 0
        this.volunteerLoading = false
      }).catch(() => {
        this.volunteerLoading = false
      })
    },
    getOrganizationList() {
      this.organizationLoading = true
      listOrganizationStatistics(this.buildQuery(this.organizationQueryParams)).then(response => {
        this.organizationList = response.rows || []
        this.organizationTotal = response.total || 0
        this.organizationLoading = false
      }).catch(() => {
        this.organizationLoading = false
      })
    },
    getTrendList() {
      this.trendLoading = true
      listTrendStatistics(this.buildQuery()).then(response => {
        this.trendList = response.data || []
        this.trendLoading = false
        this.renderTrendChart()
      }).catch(() => {
        this.trendLoading = false
        this.renderTrendChart()
      })
    },
    handleQuery() {
      this.activityQueryParams.pageNum = 1
      this.volunteerQueryParams.pageNum = 1
      this.organizationQueryParams.pageNum = 1
      this.refreshAll()
    },
    resetQuery() {
      this.dateRange = []
      this.queryParams = {
        activityTitle: undefined,
        organization: undefined,
        volunteerRealName: undefined,
        trendType: 'day'
      }
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleTabClick(tab) {
      if (tab.name === 'trend') {
        this.$nextTick(() => {
          this.renderTrendChart()
          this.resizeTrendChart()
        })
      }
    },
    renderTrendChart() {
      this.$nextTick(() => {
        if (!this.$refs.trendChart) {
          return
        }
        if (!this.trendChart) {
          this.trendChart = echarts.init(this.$refs.trendChart)
        }
        const names = this.trendList.map(item => item.statDate)
        const counts = this.trendList.map(item => Number(item.serviceCount || 0))
        const minutes = this.trendList.map(item => Number(item.serviceMinutes || 0))
        this.trendChart.setOption({
          color: ['#238276', '#b7791f'],
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            top: 0,
            data: ['服务记录', '服务分钟']
          },
          grid: {
            left: 40,
            right: 28,
            top: 46,
            bottom: 36
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: names
          },
          yAxis: {
            type: 'value',
            minInterval: 1
          },
          series: [
            {
              name: '服务记录',
              type: 'line',
              smooth: true,
              data: counts
            },
            {
              name: '服务分钟',
              type: 'line',
              smooth: true,
              data: minutes
            }
          ],
          title: names.length === 0
            ? {
              text: '暂无趋势数据',
              left: 'center',
              top: 'middle',
              textStyle: {
                color: '#9aa6a3',
                fontSize: 14,
                fontWeight: 400
              }
            }
            : null
        }, true)
      })
    },
    resizeTrendChart() {
      if (this.trendChart) {
        this.trendChart.resize()
      }
    },
    volunteerIndex(index) {
      return (this.volunteerQueryParams.pageNum - 1) * this.volunteerQueryParams.pageSize + index + 1
    },
    formatNumber(value) {
      return Number(value || 0).toLocaleString()
    },
    formatRate(part, total) {
      const denominator = Number(total || 0)
      if (denominator <= 0) {
        return '0%'
      }
      const rate = (Number(part || 0) * 100) / denominator
      return rate.toFixed(1).replace(/\.0$/, '') + '%'
    },
    formatMinutes(minutes) {
      const total = Number(minutes || 0)
      const hours = Math.floor(total / 60)
      const rest = total % 60
      if (hours <= 0) {
        return rest + ' 分钟'
      }
      if (rest === 0) {
        return hours + ' 小时'
      }
      return hours + ' 小时 ' + rest + ' 分钟'
    }
  }
}
</script>

<style scoped>
.statistics-toolbar {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 12px;
}

.statistics-summary-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
  margin-bottom: 18px;
}

.statistics-summary-item {
  min-height: 104px;
  padding: 16px;
  border: 1px solid #e2e8e5;
  border-radius: 8px;
  background: #fff;
}

.statistics-summary-head {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #63716d;
  font-size: 13px;
}

.statistics-summary-head i {
  color: #238276;
  font-size: 18px;
}

.statistics-summary-item strong {
  display: block;
  margin-top: 12px;
  color: #17211f;
  font-size: 28px;
  line-height: 1.2;
}

.statistics-summary-item small {
  display: block;
  margin-top: 8px;
  color: #7b8884;
  line-height: 1.4;
}

.statistics-tabs {
  margin-top: 4px;
}

.statistics-main-cell {
  display: flex;
  flex-direction: column;
  gap: 3px;
  min-width: 0;
}

.statistics-main-cell span {
  color: #17211f;
  font-weight: 600;
}

.statistics-main-cell small {
  color: #7b8884;
  font-size: 12px;
}

.statistics-trend-chart {
  width: 100%;
  height: 320px;
  margin-bottom: 14px;
  border: 1px solid #e2e8e5;
  border-radius: 8px;
}

.statistics-trend-table {
  margin-top: 8px;
}

@media (max-width: 1200px) {
  .statistics-summary-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 720px) {
  .statistics-summary-grid {
    grid-template-columns: 1fr;
  }

  .statistics-summary-item strong {
    font-size: 24px;
  }
}
</style>
