import request from '@/utils/request'

/**
 * 分页查询客户
 */
export function fetchCustomerPage(page, size) {
  return request.get('/customer/page', {
    params: { page, size }
  })
}

/**
 * 根据 ID 查询客户（详情）
 */
export function getCustomer(id) {
  return request.get(`/customer/${id}`)
}

/**
 * 新增客户
 */
export function createCustomer(data) {
  return request.post('/customer', data)
}

/**
 * 更新客户
 */
export function updateCustomer(data) {
  return request.put('/customer', data)
}

/**
 * 删除客户
 */
export function deleteCustomer(id) {
  return request.delete(`/customer/${id}`)
}
