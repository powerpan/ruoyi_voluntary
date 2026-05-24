import request from '@/utils/request'

// 公开活动列表
export function listActivities(query) {
  return request({
    url: '/app/voluntary/activities',
    method: 'get',
    params: query
  })
}

// 公开活动详情
export function getActivity(id) {
  return request({
    url: `/app/voluntary/activities/${id}`,
    method: 'get'
  })
}

// 报名活动
export function applyActivity(id, data) {
  return request({
    url: `/app/voluntary/activities/${id}/signups`,
    method: 'post',
    data
  })
}

// 当前用户报名记录
export function listMySignups(query) {
  return request({
    url: '/app/voluntary/signups/mine',
    method: 'get',
    params: query
  })
}

// 取消当前用户报名
export function cancelMySignup(id) {
  return request({
    url: `/app/voluntary/signups/${id}/cancel`,
    method: 'post'
  })
}
