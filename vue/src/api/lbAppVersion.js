import request from '@/utils/request'

const api = {
  add: '/base/lb-app-version/add',
  edit: '/base/lb-app-version/edit',
  list: '/base/lb-app-version/list',
  listAll: '/base/lb-app-version/listAll',
  batchDel: '/base/lb-app-version/batchDelete',
  del: '/base/lb-app-version/delete'
}

export default api

export function getLbAppVersionList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbAppVersion (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbAppVersion (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbAppVersion (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
