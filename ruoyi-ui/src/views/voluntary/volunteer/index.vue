<template>
  <div class="voluntary-admin-page volunteer-review-page">
    <el-card class="voluntary-admin-card" shadow="never">
      <div slot="header" class="voluntary-admin-title">
        <div>
          <h2>志愿者审核</h2>
          <p>维护志愿者实名档案，处理待审核、驳回、禁用和启用状态。</p>
        </div>
        <span class="meta">P2 志愿者档案</span>
      </div>

      <el-form
        ref="queryForm"
        class="voluntary-admin-filter"
        :model="queryParams"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="账号" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入账号"
            clearable
            style="width: 180px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input
            v-model="queryParams.realName"
            placeholder="请输入姓名"
            clearable
            style="width: 180px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input
            v-model="queryParams.phone"
            placeholder="请输入手机号"
            clearable
            style="width: 180px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="组织" prop="organization">
          <el-input
            v-model="queryParams.organization"
            placeholder="请输入组织"
            clearable
            style="width: 200px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="auditStatus">
          <el-select
            v-model="queryParams.auditStatus"
            placeholder="审核状态"
            clearable
            style="width: 150px"
          >
            <el-option
              v-for="dict in dict.type.vol_audit_status"
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
            :disabled="single"
            @click="handleAudit(selectedRow, 1)"
            v-hasPermi="['manager:voluntary:volunteer:audit']"
          >通过</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-close"
            size="mini"
            :disabled="single"
            @click="handleAudit(selectedRow, 2)"
            v-hasPermi="['manager:voluntary:volunteer:audit']"
          >驳回</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            plain
            icon="el-icon-view"
            size="mini"
            :disabled="single"
            @click="handleRecords(selectedRow)"
            v-hasPermi="['manager:voluntary:volunteer:query']"
          >审核记录</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['manager:voluntary:volunteer:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table
        v-loading="loading"
        class="voluntary-admin-table"
        :data="volunteerList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="48" align="center" />
        <el-table-column label="账号" prop="userName" min-width="130" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div class="account-cell">
              <span>{{ scope.row.userName || '-' }}</span>
              <small>{{ scope.row.nickName || '未设置昵称' }}</small>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="姓名" prop="realName" min-width="110" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.realName || '待完善' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="性别" prop="gender" width="80" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender" />
          </template>
        </el-table-column>
        <el-table-column label="手机号" prop="phone" min-width="130" :show-overflow-tooltip="true" />
        <el-table-column label="组织" prop="organization" min-width="150" :show-overflow-tooltip="true" />
        <el-table-column label="审核状态" prop="auditStatus" width="100" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_audit_status" :value="scope.row.auditStatus" />
          </template>
        </el-table-column>
        <el-table-column label="账号状态" prop="userStatus" width="100" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.userStatus" />
          </template>
        </el-table-column>
        <el-table-column label="服务时长" width="110" align="center">
          <template slot-scope="scope">
            {{ formatMinutes(scope.row.totalServiceMinutes) }}
          </template>
        </el-table-column>
        <el-table-column label="服务次数" prop="serviceCount" width="90" align="center" />
        <el-table-column label="创建时间" prop="createTime" width="160" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="250" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
              v-hasPermi="['manager:voluntary:volunteer:query']"
            >详情</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['manager:voluntary:volunteer:edit']"
            >编辑</el-button>
            <el-dropdown size="mini" @command="command => handleCommand(command, scope.row)">
              <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  command="approve"
                  icon="el-icon-check"
                  v-hasPermi="['manager:voluntary:volunteer:audit']"
                >审核通过</el-dropdown-item>
                <el-dropdown-item
                  command="reject"
                  icon="el-icon-close"
                  v-hasPermi="['manager:voluntary:volunteer:audit']"
                >驳回</el-dropdown-item>
                <el-dropdown-item
                  command="disable"
                  icon="el-icon-lock"
                  v-if="scope.row.auditStatus !== 3"
                  v-hasPermi="['manager:voluntary:volunteer:edit']"
                >禁用</el-dropdown-item>
                <el-dropdown-item
                  command="enable"
                  icon="el-icon-unlock"
                  v-if="scope.row.auditStatus === 3"
                  v-hasPermi="['manager:voluntary:volunteer:edit']"
                >启用</el-dropdown-item>
                <el-dropdown-item
                  command="records"
                  icon="el-icon-document"
                  v-hasPermi="['manager:voluntary:volunteer:query']"
                >审核记录</el-dropdown-item>
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
      title="志愿者档案"
      :visible.sync="editOpen"
      width="760px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="108px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="真实姓名" prop="realName">
              <el-input v-model="form.realName" placeholder="请输入真实姓名" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
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
            <el-form-item label="证件号" prop="idCard">
              <el-input v-model="form.idCard" placeholder="请输入证件号" maxlength="32" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="20" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="所属组织" prop="organization">
              <el-input v-model="form.organization" placeholder="请输入所属组织" maxlength="80" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学院班级" prop="majorOrClass">
              <el-input v-model="form.majorOrClass" placeholder="请输入学院班级或社区分组" maxlength="80" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="紧急联系人" prop="emergencyContact">
              <el-input v-model="form.emergencyContact" placeholder="请输入紧急联系人" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="紧急电话" prop="emergencyPhone">
              <el-input v-model="form.emergencyPhone" placeholder="请输入紧急联系电话" maxlength="20" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="服务特长" prop="specialty">
          <el-input v-model="form.specialty" type="textarea" :rows="3" placeholder="请输入服务特长" maxlength="500" show-word-limit />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注" maxlength="500" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="editOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="auditTitle"
      :visible.sync="auditOpen"
      width="520px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-form ref="auditForm" :model="auditForm" :rules="auditRules" label-width="96px">
        <el-form-item label="志愿者">
          <span>{{ auditTarget.userName || '-' }} / {{ auditTarget.realName || '待完善' }}</span>
        </el-form-item>
        <el-form-item label="当前状态">
          <dict-tag :options="dict.type.vol_audit_status" :value="auditTarget.auditStatus" />
        </el-form-item>
        <el-form-item label="处理意见" prop="auditReason">
          <el-input
            v-model="auditForm.auditReason"
            type="textarea"
            :rows="4"
            :placeholder="auditPlaceholder"
            maxlength="300"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAudit">确 定</el-button>
        <el-button @click="auditOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="志愿者详情"
      :visible.sync="detailOpen"
      width="820px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-descriptions v-if="detail" :column="2" border size="small" class="volunteer-detail">
        <el-descriptions-item label="账号">{{ detail.userName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="昵称">{{ detail.nickName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="真实姓名">{{ detail.realName || '待完善' }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ selectDictLabel(dict.type.sys_user_sex, detail.gender) }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ detail.phone || '-' }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ detail.email || '-' }}</el-descriptions-item>
        <el-descriptions-item label="所属组织">{{ detail.organization || '-' }}</el-descriptions-item>
        <el-descriptions-item label="学院班级">{{ detail.majorOrClass || '-' }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">{{ selectDictLabel(dict.type.vol_audit_status, detail.auditStatus) }}</el-descriptions-item>
        <el-descriptions-item label="账号状态">{{ selectDictLabel(dict.type.sys_normal_disable, detail.userStatus) }}</el-descriptions-item>
        <el-descriptions-item label="累计时长">{{ formatMinutes(detail.totalServiceMinutes) }}</el-descriptions-item>
        <el-descriptions-item label="服务次数">{{ detail.serviceCount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="审核人">{{ detail.auditorName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="审核时间">{{ parseTime(detail.auditTime) || '-' }}</el-descriptions-item>
        <el-descriptions-item label="审核意见" :span="2">{{ detail.auditReason || '-' }}</el-descriptions-item>
        <el-descriptions-item label="服务特长" :span="2">{{ detail.specialty || '-' }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="审核记录"
      :visible.sync="recordOpen"
      width="820px"
      append-to-body
      custom-class="voluntary-admin-dialog"
    >
      <el-table v-loading="recordLoading" :data="recordList" class="voluntary-admin-table">
        <el-table-column label="操作人" prop="auditorName" width="120" />
        <el-table-column label="原状态" prop="beforeStatus" width="100" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_audit_status" :value="scope.row.beforeStatus" />
          </template>
        </el-table-column>
        <el-table-column label="新状态" prop="auditStatus" width="100" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.vol_audit_status" :value="scope.row.auditStatus" />
          </template>
        </el-table-column>
        <el-table-column label="意见" prop="auditReason" min-width="220" :show-overflow-tooltip="true" />
        <el-table-column label="时间" prop="createTime" width="160" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="recordOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  auditVolunteer,
  changeVolunteerStatus,
  getVolunteer,
  listVolunteer,
  listVolunteerAuditRecords,
  updateVolunteer
} from '@/api/voluntary/volunteer'

export default {
  name: 'VoluntaryVolunteer',
  dicts: ['vol_audit_status', 'sys_user_sex', 'sys_normal_disable'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      volunteerList: [],
      ids: [],
      selectedRow: null,
      single: true,
      editOpen: false,
      auditOpen: false,
      detailOpen: false,
      recordOpen: false,
      recordLoading: false,
      recordList: [],
      detail: null,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        realName: undefined,
        phone: undefined,
        organization: undefined,
        auditStatus: undefined
      },
      form: {},
      auditTarget: {},
      auditForm: {
        auditStatus: undefined,
        auditReason: ''
      },
      rules: {
        realName: [
          { max: 30, message: '真实姓名不能超过30个字符', trigger: 'blur' }
        ],
        phone: [
          { max: 20, message: '联系电话不能超过20个字符', trigger: 'blur' }
        ],
        idCard: [
          { max: 32, message: '证件号不能超过32个字符', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    auditTitle() {
      if (this.auditForm.auditStatus === 1) {
        return '审核通过'
      }
      if (this.auditForm.auditStatus === 2) {
        return '审核驳回'
      }
      if (this.auditForm.auditStatus === 3) {
        return '禁用志愿者'
      }
      return '启用志愿者'
    },
    auditPlaceholder() {
      if (this.auditForm.auditStatus === 2) {
        return '请填写驳回原因'
      }
      if (this.auditForm.auditStatus === 3) {
        return '请填写禁用原因'
      }
      return '可填写处理说明，不填则使用系统默认意见'
    },
    auditRules() {
      const requireReason = this.auditForm.auditStatus === 2
      return {
        auditReason: requireReason
          ? [{ required: true, message: '驳回时必须填写审核意见', trigger: 'blur' }]
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
      listVolunteer(this.queryParams).then(response => {
        this.volunteerList = response.rows || []
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.selectedRow = selection.length === 1 ? selection[0] : null
    },
    resetFormData() {
      this.form = {
        id: undefined,
        realName: undefined,
        gender: '2',
        idCard: undefined,
        phone: undefined,
        organization: undefined,
        majorOrClass: undefined,
        specialty: undefined,
        emergencyContact: undefined,
        emergencyPhone: undefined,
        remark: undefined
      }
      this.resetForm('form')
    },
    handleUpdate(row) {
      const id = row.id
      this.resetFormData()
      getVolunteer(id).then(response => {
        this.form = response.data || {}
        this.editOpen = true
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return
        }
        updateVolunteer(this.form.id, this.form).then(() => {
          this.$modal.msgSuccess('档案已保存')
          this.editOpen = false
          this.getList()
        })
      })
    },
    handleCommand(command, row) {
      if (command === 'approve') {
        this.handleAudit(row, 1)
      } else if (command === 'reject') {
        this.handleAudit(row, 2)
      } else if (command === 'disable') {
        this.handleAudit(row, 3)
      } else if (command === 'enable') {
        this.handleAudit(row, 1, true)
      } else if (command === 'records') {
        this.handleRecords(row)
      }
    },
    handleAudit(row, auditStatus, statusChange) {
      if (!row) {
        return
      }
      this.auditTarget = row
      this.auditForm = {
        auditStatus: auditStatus,
        auditReason: ''
      }
      this.auditForm.statusChange = Boolean(statusChange || auditStatus === 3)
      this.auditOpen = true
      this.$nextTick(() => this.resetForm('auditForm'))
    },
    submitAudit() {
      this.$refs.auditForm.validate(valid => {
        if (!valid) {
          return
        }
        const data = {
          auditStatus: this.auditForm.auditStatus,
          auditReason: this.auditForm.auditReason
        }
        const request = this.auditForm.statusChange
          ? changeVolunteerStatus(this.auditTarget.id, data)
          : auditVolunteer(this.auditTarget.id, data)
        request.then(() => {
          this.$modal.msgSuccess('状态已更新')
          this.auditOpen = false
          this.getList()
        })
      })
    },
    handleDetail(row) {
      getVolunteer(row.id).then(response => {
        this.detail = response.data
        this.detailOpen = true
      })
    },
    handleRecords(row) {
      if (!row) {
        return
      }
      this.recordOpen = true
      this.recordLoading = true
      listVolunteerAuditRecords(row.id).then(response => {
        this.recordList = response.data || []
        this.recordLoading = false
      }).catch(() => {
        this.recordLoading = false
      })
    },
    handleExport() {
      this.download('manager/voluntary/volunteers/export', {
        ...this.queryParams
      }, `志愿者档案_${new Date().getTime()}.xlsx`)
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
.volunteer-review-page .account-cell {
  display: flex;
  flex-direction: column;
  gap: 3px;
  min-width: 0;
}

.volunteer-review-page .account-cell span {
  color: #17211f;
  font-weight: 600;
}

.volunteer-review-page .account-cell small {
  color: #7b8884;
  font-size: 12px;
}

.volunteer-review-page .volunteer-detail {
  margin-top: 4px;
}
</style>
