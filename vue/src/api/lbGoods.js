import request from '@/utils/request'

const api = {
  add: '/lb_goods/add',
  edit: '/lb_goods/edit',
  list: '/lb_goods/list',
  listAll: '/lb_goods/listAll',
  batchDel: '/lb_goods/batchDelete',
  del: '/lb_goods/delete'
}

export default api

export function getLbGoodsList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveLbGoods (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delLbGoods (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelLbGoods (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
