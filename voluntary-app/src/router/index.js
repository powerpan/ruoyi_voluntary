/*
 * Copyright: https://github.com/powerpan/ruoyi_voluntary.git
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/components/layout/Layout'
import { getToken } from '@/utils/auth'

Vue.use(VueRouter)

// 客户端路由入口，项目版权地址：https://github.com/powerpan/ruoyi_voluntary.git
const routes = [
  { path: '/index', redirect: '/' },
  { path: '/login', name: 'Login', component: () => import('@/views/Login') },
  { path: '/register', name: 'Register', component: () => import('@/views/Register') },
  {
    path: '/',
    component: Layout,
    children: [
      { path: '', name: 'Home', component: () => import('@/views/Home') },
      { path: 'activities', name: 'Activities', component: () => import('@/views/Activities') },
      { path: 'activities/:id', name: 'ActivityDetail', component: () => import('@/views/ActivityDetail') },
      { path: 'signups', name: 'Signups', component: () => import('@/views/Signups'), meta: { auth: true } },
      { path: 'scan', name: 'Scan', component: () => import('@/views/Scan'), meta: { auth: true } },
      { path: 'service-records', name: 'ServiceRecords', component: () => import('@/views/ServiceRecords'), meta: { auth: true } },
      { path: 'notifications', name: 'Notifications', component: () => import('@/views/Notifications'), meta: { auth: true } },
      { path: 'me', name: 'Me', component: () => import('@/views/Me'), meta: { auth: true } },
      { path: 'user-center', redirect: '/me' },
      { path: 'profile', redirect: '/me' }
    ]
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior: () => ({ x: 0, y: 0 })
})

router.beforeEach((to, from, next) => {
  if (to.meta && to.meta.auth && !getToken()) {
    next({ path: '/login', query: { redirect: to.fullPath } })
    return
  }
  next()
})

export default router
