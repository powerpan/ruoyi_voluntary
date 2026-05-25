<template>
  <div class="page" v-loading="loading">
    <section class="profile-hero">
      <div class="avatar-fallback">{{ avatarText }}</div>
      <div class="profile-copy">
        <div class="profile-title-row">
          <h1>{{ displayName }}</h1>
          <el-tag :type="auditTagType" effect="dark">{{ auditStatusLabel }}</el-tag>
        </div>
        <p>{{ auditStatusText }}</p>
        <div class="profile-meta">
          <span><i class="el-icon-user"></i>{{ user.userName || '未获取账号' }}</span>
          <span><i class="el-icon-phone"></i>{{ form.phone || user.phonenumber || '未填写手机号' }}</span>
          <span><i class="el-icon-office-building"></i>{{ form.organization || '未填写组织' }}</span>
        </div>
      </div>
      <div class="hero-actions">
        <el-button type="success" icon="el-icon-key" @click="$router.push('/token-checkin')">令牌签到</el-button>
        <el-button type="primary" icon="el-icon-document" @click="$router.push('/signups')">我的报名</el-button>
        <el-button icon="el-icon-time" @click="$router.push('/service-records')">服务记录</el-button>
      </div>
    </section>

    <el-row :gutter="18" class="summary-grid">
      <el-col :xs="24" :sm="8">
        <div class="summary-item">
          <span class="summary-label">累计服务时长</span>
          <strong>{{ serviceHours }}</strong>
          <small>小时</small>
        </div>
      </el-col>
      <el-col :xs="24" :sm="8">
        <div class="summary-item">
          <span class="summary-label">有效服务次数</span>
          <strong>{{ serviceCount }}</strong>
          <small>次</small>
        </div>
      </el-col>
      <el-col :xs="24" :sm="8">
        <div class="summary-item">
          <span class="summary-label">档案审核</span>
          <strong>{{ auditStatusLabel }}</strong>
          <small>{{ approved ? '可正常报名参与' : '等待管理员处理' }}</small>
        </div>
      </el-col>
    </el-row>

    <el-alert
      class="status-alert"
      :title="auditStatusText"
      :description="profile.auditReason ? '审核意见：' + profile.auditReason : ''"
      :type="auditAlertType"
      show-icon
      :closable="false"
    />

    <el-card shadow="never" class="panel">
      <div slot="header" class="panel-head">
        <span><i class="el-icon-user"></i>志愿者资料维护</span>
        <el-button size="mini" icon="el-icon-refresh" :loading="loading" @click="loadProfile">刷新</el-button>
      </div>

      <el-form ref="profileForm" :model="form" :rules="rules" label-width="112px" class="profile-form">
        <el-row :gutter="18">
          <el-col :xs="24" :sm="12">
            <el-form-item label="真实姓名" prop="realName">
              <el-input v-model="form.realName" maxlength="30" show-word-limit :disabled="isProfileDisabled" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="form.gender" :disabled="isProfileDisabled">
                <el-radio label="0">男</el-radio>
                <el-radio label="1">女</el-radio>
                <el-radio label="2">未知</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="证件号码" prop="idCard">
              <el-input v-model="form.idCard" maxlength="18" show-word-limit :disabled="isProfileDisabled" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" maxlength="11" :disabled="isProfileDisabled" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="所属组织" prop="organization">
              <el-input v-model="form.organization" maxlength="60" show-word-limit :disabled="isProfileDisabled" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="学院班级" prop="majorOrClass">
              <el-input v-model="form.majorOrClass" maxlength="60" show-word-limit :disabled="isProfileDisabled" />
            </el-form-item>
          </el-col>
          <el-col :xs="24">
            <el-form-item label="服务特长" prop="specialty">
              <el-input
                v-model="form.specialty"
                type="textarea"
                :rows="3"
                maxlength="200"
                show-word-limit
                placeholder="例如：秩序维护、活动摄影、应急救护、公益宣传"
                :disabled="isProfileDisabled"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="紧急联系人" prop="emergencyContact">
              <el-input v-model="form.emergencyContact" maxlength="30" show-word-limit :disabled="isProfileDisabled" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="紧急电话" prop="emergencyPhone">
              <el-input v-model="form.emergencyPhone" maxlength="11" :disabled="isProfileDisabled" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item class="form-actions">
          <el-button
            type="primary"
            icon="el-icon-check"
            :loading="saving"
            :disabled="isProfileDisabled"
            @click="submit"
          >
            {{ submitText }}
          </el-button>
          <el-button icon="el-icon-refresh-left" :disabled="saving" @click="resetForm">还原</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getVolunteerProfile, updateVolunteerProfile } from '@/api/voluntary/profile'

const STATUS_MAP = {
  0: { label: '待审核', tag: 'warning', alert: 'warning', text: '资料已提交，等待管理员审核。审核通过后可参与需要审核通过的活动。' },
  1: { label: '已通过', tag: 'success', alert: 'success', text: '档案已通过审核。修改真实姓名、证件号、手机号等关键信息后将重新进入待审核。' },
  2: { label: '已驳回', tag: 'danger', alert: 'error', text: '档案审核未通过，请根据审核意见修改后重新提交。' },
  3: { label: '已禁用', tag: 'info', alert: 'error', text: '档案已被禁用，请联系管理员处理。' }
}

