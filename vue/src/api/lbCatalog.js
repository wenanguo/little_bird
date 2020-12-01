import request from '@/utils/request'

const api = {
  add: '/lb_catalog/add',
  edit: '/lb_catalog/edit',
  list: '/lb_catalog/list',
  listAll: '/lb_catalog/listAll',
  batchDel: '/lb_catalog/batchDelete',
  del: '/lb_catalog/delete'
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
