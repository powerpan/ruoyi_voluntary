import request from '@/utils/request'

// 查询服务记录列表
export function listServiceRecord(query) {
  return request({
    url: '/manager/voluntary/service-records/list',
    method: 'get',
    params: query
  })
}

// 查询服务记录详情
export function getServiceRecord(id) {
  return request({
    url: '/manager/voluntary/service-records/' + id,
    method: 'get'
  })
}
