import request from '@/utils/request'

const api = {
  add: '/lb_report/add',
  edit: '/lb_report/edit',
  list: '/lb_report/list',
  listAll: '/lb_report/listAll',
  batchDel: '/lb_reportt/batchDelete',
  del: '/lb_report/delete'
}

export default api

export function getLbReportList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbReport (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbReport (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbReport (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
