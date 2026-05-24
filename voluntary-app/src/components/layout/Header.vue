<template>
  <header class="header">
    <div class="brand" @click="$router.push('/')">
      <span class="brand-mark">志</span>
      <span class="brand-copy">
        <strong>志愿活动管理系统</strong>
        <small>Voluntary Service</small>
      </span>
    </div>
    <nav class="nav">
      <router-link to="/"><i class="el-icon-s-home"></i>首页</router-link>
      <router-link to="/activities"><i class="el-icon-guide"></i>活动浏览</router-link>
      <router-link v-if="token" to="/signups"><i class="el-icon-s-management"></i>我的报名</router-link>
      <router-link v-if="token" to="/service-records"><i class="el-icon-time"></i>服务记录</router-link>
      <router-link to="/me"><i class="el-icon-user"></i>我的</router-link>
    </nav>
    <div class="actions">
      <el-button v-if="!token" size="small" type="primary" icon="el-icon-user" @click="$router.push('/login')">登录</el-button>
      <template v-else>
        <el-badge :value="unreadCount" :hidden="unreadCount <= 0" :max="99" class="notice-badge">
          <el-button class="notice-button" size="small" icon="el-icon-bell" circle @click="$router.push('/notifications')" />
        </el-badge>
        <el-dropdown>
          <span class="user-entry">{{ name || '已登录' }}<i class="el-icon-arrow-down el-icon--right"></i></span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="$router.push('/notifications')">通知中心</el-dropdown-item>
            <el-dropdown-item @click.native="$router.push('/me')">个人主页</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>
    </div>
  </header>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Header',
  data() {
    return {
      unreadCount: 0,
      noticeTimer: null
    }
  },
  computed: {
    ...mapGetters(['token', 'name'])
  },
  watch: {
    token: {
      immediate: true,
      handler() {
        this.unreadCount = 0
      }
    },
    '$route.path'() {
      this.unreadCount = 0
    }
  },
  mounted() {
    window.addEventListener('voluntary-notification-updated', this.resetUnreadCount)
  },
  beforeDestroy() {
    window.removeEventListener('voluntary-notification-updated', this.resetUnreadCount)
    if (this.noticeTimer) {
      window.clearInterval(this.noticeTimer)
    }
  },
  methods: {
    resetUnreadCount() {
      this.unreadCount = 0
    },
    logout() {
      this.$store.dispatch('LogOut').finally(() => {
        this.unreadCount = 0
        this.$router.push('/')
      })
    }
  }
}
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 20;
  min-height: 72px;
  padding: 0 34px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
  background: rgba(255, 255, 255, .92);
  border-bottom: 1px solid rgba(226, 232, 229, .86);
  backdrop-filter: blur(14px);
  box-shadow: 0 10px 26px rgba(23, 33, 31, .05);
}
.brand {
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 230px;
  cursor: pointer;
}
.brand-mark {
  width: 42px;
  height: 42px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  color: #fff;
  background: linear-gradient(135deg, var(--voluntary-primary), var(--voluntary-info));
  box-shadow: 0 9px 20px rgba(35, 130, 118, .22);
  font-weight: 800;
}
.brand-copy {
  display: grid;
  gap: 2px;
  line-height: 1.2;
}
.brand-copy strong {
  font-size: 17px;
  color: var(--voluntary-text);
}
.brand-copy small {
  color: var(--voluntary-text-muted);
  font-size: 12px;
}
.nav {
  display: flex;
  gap: 6px;
  padding: 6px;
  border: 1px solid #edf2ef;
  border-radius: 8px;
  background: #f8fbfa;
  scrollbar-width: none;
}
.nav::-webkit-scrollbar {
  display: none;
}
.nav a {
  position: relative;
  display: inline-flex;
  align-items: center;
  gap: 6px;
  height: 36px;
  padding: 0 12px;
  border-radius: 8px;
  color: #344440;
  text-decoration: none;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  transition: background .18s ease, color .18s ease;
}
.nav a.router-link-exact-active {
  color: var(--voluntary-primary);
  font-weight: 700;
  background: #fff;
  box-shadow: 0 6px 16px rgba(23, 33, 31, .06);
}
.actions {
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: flex-end;
  min-width: 120px;
}
.notice-badge {
  line-height: 1;
}
.notice-button {
  border-color: #e1ece8;
  color: var(--voluntary-primary);
  background: #fff;
}
.user-entry {
  display: inline-flex;
  align-items: center;
  min-height: 36px;
  padding: 0 10px;
  border-radius: 8px;
  background: var(--voluntary-primary-soft);
  cursor: pointer;
  color: var(--voluntary-primary-dark);
  font-weight: 700;
}
@media (max-width: 1040px) {
  .header {
    align-items: flex-start;
    flex-wrap: wrap;
    padding: 12px 18px;
  }
  .brand {
    min-width: 0;
  }
  .nav {
    order: 3;
    width: 100%;
    overflow-x: auto;
    justify-content: flex-start;
  }
  .actions {
    min-width: auto;
  }
}
@media (max-width: 560px) {
  .brand-copy small {
    display: none;
  }
  .brand-copy strong {
    font-size: 15px;
  }
  .nav a {
    padding: 0 10px;
  }
}
</style>
