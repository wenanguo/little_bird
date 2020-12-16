import request from '@/utils/request'

const api = {
  add: '/base/lb-exchange-orders/add',
  edit: '/base/lb-exchange-orders/edit',
  list: '/base/lb-exchange-orders/list',
  listAll: '/base/lb-exchange-orders/listAll',
  batchDel: '/base/lb-exchange-orders/batchDelete',
  del: '/base/lb-exchange-orders/delete'
}

export default api

export function getLbExchangeOrdersList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbExchangeOrders (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbExchangeOrders (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbExchangeOrders (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
