import request from '@/utils/request'

const api = {
  add: '/base/lb-periodical/add',
  edit: '/base/lb-periodical/edit',
  list: '/base/lb-periodical/list',
  listAll: '/base/lb-periodical/listAll',
  batchDel: '/base/lb-periodical/batchDelete',
  del: '/base/lb-periodical/delete'
}

export default api

export function getLbPeriodicalList (parameter) {
  return request({
    url: api.list,
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
