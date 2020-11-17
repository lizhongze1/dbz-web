import request from '@/utils/request'

// 查询数据源列表
export function listSource(query) {
  return request({
    url: '/system/source/list',
    method: 'get',
    params: query
  })
}

// 查询数据源详细
export function getSource(id) {
  return request({
    url: '/system/source/' + id,
    method: 'get'
  })
}

// 新增数据源
export function addSource(data) {
  return request({
    url: '/system/source',
    method: 'post',
    data: data
  })
}

// 修改数据源
export function updateSource(data) {
  return request({
    url: '/system/source',
    method: 'put',
    data: data
  })
}

// 删除数据源
export function delSource(id) {
  return request({
    url: '/system/source/' + id,
    method: 'delete'
  })
}

// 导出数据源
export function exportSource(query) {
  return request({
    url: '/system/source/export',
    method: 'get',
    params: query
  })
}