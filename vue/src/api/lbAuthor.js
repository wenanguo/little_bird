import request from '@/utils/request'

const api = {
  add: '/lb_author/add',
  edit: '/lb_author/edit',
  list: '/lb_author/list',
  listAll: '/lb_author/list_all',
  batchDel: '/lb_author/batchDelete',
  del: '/lb_author/delete'
}

export default api

export function getLbAuthorList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

export function getLbAuthorListAll (parameter) {
  return request({
    url: api.listAll,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbAuthor (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbAuthor (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbAuthor (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
