import request from '@/utils/request'

const api = {
  add: '/lb_post/add',
  edit: '/lb_post/edit',
  list: '/lb_post/list',
  listAll: '/lb_post/listAll',
  batchDel: '/lb_post/batchDelete',
  del: '/lb_post/delete'
}

export default api

export function getLbPostList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbPost (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbPost (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbPost (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
