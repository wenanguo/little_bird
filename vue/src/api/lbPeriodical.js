import request from '@/utils/request'

const api = {
  add: '/lb_periodical/add',
  edit: '/lb_periodical/edit',
  list: '/lb_periodical/list',
  listAll: '/lb_periodical/list_all',
  batchDel: '/lb_periodical/batchDelete',
  del: '/lb_periodical/delete'
}

export default api

export function getLbPeriodicalList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

export function getLbPeriodicalListAll (parameter) {
  return request({
    url: api.listAll,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbPeriodical (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbPeriodical (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbPeriodical (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
