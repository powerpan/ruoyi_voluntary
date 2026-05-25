import request from '@/utils/request'

// 查询业务通知记录列表
export function listNotification(query) {
  return request({
    url: '/manager/voluntary/notifications/list',
    method: 'get',
    params: query
  })
}

// 查询业务通知记录详情
export function getNotification(id) {
  return request({
    url: '/manager/voluntary/notifications/' + id,
    method: 'get'
  })
}
