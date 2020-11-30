import request from '@/utils/request'

const api = {
  add: '/base/lb-post/add',
  edit: '/base/lb-post/edit',
  list: '/base/lb-post/list',
  listAll: '/base/lb-post/listAll',
  batchDel: '/base/lb-post/batchDelete',
  del: '/base/lb-post/delete'
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
