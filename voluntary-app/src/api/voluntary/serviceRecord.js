import request from '@/utils/request'

// 签到令牌信息
export function getScanInfo(token, config) {
  return request({
    url: `/app/voluntary/scan/${token}`,
    method: 'get',
    ...config
  })
}

// 令牌签到
export function scanCheckin(token) {
  return request({
    url: `/app/voluntary/scan/${token}/checkin`,
    method: 'post'
  })
}

// 令牌签退
export function scanCheckout(token) {
  return request({
    url: `/app/voluntary/scan/${token}/checkout`,
    method: 'post'
  })
}

// 我的服务记录
export function listMyServiceRecords(query) {
  return request({
    url: '/app/voluntary/service-records/mine',
    method: 'get',
    params: query
  })
}

// 我的服务时长汇总
export function getMyServiceSummary() {
  return request({
    url: '/app/voluntary/service-records/summary',
    method: 'get'
  })
}
