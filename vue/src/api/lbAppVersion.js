import request from '@/utils/request'

const api = {
  add: '/lb_app_version/add',
  edit: '/lb_app_version/edit',
  list: '/lb_app_version/list',
  listAll: '/lb_app_version/listAll',
  batchDel: '/lb_app_version/batchDelete',
  del: '/lb_app_version/delete'
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
