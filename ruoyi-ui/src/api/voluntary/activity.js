import request from '@/utils/request'

// 查询活动列表
export function listActivity(query) {
  return request({
    url: '/manager/voluntary/activities/list',
    method: 'get',
    params: query
  })
}

// 查询活动详情
export function getActivity(id) {
  return request({
    url: '/manager/voluntary/activities/' + id,
    method: 'get'
  })
}

// 新增活动
export function addActivity(data) {
  return request({
    url: '/manager/voluntary/activities',
    method: 'post',
    data: data
  })
}

// 修改活动
export function updateActivity(id, data) {
  return request({
    url: '/manager/voluntary/activities/' + id,
    method: 'put',
    data: data
  })
}

// 变更活动状态
export function changeActivityStatus(id, data) {
  return request({
    url: '/manager/voluntary/activities/' + id + '/status',
    method: 'put',
    data: data
  })
}
