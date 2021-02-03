import request from '@/utils/request'

const api = {
  add: '/base/lb-exchange-give/add',
  edit: '/base/lb-exchange-give/edit',
  list: '/base/lb-exchange-give/list',
  listAll: '/base/lb-exchange-give/listAll',
  batchDel: '/base/lb-exchange-give/batchDelete',
  del: '/base/lb-exchange-give/delete'
}

export default api

export function getLbExchangeGiveList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbExchangeGive (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbExchangeGive (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbExchangeGive (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
