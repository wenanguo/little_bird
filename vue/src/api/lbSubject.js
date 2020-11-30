import request from '@/utils/request'

const api = {
  add: '/base/lb-subject/add',
  edit: '/base/lb-subject/edit',
  list: '/base/lb-subject/list',
  listAll: '/base/lb-subject/listAll',
  batchDel: '/base/lb-subject/batchDelete',
  del: '/base/lb-subject/delete'
}

export default api

export function getLbSubjectList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbSubject (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbSubject (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbSubject (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
