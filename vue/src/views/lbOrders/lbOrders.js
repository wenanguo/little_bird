import request from '@/utils/request'

const api = {
  add: '/base/lb-orders/add',
  edit: '/base/lb-orders/edit',
  list: '/base/lb-orders/list',
  listAll: '/base/lb-orders/listAll',
  batchDel: '/base/lb-orders/batchDelete',
  del: '/base/lb-orders/delete'
}

export default api

export function getLbOrdersList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbOrders (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbOrders (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbOrders (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
