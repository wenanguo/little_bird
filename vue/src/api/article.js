import request from '@/utils/request'

const api = {
  add: '/base/article/add',
  edit: '/base/article/edit',
  list: '/base/article/list',
  listAll: '/base/article/listAll',
  batchDel: '/base/article/batchDelete',
  del: '/base/article/delete'
}

export default api

export function getArticleList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveArticle (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delArticle (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelArticle (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
