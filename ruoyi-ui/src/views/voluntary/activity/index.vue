<template>
  <div class="voluntary-admin-page activity-manage-page">
    <el-card class="voluntary-admin-card" shadow="never">
      <div slot="header" class="voluntary-admin-title">
        <div>
          <h2>活动管理</h2>
          <p>创建、编辑、发布、下架、结束和取消志愿活动。</p>
        </div>
        <span class="meta">活动发布</span>
      </div>

      <el-form
        ref="queryForm"
        class="voluntary-admin-filter"
        :model="queryParams"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="关键字" prop="keyword">
          <el-input
            v-model="queryParams.keyword"
            placeholder="标题或地点"
            clearable
            style="width: 190px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="activityType">
          <el-select v-model="queryParams.activityType" placeholder="活动类型" clearable style="width: 150px">
            <el-option
              v-for="dict in dict.type.vol_activity_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="活动状态" clearable style="width: 150px">
            <el-option
              v-for="dict in dict.type.vol_activity_status"
              :key="dict.value"
              :label="dict.label"
              :value="Number(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="报名中" prop="signupOpen">
          <el-select v-model="queryParams.signupOpen" placeholder="不限" clearable style="width: 120px">
            <el-option label="是" :value="true" />
            <el-option label="否" :value="false" />
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
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['manager:voluntary:activity:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single || !canEdit(selectedRow)"
            @click="handleUpdate(selectedRow)"
            v-hasPermi="['manager:voluntary:activity:edit']"
          >编辑</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table
        v-loading="loading"
        class="voluntary-admin-table"
        :data="activityList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="48" align="center" />
        <el-table-column label="活动标题" prop="title" min-width="180" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="activity-title-cell">
              <span>{{ scope.row.title || '-' }}</span>
              <small>{{ scope.row.serviceLocation || '-' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="类型" prop="activityType" width="100" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_activity_type" :value="scope.row.activityType" />
          </template>
        </el-table-column>
        <el-table-column label="活动时间" min-width="180">
          <template slot-scope="scope">
            <span>{{ formatRange(scope.row.startTime, scope.row.endTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="报名时间" min-width="180">
          <template slot-scope="scope">
            <span>{{ formatRange(scope.row.signupStartTime, scope.row.signupEndTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="名额" width="90" align="center">
          <template slot-scope="scope">
            {{ scope.row.approvedCount || 0 }} / {{ scope.row.recruitCount || 0 }}
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="90" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_activity_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="220" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
              v-hasPermi="['manager:voluntary:activity:query']"
            >详情</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              :disabled="!canEdit(scope.row)"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['manager:voluntary:activity:edit']"
            >编辑</el-button>
            <el-dropdown size="mini" @command="command => handleStatusCommand(command, scope.row)">
              <el-button size="mini" type="text" icon="el-icon-d-arrow-right">状态</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="publish" icon="el-icon-s-promotion" v-if="canPublish(scope.row)">发布</el-dropdown-item>
                <el-dropdown-item command="offline" icon="el-icon-bottom" v-if="scope.row.status === 1">下架</el-dropdown-item>
                <el-dropdown-item command="end" icon="el-icon-finished" v-if="scope.row.status === 1">结束</el-dropdown-item>
                <el-dropdown-item command="cancel" icon="el-icon-close" v-if="canCancel(scope.row)">取消</el-dropdown-item>
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
      :title="form.id ? '编辑活动' : '新增活动'"
      :visible.sync="formOpen"
      width="880px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="16">
          <el-col :span="16">
            <el-form-item label="活动标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入活动标题" maxlength="160" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="活动类型" prop="activityType">
              <el-select v-model="form.activityType" placeholder="请选择类型" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.vol_activity_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="服务地点" prop="serviceLocation">
              <el-input v-model="form.serviceLocation" placeholder="请输入服务地点" maxlength="255" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="招募人数" prop="recruitCount">
              <el-input-number v-model="form.recruitCount" :min="1" :max="9999" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="计入分钟" prop="maxServiceMinutes">
              <el-input-number v-model="form.maxServiceMinutes" :min="1" :max="1440" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="活动开始" prop="startTime">
              <el-date-picker v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动结束" prop="endTime">
              <el-date-picker v-model="form.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择结束时间" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="报名开始" prop="signupStartTime">
              <el-date-picker v-model="form.signupStartTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择报名开始时间" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报名截止" prop="signupEndTime">
              <el-date-picker v-model="form.signupEndTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择报名截止时间" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="负责人" prop="managerName">
              <el-input v-model="form.managerName" placeholder="请输入负责人" maxlength="64" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="managerPhone">
              <el-input v-model="form.managerPhone" placeholder="请输入负责人联系电话" maxlength="32" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="服务对象" prop="serviceTarget">
          <el-input v-model="form.serviceTarget" placeholder="请输入服务对象" maxlength="255" />
        </el-form-item>
        <el-form-item label="报名要求" prop="requirements">
          <el-input v-model="form.requirements" type="textarea" :rows="3" placeholder="请输入报名要求" maxlength="1000" show-word-limit />
        </el-form-item>
        <el-form-item label="活动内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入活动内容" maxlength="2000" show-word-limit />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注" maxlength="500" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="formOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="活动状态变更"
      :visible.sync="statusOpen"
      width="520px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-form ref="statusForm" :model="statusForm" label-width="96px">
        <el-form-item label="活动">
          <span>{{ statusTarget.title || '-' }}</span>
        </el-form-item>
        <el-form-item label="目标状态">
          <span>{{ statusLabel }}</span>
        </el-form-item>
        <el-form-item label="处理说明">
          <el-input v-model="statusForm.reason" type="textarea" :rows="4" placeholder="可填写发布、下架、结束或取消原因" maxlength="300" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitStatus">确 定</el-button>
        <el-button @click="statusOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="活动详情"
      :visible.sync="detailOpen"
      width="860px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-descriptions v-if="detail" :column="2" border size="small">
        <el-descriptions-item label="活动标题">{{ detail.title || '-' }}</el-descriptions-item>
        <el-descriptions-item label="活动类型">{{ selectDictLabel(dict.type.vol_activity_type, detail.activityType) }}</el-descriptions-item>
        <el-descriptions-item label="服务地点">{{ detail.serviceLocation || '-' }}</el-descriptions-item>
        <el-descriptions-item label="活动状态">{{ selectDictLabel(dict.type.vol_activity_status, detail.status) }}</el-descriptions-item>
        <el-descriptions-item label="活动时间">{{ formatRange(detail.startTime, detail.endTime) }}</el-descriptions-item>
        <el-descriptions-item label="报名时间">{{ formatRange(detail.signupStartTime, detail.signupEndTime) }}</el-descriptions-item>
        <el-descriptions-item label="招募人数">{{ detail.recruitCount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="已通过">{{ detail.approvedCount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="负责人">{{ detail.managerName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detail.managerPhone || '-' }}</el-descriptions-item>
        <el-descriptions-item label="服务对象" :span="2">{{ detail.serviceTarget || '-' }}</el-descriptions-item>
        <el-descriptions-item label="报名要求" :span="2">{{ detail.requirements || '-' }}</el-descriptions-item>
        <el-descriptions-item label="活动内容" :span="2">{{ detail.content || '-' }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addActivity, changeActivityStatus, getActivity, listActivity, updateActivity } from '@/api/voluntary/activity'

export default {
  name: 'VoluntaryActivity',
  dicts: ['vol_activity_type', 'vol_activity_status'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      activityList: [],
      selectedRow: null,
      single: true,
      formOpen: false,
      statusOpen: false,
      detailOpen: false,
      detail: null,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: undefined,
        activityType: undefined,
        status: undefined,
        signupOpen: undefined
      },
      form: {},
      statusTarget: {},
      statusForm: {
        status: undefined,
        reason: ''
      },
      rules: {
        title: [{ required: true, message: '活动标题不能为空', trigger: 'blur' }],
        activityType: [{ required: true, message: '活动类型不能为空', trigger: 'change' }],
        serviceLocation: [{ required: true, message: '服务地点不能为空', trigger: 'blur' }],
        startTime: [{ required: true, message: '活动开始时间不能为空', trigger: 'change' }],
        endTime: [{ required: true, message: '活动结束时间不能为空', trigger: 'change' }],
        signupStartTime: [{ required: true, message: '报名开始时间不能为空', trigger: 'change' }],
        signupEndTime: [{ required: true, message: '报名截止时间不能为空', trigger: 'change' }],
        recruitCount: [{ required: true, message: '招募人数不能为空', trigger: 'change' }]
      }
    }
  },
  computed: {
    statusLabel() {
      return this.selectDictLabel(this.dict.type.vol_activity_status, this.statusForm.status)
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listActivity(this.queryParams).then(response => {
        this.activityList = response.rows || []
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
    resetFormData() {
      this.form = {
        id: undefined,
        title: undefined,
        activityType: undefined,
        coverUrl: undefined,
        serviceLocation: undefined,
        startTime: undefined,
        endTime: undefined,
        signupStartTime: undefined,
        signupEndTime: undefined,
        recruitCount: 20,
        serviceTarget: undefined,
        content: undefined,
        requirements: undefined,
        managerName: undefined,
        managerPhone: undefined,
        maxServiceMinutes: 120,
        remark: undefined
      }
      this.resetForm('form')
    },
    handleAdd() {
      this.resetFormData()
      this.formOpen = true
    },
    handleUpdate(row) {
      if (!row || !this.canEdit(row)) {
        return
      }
      this.resetFormData()
      getActivity(row.id).then(response => {
        this.form = response.data || {}
        this.formOpen = true
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return
        }
        const request = this.form.id
          ? updateActivity(this.form.id, this.form)
          : addActivity(this.form)
        request.then(() => {
          this.$modal.msgSuccess(this.form.id ? '活动已保存' : '活动已创建')
          this.formOpen = false
          this.getList()
        })
      })
    },
    handleDetail(row) {
      getActivity(row.id).then(response => {
        this.detail = response.data
        this.detailOpen = true
      })
    },
    handleStatusCommand(command, row) {
      const map = {
        publish: 1,
        end: 2,
        offline: 3,
        cancel: 4
      }
      this.statusTarget = row
      this.statusForm = {
        status: map[command],
        reason: ''
      }
      this.statusOpen = true
    },
    submitStatus() {
      changeActivityStatus(this.statusTarget.id, this.statusForm).then(() => {
        this.$modal.msgSuccess('活动状态已更新')
        this.statusOpen = false
        this.getList()
      })
    },
    canEdit(row) {
      return row && (row.status === 0 || row.status === 3)
    },
    canPublish(row) {
      return row && (row.status === 0 || row.status === 3)
    },
    canCancel(row) {
      return row && (row.status === 0 || row.status === 1 || row.status === 3)
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
.activity-title-cell {
  display: flex;
  flex-direction: column;
  gap: 3px;
  min-width: 0;
}

.activity-title-cell span {
  color: #17211f;
  font-weight: 600;
}

.activity-title-cell small {
  color: #7b8884;
  font-size: 12px;
}
</style>
