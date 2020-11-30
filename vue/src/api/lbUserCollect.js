import request from '@/utils/request'

const api = {
  add: '/base/lb-user-collect/add',
  edit: '/base/lb-user-collect/edit',
  list: '/base/lb-user-collect/list',
  listAll: '/base/lb-user-collect/listAll',
  batchDel: '/base/lb-user-collect/batchDelete',
  del: '/base/lb-user-collect/delete'
}

export default api

export function getLbUserCollectList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbUserCollect (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbUserCollect (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbUserCollect (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
