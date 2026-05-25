import request from '@/utils/request'

// 查询签到签退记录列表
export function listCheckin(query) {
  return request({
    url: '/manager/voluntary/checkins/list',
    method: 'get',
    params: query
  })
}

// 查询签到签退记录详情
export function getCheckin(id) {
  return request({
    url: '/manager/voluntary/checkins/' + id,
    method: 'get'
  })
}

// 查询活动二维码令牌
export function listQrToken(activityId, query) {
  return request({
    url: '/manager/voluntary/activities/' + activityId + '/qr-tokens',
    method: 'get',
    params: query
  })
}

// 生成签到或签退二维码令牌
export function generateQrToken(activityId, data) {
  return request({
    url: '/manager/voluntary/activities/' + activityId + '/qr-tokens',
    method: 'post',
    data: data
  })
}

// 停用二维码令牌
export function disableQrToken(id) {
  return request({
    url: '/manager/voluntary/qr-tokens/' + id + '/disable',
    method: 'put'
  })
}
