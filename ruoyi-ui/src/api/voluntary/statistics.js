import request from '@/utils/request'

// 查询志愿业务统计总览
export function getStatisticsOverview(query) {
  return request({
    url: '/manager/voluntary/statistics/overview',
    method: 'get',
    params: query
  })
}

// 查询活动维度统计
export function listActivityStatistics(query) {
  return request({
    url: '/manager/voluntary/statistics/activities',
    method: 'get',
    params: query
  })
}

// 查询志愿者服务排行
export function listVolunteerStatistics(query) {
  return request({
    url: '/manager/voluntary/statistics/volunteers',
    method: 'get',
    params: query
  })
}

// 查询组织维度统计
export function listOrganizationStatistics(query) {
  return request({
    url: '/manager/voluntary/statistics/organizations',
    method: 'get',
    params: query
  })
}

// 查询服务趋势
export function listTrendStatistics(query) {
  return request({
    url: '/manager/voluntary/statistics/trend',
    method: 'get',
    params: query
  })
}
