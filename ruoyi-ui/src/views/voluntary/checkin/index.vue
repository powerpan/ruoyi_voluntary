<template>
  <div class="voluntary-admin-page checkin-manage-page">
    <el-card class="voluntary-admin-card" shadow="never">
      <div slot="header" class="voluntary-admin-title">
        <div>
          <h2>签到管理</h2>
          <p>查看活动签到签退记录，生成签到和签退令牌入口。</p>
        </div>
        <span class="meta">签到签退</span>
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
          <el-select v-model="queryParams.status" placeholder="签到状态" clearable style="width: 140px">
            <el-option
              v-for="dict in dict.type.vol_checkin_status"
              :key="dict.value"
              :label="dict.label"
              :value="Number(dict.value)"
            />
          </el-select>
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
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-key"
            size="mini"
            @click="openQrDialog"
            v-hasPermi="['manager:voluntary:checkin:qr']"
          >令牌</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['manager:voluntary:checkin:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" class="voluntary-admin-table" :data="checkinList">
        <el-table-column label="活动" prop="activityTitle" min-width="190" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="checkin-main-cell">
              <span>{{ scope.row.activityTitle || '-' }}</span>
              <small>{{ scope.row.serviceLocation || '-' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="志愿者" prop="volunteerRealName" min-width="140" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="checkin-main-cell">
              <span>{{ scope.row.volunteerRealName || '-' }}</span>
              <small>{{ scope.row.volunteerPhone || '-' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="活动时间" min-width="210">
          <template slot-scope="scope">
            <span>{{ formatRange(scope.row.activityStartTime, scope.row.activityEndTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="签到时间" prop="checkinTime" width="150" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.checkinTime) || '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="签退时间" prop="checkoutTime" width="150" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.checkoutTime) || '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="方式" width="90" align="center">
          <template slot-scope="scope">
            <span>{{ methodLabel(scope.row.checkinMethod) }} / {{ methodLabel(scope.row.checkoutMethod) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="100" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_checkin_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
              v-hasPermi="['manager:voluntary:checkin:query']"
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
      title="活动签到令牌"
      :visible.sync="qrOpen"
      width="920px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-form ref="qrForm" :model="qrForm" :rules="qrRules" label-width="96px" size="small">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="活动" prop="activityId">
              <el-select
                v-model="qrForm.activityId"
                placeholder="选择已发布活动"
                filterable
                style="width: 100%"
                @change="loadQrTokens"
              >
                <el-option
                  v-for="activity in publishedActivities"
                  :key="activity.id"
                  :label="activity.title"
                  :value="activity.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="类型" prop="actionType">
              <el-select v-model="qrForm.actionType" style="width: 100%">
                <el-option label="签到" value="checkin" />
                <el-option label="签退" value="checkout" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="有效分钟" prop="expireMinutes">
              <el-input-number v-model="qrForm.expireMinutes" :min="1" :max="1440" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div class="qr-dialog-actions">
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="submitQr">生成令牌</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="loadQrTokens">刷新</el-button>
      </div>

      <el-alert
        v-if="generatedToken"
        class="qr-result"
        type="success"
        :closable="false"
        show-icon
      >
        <template slot="title">
          <span>{{ actionLabel(generatedToken.actionType) }}令牌已生成</span>
        </template>
        <div class="token-preview-block">
          <div class="token-preview-copy">
            <strong>现场令牌使用</strong>
            <p>管理员复制令牌或入口地址给志愿者，志愿者在用户端令牌签到页确认签到或签退。</p>
          </div>
        </div>
        <div class="token-copy-row">
          <span>令牌</span>
          <el-input :value="generatedToken.token" readonly size="small" />
          <el-button
            size="small"
            icon="el-icon-document-copy"
            v-clipboard="generatedToken.token"
            v-clipboard:success="handleCopySuccess"
            v-clipboard:error="handleCopyError"
          >复制</el-button>
        </div>
        <div class="token-copy-row">
          <span>入口地址</span>
          <el-input :value="generatedToken.scanUrl" readonly size="small" />
          <el-button
            size="small"
            icon="el-icon-document-copy"
            v-clipboard="generatedToken.scanUrl"
            v-clipboard:success="handleCopySuccess"
            v-clipboard:error="handleCopyError"
          >复制</el-button>
        </div>
      </el-alert>

      <el-table v-loading="qrLoading" :data="qrTokenList" size="small" class="voluntary-admin-table">
        <el-table-column label="类型" prop="actionType" width="90" align="center">
          <template slot-scope="scope">
            <span>{{ actionLabel(scope.row.actionType) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="令牌" prop="token" min-width="220" :show-overflow-tooltip="true" />
        <el-table-column label="入口地址" prop="scanUrl" min-width="260" :show-overflow-tooltip="true" />
        <el-table-column label="过期时间" prop="expireTime" width="150" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.expireTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="90" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_qr_token_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="110" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-close"
              :disabled="scope.row.status !== 0"
              @click="handleDisableQr(scope.row)"
            >停用</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="qrOpen = false">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="签到详情"
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
        <el-descriptions-item label="状态">{{ selectDictLabel(dict.type.vol_checkin_status, detail.status) }}</el-descriptions-item>
        <el-descriptions-item label="活动时间">{{ formatRange(detail.activityStartTime, detail.activityEndTime) }}</el-descriptions-item>
        <el-descriptions-item label="签到时间">{{ parseTime(detail.checkinTime) || '-' }}</el-descriptions-item>
        <el-descriptions-item label="签退时间">{{ parseTime(detail.checkoutTime) || '-' }}</el-descriptions-item>
        <el-descriptions-item label="签到方式">{{ methodLabel(detail.checkinMethod) }}</el-descriptions-item>
        <el-descriptions-item label="签退方式">{{ methodLabel(detail.checkoutMethod) }}</el-descriptions-item>
        <el-descriptions-item label="异常原因" :span="2">{{ detail.abnormalReason || '-' }}</el-descriptions-item>
        <el-descriptions-item label="人工说明" :span="2">{{ detail.manualReason || '-' }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listActivity } from '@/api/voluntary/activity'
import { disableQrToken, generateQrToken, getCheckin, listCheckin, listQrToken } from '@/api/voluntary/checkin'

export default {
  name: 'VoluntaryCheckin',
  dicts: ['vol_checkin_status', 'vol_qr_token_status'],
  data() {
    return {
      loading: false,
      qrLoading: false,
      showSearch: true,
      total: 0,
      checkinList: [],
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
      detail: null,
      qrOpen: false,
      publishedActivities: [],
      qrTokenList: [],
      generatedToken: null,
      qrForm: {
        activityId: undefined,
        actionType: 'checkin',
        expireMinutes: 120
      },
      qrRules: {
        activityId: [{ required: true, message: '请选择活动', trigger: 'change' }],
        actionType: [{ required: true, message: '请选择令牌类型', trigger: 'change' }],
        expireMinutes: [{ required: true, message: '请填写有效分钟', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCheckin(this.addDateRange({ ...this.queryParams }, this.dateRange)).then(response => {
        this.checkinList = response.rows || []
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
      getCheckin(row.id).then(response => {
        this.detail = response.data
        this.detailOpen = true
      })
    },
    openQrDialog() {
      this.generatedToken = null
      this.qrOpen = true
      this.loadPublishedActivities()
    },
    loadPublishedActivities() {
      listActivity({ pageNum: 1, pageSize: 100, status: 1 }).then(response => {
        this.publishedActivities = response.rows || []
        if (!this.qrForm.activityId && this.publishedActivities.length > 0) {
          this.qrForm.activityId = this.publishedActivities[0].id
        }
        this.loadQrTokens()
      })
    },
    loadQrTokens() {
      if (!this.qrForm.activityId) {
        this.qrTokenList = []
        return
      }
      this.qrLoading = true
      listQrToken(this.qrForm.activityId, { pageNum: 1, pageSize: 10 }).then(response => {
        this.qrTokenList = response.rows || []
        this.qrLoading = false
      }).catch(() => {
        this.qrLoading = false
      })
    },
    submitQr() {
      this.$refs.qrForm.validate(valid => {
        if (!valid) {
          return
        }
        generateQrToken(this.qrForm.activityId, {
          actionType: this.qrForm.actionType,
          expireMinutes: this.qrForm.expireMinutes
        }).then(response => {
          this.generatedToken = response.data
          this.$modal.msgSuccess('令牌入口已生成')
          this.loadQrTokens()
        })
      })
    },
    handleDisableQr(row) {
      this.$modal.confirm('确认停用该令牌？').then(() => {
        return disableQrToken(row.id)
      }).then(() => {
        this.$modal.msgSuccess('令牌已停用')
        this.loadQrTokens()
      })
    },
    handleCopySuccess() {
      this.$modal.msgSuccess('已复制令牌内容')
    },
    handleCopyError() {
      this.$modal.msgError('复制失败，请手动复制')
    },
    handleExport() {
      this.download(
        'manager/voluntary/checkins/export',
        this.addDateRange({ ...this.queryParams }, this.dateRange),
        `签到记录_${new Date().getTime()}.xlsx`
      )
    },
    methodLabel(value) {
      if (value === 'qr') {
        return '令牌'
      }
      if (value === 'manual') {
        return '人工'
      }
      return '-'
    },
    actionLabel(value) {
      if (value === 'checkin') {
        return '签到'
      }
      if (value === 'checkout') {
        return '签退'
      }
      return value || '-'
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
.checkin-main-cell {
  display: flex;
  flex-direction: column;
  gap: 3px;
  min-width: 0;
}

.checkin-main-cell span {
  color: #17211f;
  font-weight: 600;
}

.checkin-main-cell small {
  color: #7b8884;
  font-size: 12px;
}

.qr-dialog-actions {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 14px;
}

.qr-result {
  margin-bottom: 14px;
}

.token-copy-row {
  display: grid;
  grid-template-columns: 72px minmax(0, 1fr) auto;
  align-items: center;
  gap: 8px;
  margin-top: 8px;
}

.token-copy-row span {
  color: #63716d;
  font-weight: 600;
}

.token-preview-block {
  margin-top: 10px;
  padding: 12px 14px;
  border: 1px solid #dfe8e4;
  border-radius: 8px;
  background: #f6fbf9;
}

.token-preview-copy strong {
  color: #17211f;
  font-size: 15px;
}

.token-preview-copy p {
  margin: 8px 0 0;
  color: #63716d;
  line-height: 1.6;
}
</style>
