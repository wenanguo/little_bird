import request from '@/utils/request'

const api = {
  add: '/base/sys-sms/add',
  edit: '/base/sys-sms/edit',
  list: '/base/sys-sms/list',
  listAll: '/base/sys-sms/listAll'
}

export default api

export function getSysSmsList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSysSms (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}
