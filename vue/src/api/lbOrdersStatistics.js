import request from '@/utils/request'

const api = {
  add: '/base/lb-orders-statistics/add',
  edit: '/base/lb-orders-statistics/edit',
  list: '/lb_orders/order_statistics',
  listAll: '/base/lb-orders-statistics/listAll',
  batchDel: '/base/lb-orders-statistics/batchDelete',
  del: '/base/lb-orders-statistics/delete'
}

export default api

export function getLbOrdersStatisticsList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbOrdersStatistics (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbOrdersStatistics (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbOrdersStatistics (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
