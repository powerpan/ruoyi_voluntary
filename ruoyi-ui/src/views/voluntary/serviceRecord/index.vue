<template>
  <div class="voluntary-admin-page service-record-page">
    <el-card class="voluntary-admin-card" shadow="never">
      <div slot="header" class="voluntary-admin-title">
        <div>
          <h2>服务记录</h2>
          <p>查看志愿者有效服务记录、服务时长和生成来源。</p>
        </div>
        <span class="meta">时长记录</span>
      </div>

      <el-form
        ref="queryForm"
        class="voluntary-admin-filter"
        :model="queryParams"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="活动" prop="activityTitle">
          <el-input
            v-model="queryParams.activityTitle"
            placeholder="请输入活动标题"
            clearable
            style="width: 190px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="volunteerRealName">
          <el-input
            v-model="queryParams.volunteerRealName"
            placeholder="请输入姓名"
            clearable
            style="width: 150px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机" prop="volunteerPhone">
          <el-input
            v-model="queryParams.volunteerPhone"
            placeholder="请输入手机"
            clearable
            style="width: 150px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="记录状态" clearable style="width: 140px">
            <el-option
              v-for="dict in dict.type.vol_service_record_status"
              :key="dict.value"
              :label="dict.label"
              :value="Number(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="服务日期">
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
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['manager:voluntary:serviceRecord:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" class="voluntary-admin-table" :data="recordList">
        <el-table-column label="活动" prop="activityTitle" min-width="190" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="service-record-main-cell">
              <span>{{ scope.row.activityTitle || '-' }}</span>
              <small>{{ scope.row.serviceLocation || '-' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="志愿者" prop="volunteerRealName" min-width="140" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="service-record-main-cell">
              <span>{{ scope.row.volunteerRealName || '-' }}</span>
              <small>{{ scope.row.volunteerPhone || '-' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="服务日期" prop="serviceDate" width="120" align="center" />
        <el-table-column label="计入时间" min-width="210">
          <template slot-scope="scope">
            <span>{{ formatRange(scope.row.startTime, scope.row.endTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="分钟" prop="serviceMinutes" width="90" align="center">
          <template slot-scope="scope">
            <strong>{{ scope.row.serviceMinutes || 0 }}</strong>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="100" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_service_record_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="生成时间" prop="createTime" width="150" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) || '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
              v-hasPermi="['manager:voluntary:serviceRecord:query']"
            >详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-card>

    <el-dialog
      title="服务记录详情"
      :visible.sync="detailOpen"
      width="820px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-descriptions v-if="detail" :column="2" border size="small">
        <el-descriptions-item label="活动">{{ detail.activityTitle || '-' }}</el-descriptions-item>
        <el-descriptions-item label="服务地点">{{ detail.serviceLocation || '-' }}</el-descriptions-item>
        <el-descriptions-item label="志愿者">{{ detail.volunteerRealName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detail.volunteerPhone || '-' }}</el-descriptions-item>
        <el-descriptions-item label="所属组织">{{ detail.volunteerOrganization || '-' }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ selectDictLabel(dict.type.vol_service_record_status, detail.status) }}</el-descriptions-item>
        <el-descriptions-item label="服务日期">{{ detail.serviceDate || '-' }}</el-descriptions-item>
        <el-descriptions-item label="服务分钟">{{ detail.serviceMinutes || 0 }}</el-descriptions-item>
        <el-descriptions-item label="计入时间" :span="2">{{ formatRange(detail.startTime, detail.endTime) }}</el-descriptions-item>
        <el-descriptions-item label="签到记录ID">{{ detail.checkinRecordId || '-' }}</el-descriptions-item>
        <el-descriptions-item label="报名ID">{{ detail.signupId || '-' }}</el-descriptions-item>
        <el-descriptions-item label="确认人ID">{{ detail.confirmUserId || '-' }}</el-descriptions-item>
        <el-descriptions-item label="确认时间">{{ parseTime(detail.confirmTime) || '-' }}</el-descriptions-item>
        <el-descriptions-item label="修正原因" :span="2">{{ detail.adjustReason || '-' }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getServiceRecord, listServiceRecord } from '@/api/voluntary/serviceRecord'

export default {
  name: 'VoluntaryServiceRecord',
  dicts: ['vol_service_record_status'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      recordList: [],
      dateRange: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activityTitle: undefined,
        volunteerRealName: undefined,
        volunteerPhone: undefined,
        status: undefined
      },
      detailOpen: false,
      detail: null
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listServiceRecord(this.addDateRange({ ...this.queryParams }, this.dateRange)).then(response => {
        this.recordList = response.rows || []
        this.total = response.total || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleDetail(row) {
      getServiceRecord(row.id).then(response => {
        this.detail = response.data
        this.detailOpen = true
      })
    },
    handleExport() {
      this.download(
        'manager/voluntary/service-records/export',
        this.addDateRange({ ...this.queryParams }, this.dateRange),
        `服务记录_${new Date().getTime()}.xlsx`
      )
    },
    formatRange(start, end) {
      const startText = this.parseTime(start) || '-'
      const endText = this.parseTime(end) || '-'
      return startText + ' 至 ' + endText
    }
  }
}
</script>

<style scoped>
.service-record-main-cell {
  display: flex;
  flex-direction: column;
  gap: 3px;
  min-width: 0;
}

.service-record-main-cell span {
  color: #17211f;
  font-weight: 600;
}

.service-record-main-cell small {
  color: #7b8884;
  font-size: 12px;
}
</style>
