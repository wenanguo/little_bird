import request from '@/utils/request'

const api = {
  add: '/base/lb-protocol/add',
  edit: '/base/lb-protocol/edit',
  list: '/base/lb-protocol/list',
  listAll: '/base/lb-protocol/listAll',
  batchDel: '/base/lb-protocol/batchDelete',
  del: '/base/lb-protocol/delete'
}

export default api

export function getLbProtocolList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbProtocol (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbProtocol (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbProtocol (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
