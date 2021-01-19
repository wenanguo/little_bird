import request from '@/utils/request'

const api = {
  add: '/base/sys-role/add',
  edit: '/base/sys-role/edit',
  list: '/base/sys-role/list',
  listAll: '/base/sys-role/listAll',
  batchDel: '/base/sys-role/batchDelete',
  del: '/base/sys-role/delete'
}

export default api

export function getSysRoleList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSysRole (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSysRole (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSysRole (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
