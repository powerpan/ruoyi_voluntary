import request from '@/utils/request'

// 我的通知列表
export function listMyNotifications(query) {
  return request({
    url: '/app/voluntary/notifications/mine',
    method: 'get',
    params: query
  })
}

// 我的未读通知数
export function getUnreadNotificationCount(config) {
  return request({
    url: '/app/voluntary/notifications/unread-count',
    method: 'get',
    ...config
  })
}

// 标记单条通知已读
export function markNotificationRead(id) {
  return request({
    url: `/app/voluntary/notifications/${id}/read`,
    method: 'put'
  })
}

// 标记全部通知已读
export function markAllNotificationsRead() {
  return request({
    url: '/app/voluntary/notifications/read-all',
    method: 'put'
  })
}
