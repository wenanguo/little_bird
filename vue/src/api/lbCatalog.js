import request from '@/utils/request'

const api = {
  add: '/base/lb-catalog/add',
  edit: '/base/lb-catalog/edit',
  list: '/base/lb-catalog/list',
  listAll: '/base/lb-catalog/listAll',
  batchDel: '/base/lb-catalog/batchDelete',
  del: '/base/lb-catalog/delete'
}

export default api

export function getLbCatalogList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbCatalog (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbCatalog (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbCatalog (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
