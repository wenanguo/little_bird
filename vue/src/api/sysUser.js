import request from '@/utils/request'

const api = {
  add: '/sys_user/add',
  edit: '/sys_user/edit',
  list: '/sys_user/list',
  listAll: '/sys_user/listAll',
  batchDel: '/sys_user/batchDelete',
  del: '/sys_user/delete'
}

export default api

export function getSysUserList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSysUser (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSysUser (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSysUser (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
