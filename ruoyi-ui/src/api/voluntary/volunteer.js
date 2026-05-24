import request from '@/utils/request'

// 查询志愿者档案列表
export function listVolunteer(query) {
  return request({
    url: '/manager/voluntary/volunteers/list',
    method: 'get',
    params: query
  })
}

// 查询志愿者档案详情
export function getVolunteer(id) {
  return request({
    url: '/manager/voluntary/volunteers/' + id,
    method: 'get'
  })
}

// 管理员修改志愿者档案
export function updateVolunteer(id, data) {
  return request({
    url: '/manager/voluntary/volunteers/' + id,
    method: 'put',
    data: data
  })
}

// 审核通过或驳回志愿者档案
export function auditVolunteer(id, data) {
  return request({
    url: '/manager/voluntary/volunteers/' + id + '/audit',
    method: 'post',
    data: data
  })
}

// 禁用或启用志愿者档案
export function changeVolunteerStatus(id, data) {
  return request({
    url: '/manager/voluntary/volunteers/' + id + '/status',
    method: 'put',
    data: data
  })
}

// 查询志愿者审核记录
export function listVolunteerAuditRecords(id) {
  return request({
    url: '/manager/voluntary/volunteers/' + id + '/audit-records',
    method: 'get'
  })
}
