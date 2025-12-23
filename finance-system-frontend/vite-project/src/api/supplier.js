import request from '@/utils/request'

/**
 * 分页查询供应商
 */
export function fetchSupplierPage(page, size) {
  return request.get('/supplier/page', {
    params: { page, size }
  })
}

/**
 * 根据 ID 查询供应商
 */
export function getSupplier(id) {
  return request.get(`/supplier/${id}`)
}

/**
 * 新增供应商
 */
export function createSupplier(data) {
  return request.post('/supplier', data)
}

/**
 * 更新供应商
 */
export function updateSupplier(data) {
  return request.put('/supplier', data)
}

/**
 * 删除供应商
 */
export function deleteSupplier(id) {
  return request.delete(`/supplier/${id}`)
}
