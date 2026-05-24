import request from '@/utils/request'

// 查询活动报名列表
export function listSignup(query) {
  return request({
    url: '/manager/voluntary/signups/list',
    method: 'get',
    params: query
  })
}

// 查询活动报名详情
export function getSignup(id) {
  return request({
    url: '/manager/voluntary/signups/' + id,
    method: 'get'
  })
}

// 筛选活动报名
export function reviewSignup(id, data) {
  return request({
    url: '/manager/voluntary/signups/' + id + '/review',
    method: 'put',
    data: data
  })
}
