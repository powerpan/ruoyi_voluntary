<template>
  <div class="voluntary-admin-page notification-page">
    <el-card class="voluntary-admin-card" shadow="never">
      <div slot="header" class="voluntary-admin-title">
        <div>
          <h2>通知记录</h2>
          <p>查看志愿业务站内通知、接收人、业务对象和已读状态。</p>
        </div>
        <span class="meta">业务通知</span>
      </div>

      <el-form
        ref="queryForm"
        class="voluntary-admin-filter"
        :model="queryParams"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="接收人" prop="receiverUserName">
          <el-input
            v-model="queryParams.receiverUserName"
            placeholder="账号或昵称"
            clearable
            style="width: 160px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="queryParams.title"
            placeholder="请输入通知标题"
            clearable
            style="width: 190px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="noticeType">
          <el-select v-model="queryParams.noticeType" placeholder="通知类型" clearable style="width: 150px">
            <el-option
              v-for="dict in dict.type.vol_notification_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="阅读状态" clearable style="width: 130px">
            <el-option
              v-for="dict in dict.type.vol_notification_status"
              :key="dict.value"
              :label="dict.label"
              :value="Number(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="对象" prop="targetType">
          <el-input
            v-model="queryParams.targetType"
            placeholder="业务对象"
            clearable
            style="width: 140px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="创建时间">
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
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" class="voluntary-admin-table" :data="notificationList">
        <el-table-column label="通知" prop="title" min-width="220" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="notification-main-cell">
              <span>{{ scope.row.title || '-' }}</span>
              <small>{{ scope.row.content || '-' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="接收人" min-width="140" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="notification-main-cell">
              <span>{{ scope.row.receiverNickName || scope.row.receiverUserName || '-' }}</span>
              <small>{{ scope.row.receiverUserName || ('ID ' + (scope.row.receiverUserId || '-')) }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="类型" prop="noticeType" width="120" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_notification_type" :value="scope.row.noticeType" />
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="90" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_notification_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="业务对象" min-width="140" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="notification-main-cell">
              <span>{{ targetLabel(scope.row.targetType) }}</span>
              <small>ID {{ scope.row.targetId || '-' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="150" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) || '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="阅读时间" prop="readTime" width="150" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.readTime) || '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
              v-hasPermi="['manager:voluntary:notification:query']"
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
      title="通知详情"
      :visible.sync="detailOpen"
      width="820px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-descriptions v-if="detail" :column="2" border size="small">
        <el-descriptions-item label="标题" :span="2">{{ detail.title || '-' }}</el-descriptions-item>
        <el-descriptions-item label="类型">{{ selectDictLabel(dict.type.vol_notification_type, detail.noticeType) }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ selectDictLabel(dict.type.vol_notification_status, detail.status) }}</el-descriptions-item>
        <el-descriptions-item label="接收人">{{ receiverText(detail) }}</el-descriptions-item>
        <el-descriptions-item label="触发人">{{ actorText(detail) }}</el-descriptions-item>
        <el-descriptions-item label="业务对象">{{ targetLabel(detail.targetType) }}</el-descriptions-item>
        <el-descriptions-item label="对象ID">{{ detail.targetId || '-' }}</el-descriptions-item>
        <el-descriptions-item label="跳转地址" :span="2">{{ detail.actionUrl || '-' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ parseTime(detail.createTime) || '-' }}</el-descriptions-item>
        <el-descriptions-item label="阅读时间">{{ parseTime(detail.readTime) || '-' }}</el-descriptions-item>
        <el-descriptions-item label="通知内容" :span="2">{{ detail.content || '-' }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getNotification, listNotification } from '@/api/voluntary/notification'

export default {
  name: 'VoluntaryNotification',
  dicts: ['vol_notification_type', 'vol_notification_status'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      notificationList: [],
      dateRange: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        receiverUserName: undefined,
        title: undefined,
        noticeType: undefined,
        status: undefined,
        targetType: undefined
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
      listNotification(this.addDateRange({ ...this.queryParams }, this.dateRange)).then(response => {
        this.notificationList = response.rows || []
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
      getNotification(row.id).then(response => {
        this.detail = response.data
        this.detailOpen = true
      })
    },
    receiverText(row) {
      if (!row) {
        return '-'
      }
      return row.receiverNickName || row.receiverUserName || ('ID ' + (row.receiverUserId || '-'))
    },
    actorText(row) {
      if (!row) {
        return '-'
      }
      return row.actorNickName || row.actorUserName || (row.actorUserId ? 'ID ' + row.actorUserId : '-')
    },
    targetLabel(value) {
      const labels = {
        volunteer_profile: '志愿者档案',
        signup: '活动报名',
        activity: '活动',
        checkin: '签到记录',
        service_record: '服务记录',
        p5c_smoke: '接口验证'
      }
      return labels[value] || value || '-'
    }
  }
}
</script>

<style scoped>
.notification-main-cell {
  display: flex;
  flex-direction: column;
  gap: 3px;
  min-width: 0;
}

.notification-main-cell span {
  color: #17211f;
  font-weight: 600;
}

.notification-main-cell small {
  color: #7b8884;
  font-size: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