export default {
  name: 'Me',
  data() {
    const validatePhone = (rule, value, callback) => {
      if (!value) {
        callback()
        return
      }
      if (!/^1[3-9]\d{9}$/.test(value)) {
        callback(new Error('请输入正确的手机号'))
        return
      }
      callback()
    }
    return {
      loading: false,
      saving: false,
      user: {},
      profile: {},
      approved: false,
      form: this.emptyForm(),
      rules: {
        realName: [
          { required: true, message: '真实姓名不能为空', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        idCard: [
          { required: true, message: '证件号码不能为空', trigger: 'blur' },
          { pattern: /(^\d{15}$)|(^\d{17}[\dXx]$)/, message: '请输入正确的身份证号码', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' }
        ],
        emergencyPhone: [
          { validator: validatePhone, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    auditStatusValue() {
      const value = Number(this.profile.auditStatus)
      return Number.isFinite(value) ? value : 0
    },
    statusConfig() {
      return STATUS_MAP[this.auditStatusValue] || STATUS_MAP[0]
    },
    auditStatusLabel() {
      return this.statusConfig.label
    },
    auditStatusText() {
      return this.statusConfig.text
    },
    auditTagType() {
      return this.statusConfig.tag
    },
    auditAlertType() {
      return this.statusConfig.alert
    },
    displayName() {
      return this.form.realName || this.user.nickName || this.user.userName || '志愿者'
    },
    avatarText() {
      return this.displayName.substring(0, 1)
    },
    serviceHours() {
      const minutes = Number(this.profile.totalServiceMinutes || 0)
      return (minutes / 60).toFixed(1)
    },
    serviceCount() {
      return Number(this.profile.serviceCount || 0)
    },
    isProfileDisabled() {
      return this.auditStatusValue === 3 || this.saving
    },
    submitText() {
      if (this.auditStatusValue === 2) {
        return '重新提交审核'
      }
      return '保存资料'
    }
  },
  created() {
    this.loadProfile()
  },
  methods: {
    emptyForm() {
      return {
        realName: '',
        gender: '2',
        idCard: '',
        phone: '',
        organization: '',
        majorOrClass: '',
        specialty: '',
        emergencyContact: '',
        emergencyPhone: ''
      }
    },
    loadProfile() {
      this.loading = true
      return getVolunteerProfile().then((res) => {
        this.user = res.user || {}
        this.profile = res.profile || {}
        this.approved = !!res.approved
        this.fillForm(this.profile)
      }).finally(() => {
        this.loading = false
      })
    },
    fillForm(profile) {
      this.form = Object.assign(this.emptyForm(), {
        realName: profile.realName || '',
        gender: profile.gender || '2',
        idCard: profile.idCard || '',
        phone: profile.phone || '',
        organization: profile.organization || '',
        majorOrClass: profile.majorOrClass || '',
        specialty: profile.specialty || '',
        emergencyContact: profile.emergencyContact || '',
        emergencyPhone: profile.emergencyPhone || ''
      })
      this.$nextTick(() => {
        if (this.$refs.profileForm) {
          this.$refs.profileForm.clearValidate()
        }
      })
    },
    resetForm() {
      this.fillForm(this.profile || {})
    },
    submit() {
      if (this.isProfileDisabled) {
        this.$message.warning('当前档案不可修改')
        return
      }
      this.$refs.profileForm.validate((valid) => {
        if (!valid) {
          return
        }
        this.saving = true
        updateVolunteerProfile(this.form).then((res) => {
          this.profile = res.profile || {}
          this.approved = !!res.approved
          this.fillForm(this.profile)
          this.$message.success('资料已保存')
        }).finally(() => {
          this.saving = false
        })
      })
    }
  }
}
</script>

<style scoped>
.page {
  max-width: 1120px;
  margin: 0 auto;
  padding: 32px 20px 52px;
}

.profile-hero,
.panel,
.summary-item {
  border: 1px solid #dfe8e4;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 10px 26px rgba(23, 33, 31, .05);
}

.profile-hero {
  display: grid;
  grid-template-columns: auto minmax(0, 1fr) auto;
  gap: 22px;
  align-items: center;
  padding: 28px;
}

.avatar-fallback {
  width: 92px;
  height: 92px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background: linear-gradient(135deg, #2f8f83, #5f9ea0);
  color: #fff;
  font-size: 36px;
  font-weight: 700;
}

.profile-copy {
  min-width: 0;
}

.profile-title-row {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 12px;
}

.profile-title-row h1 {
  margin: 0;
  font-size: 28px;
  letter-spacing: 0;
}

.profile-copy p {
  margin: 10px 0 0;
  color: #63716d;
  line-height: 1.7;
}

.profile-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 10px 18px;
  margin-top: 14px;
  color: #4b5c58;
  font-size: 14px;
}

.profile-meta span {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  min-width: 0;
}

.hero-actions,
.panel-head {
  display: flex;
  align-items: center;
  gap: 10px;
}

.hero-actions {
  flex-wrap: wrap;
  justify-content: flex-end;
}

.summary-grid {
  margin-top: 18px;
}

.summary-item {
  min-height: 112px;
  padding: 18px 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.summary-label,
.summary-item small {
  color: #63716d;
  font-size: 13px;
}

.summary-item strong {
  margin-top: 8px;
  color: #17211f;
  font-size: 28px;
  line-height: 1.2;
  letter-spacing: 0;
}

.summary-item small {
  margin-top: 4px;
}

.status-alert {
  margin-top: 18px;
}

.panel {
  margin-top: 18px;
}

.panel-head {
  justify-content: space-between;
}

.panel-head span {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-weight: 700;
}

.profile-form {
  max-width: 980px;
}

.form-actions {
  margin-top: 6px;
}

@media (max-width: 820px) {
  .profile-hero {
    grid-template-columns: 1fr;
  }

  .hero-actions {
    justify-content: flex-start;
  }

  .summary-grid .el-col + .el-col {
    margin-top: 12px;
  }
}
</style>
