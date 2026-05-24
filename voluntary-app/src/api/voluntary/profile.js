import request from '@/utils/request'

// 查询当前登录用户的志愿者档案
export function getVolunteerProfile() {
  return request({
    url: '/app/voluntary/profile',
    method: 'get'
  })
}

// 修改当前登录用户的志愿者档案
export function updateVolunteerProfile(data) {
  return request({
    url: '/app/voluntary/profile',
    method: 'put',
    data
  })
}

// 查询当前登录用户的志愿者审核状态
export function getVolunteerAuditStatus(config = {}) {
  return request({
    url: '/app/voluntary/profile/audit-status',
    method: 'get',
    ...config
  })
}
