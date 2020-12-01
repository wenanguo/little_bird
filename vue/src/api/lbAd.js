import request from '@/utils/request'

const api = {
  add: '/lb_ad/add',
  edit: '/lb_ad/edit',
  list: '/lb_ad/list',
  listAll: '/lb_add/listAll',
  batchDel: '/lb_ad/batchDelete',
  del: '/lb_ad/delete'
}

export default api

export function getLbAdList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbAd (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbAd (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbAd (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
