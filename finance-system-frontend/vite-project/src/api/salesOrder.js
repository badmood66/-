import request from '@/utils/request'

/**
 * 分页查询销售订单
 */
export function fetchSalesOrderPage(page, size) {
  return request.get('/sales/orders/page', {
    params: { page, size }
  })
}

/**
 * 创建销售订单
 */
export function createSalesOrder(data) {
  return request.post('/sales/orders', data)
}

/**
 * 查询销售订单详情
 */
export function getSalesOrderDetail(id) {
  return request.get(`/sales/orders/${id}`)
}
