import request from '@/utils/request'

const api = {
  add: '/base/lb-ad/add',
  edit: '/base/lb-ad/edit',
  list: '/base/lb-ad/list',
  listAll: '/base/lb-ad/listAll',
  batchDel: '/base/lb-ad/batchDelete',
  del: '/base/lb-ad/delete'
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
