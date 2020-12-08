import request from '@/utils/request'

const api = {
  add: '/base/lb-pay-order/add',
  edit: '/base/lb-pay-order/edit',
  list: '/base/lb-pay-order/list',
  listAll: '/base/lb-pay-order/listAll',
  batchDel: '/base/lb-pay-order/batchDelete',
  del: '/base/lb-pay-order/delete'
}

export default api

export function getLbPayOrderList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbPayOrder (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbPayOrder (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbPayOrder (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
