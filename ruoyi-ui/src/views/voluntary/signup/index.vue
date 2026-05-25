<template>
  <div class="voluntary-admin-page signup-manage-page">
    <el-card class="voluntary-admin-card" shadow="never">
      <div slot="header" class="voluntary-admin-title">
        <div>
          <h2>报名管理</h2>
          <p>查看活动报名人员，执行通过、拒绝和候补筛选。</p>
        </div>
        <span class="meta">报名筛选</span>
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
        <el-form-item label="姓名" prop="realName">
          <el-input
            v-model="queryParams.realName"
            placeholder="请输入姓名"
            clearable
            style="width: 150px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input
            v-model="queryParams.phone"
            placeholder="请输入手机"
            clearable
            style="width: 150px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="activityType">
          <el-select v-model="queryParams.activityType" placeholder="活动类型" clearable style="width: 140px">
            <el-option
              v-for="dict in dict.type.vol_activity_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="报名状态" clearable style="width: 140px">
            <el-option
              v-for="dict in dict.type.vol_signup_status"
              :key="dict.value"
              :label="dict.label"
              :value="Number(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-check"
            size="mini"
            :disabled="single || !canReview(selectedRow)"
            @click="handleReview(selectedRow, 1)"
            v-hasPermi="['manager:voluntary:signup:review']"
          >通过</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-time"
            size="mini"
            :disabled="single || !canReview(selectedRow)"
            @click="handleReview(selectedRow, 3)"
            v-hasPermi="['manager:voluntary:signup:review']"
          >候补</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-close"
            size="mini"
            :disabled="single || !canReview(selectedRow)"
            @click="handleReview(selectedRow, 2)"
            v-hasPermi="['manager:voluntary:signup:review']"
          >拒绝</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['manager:voluntary:signup:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table
        v-loading="loading"
        class="voluntary-admin-table"
        :data="signupList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="48" align="center" />
        <el-table-column label="活动" prop="activityTitle" min-width="190" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="signup-activity-cell">
              <span>{{ scope.row.activityTitle || '-' }}</span>
              <small>{{ scope.row.serviceLocation || '-' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="志愿者" prop="realName" min-width="130" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="signup-activity-cell">
              <span>{{ scope.row.realName || '-' }}</span>
              <small>{{ scope.row.organization || '未设置组织' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="活动时间" min-width="210">
          <template slot-scope="scope">
            <span>{{ formatRange(scope.row.activityStartTime, scope.row.activityEndTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="100" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_signup_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="报名时间" prop="createTime" width="130" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
              v-hasPermi="['manager:voluntary:signup:query']"
            >详情</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              :disabled="!canReview(scope.row)"
              @click="handleReview(scope.row, 1)"
              v-hasPermi="['manager:voluntary:signup:review']"
            >通过</el-button>
            <el-dropdown size="mini" @command="command => handleCommand(command, scope.row)">
              <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  command="waitlist"
                  icon="el-icon-time"
                  :disabled="!canReview(scope.row)"
                  v-hasPermi="['manager:voluntary:signup:review']"
                >候补</el-dropdown-item>
                <el-dropdown-item
                  command="reject"
                  icon="el-icon-close"
                  :disabled="!canReview(scope.row)"
                  v-hasPermi="['manager:voluntary:signup:review']"
                >拒绝</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
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
      :title="reviewTitle"
      :visible.sync="reviewOpen"
      width="540px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-form ref="reviewForm" :model="reviewForm" :rules="reviewRules" label-width="96px">
        <el-form-item label="活动">
          <span>{{ reviewTarget.activityTitle || '-' }}</span>
        </el-form-item>
        <el-form-item label="志愿者">
          <span>{{ reviewTarget.realName || '-' }} / {{ reviewTarget.phone || '-' }}</span>
        </el-form-item>
        <el-form-item label="当前状态">
          <dict-tag :options="dict.type.vol_signup_status" :value="reviewTarget.status" />
        </el-form-item>
        <el-form-item label="筛选意见" prop="reviewReason">
          <el-input
            v-model="reviewForm.reviewReason"
            type="textarea"
            :rows="4"
            :placeholder="reviewPlaceholder"
            maxlength="300"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitReview">确 定</el-button>
        <el-button @click="reviewOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="报名详情"
      :visible.sync="detailOpen"
      width="860px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-descriptions v-if="detail" :column="2" border size="small">
        <el-descriptions-item label="活动标题">{{ detail.activityTitle || '-' }}</el-descriptions-item>
        <el-descriptions-item label="活动状态">{{ selectDictLabel(dict.type.vol_activity_status, detail.activityStatus) }}</el-descriptions-item>
        <el-descriptions-item label="活动类型">{{ selectDictLabel(dict.type.vol_activity_type, detail.activityType) }}</el-descriptions-item>
        <el-descriptions-item label="服务地点">{{ detail.serviceLocation || '-' }}</el-descriptions-item>
        <el-descriptions-item label="活动时间">{{ formatRange(detail.activityStartTime, detail.activityEndTime) }}</el-descriptions-item>
        <el-descriptions-item label="报名状态">{{ selectDictLabel(dict.type.vol_signup_status, detail.status) }}</el-descriptions-item>
        <el-descriptions-item label="志愿者">{{ detail.realName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detail.phone || '-' }}</el-descriptions-item>
        <el-descriptions-item label="所属组织">{{ detail.organization || '-' }}</el-descriptions-item>
        <el-descriptions-item label="报名时间">{{ parseTime(detail.createTime) || '-' }}</el-descriptions-item>
        <el-descriptions-item label="报名理由" :span="2">{{ detail.applyReason || '-' }}</el-descriptions-item>
        <el-descriptions-item label="相关经验" :span="2">{{ detail.experience || '-' }}</el-descriptions-item>
        <el-descriptions-item label="筛选意见" :span="2">{{ detail.reviewReason || '-' }}</el-descriptions-item>
        <el-descriptions-item label="处理人">{{ detail.reviewerName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="处理时间">{{ parseTime(detail.reviewTime) || '-' }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getSignup, listSignup, reviewSignup } from '@/api/voluntary/signup'

export default {
  name: 'VoluntarySignup',
  dicts: ['vol_activity_type', 'vol_activity_status', 'vol_signup_status'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      signupList: [],
      selectedRow: null,
      single: true,
      reviewOpen: false,
      detailOpen: false,
      detail: null,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activityTitle: undefined,
        realName: undefined,
        phone: undefined,
        activityType: undefined,
        status: undefined
      },
      reviewTarget: {},
      reviewForm: {
        status: undefined,
        reviewReason: ''
      }
    }
  },
  computed: {
    reviewTitle() {
      if (this.reviewForm.status === 1) {
        return '通过报名'
      }
      if (this.reviewForm.status === 2) {
        return '拒绝报名'
      }
      return '设为候补'
    },
    reviewPlaceholder() {
      if (this.reviewForm.status === 2) {
        return '请填写拒绝原因'
      }
      return '可填写筛选说明'
    },
    reviewRules() {
      const requireReason = this.reviewForm.status === 2
      return {
        reviewReason: requireReason
          ? [{ required: true, message: '拒绝报名时必须填写原因', trigger: 'blur' }]
          : []
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listSignup(this.queryParams).then(response => {
        this.signupList = response.rows || []
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
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.single = selection.length !== 1
      this.selectedRow = selection.length === 1 ? selection[0] : null
    },
    handleCommand(command, row) {
      if (command === 'waitlist') {
        this.handleReview(row, 3)
      } else if (command === 'reject') {
        this.handleReview(row, 2)
      }
    },
    handleReview(row, status) {
      if (!row || !this.canReview(row)) {
        return
      }
      this.reviewTarget = row
      this.reviewForm = {
        status: status,
        reviewReason: ''
      }
      this.reviewOpen = true
      this.$nextTick(() => this.resetForm('reviewForm'))
    },
    submitReview() {
      this.$refs.reviewForm.validate(valid => {
        if (!valid) {
          return
        }
        reviewSignup(this.reviewTarget.id, this.reviewForm).then(() => {
          this.$modal.msgSuccess('报名状态已更新')
          this.reviewOpen = false
          this.getList()
        })
      })
    },
    handleDetail(row) {
      getSignup(row.id).then(response => {
        this.detail = response.data
        this.detailOpen = true
      })
    },
    handleExport() {
      this.download('manager/voluntary/signups/export', {
        ...this.queryParams
      }, `报名记录_${new Date().getTime()}.xlsx`)
    },
    canReview(row) {
      return row && (row.status === 0 || row.status === 3)
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
.signup-activity-cell {
  display: flex;
  flex-direction: column;
  gap: 3px;
  min-width: 0;
}

.signup-activity-cell span {
  color: #17211f;
  font-weight: 600;
}

.signup-activity-cell small {
  color: #7b8884;
  font-size: 12px;
}
</style>
