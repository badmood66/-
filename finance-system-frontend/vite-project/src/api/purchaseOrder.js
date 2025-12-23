import request from '@/utils/request'

/**
 * 分页查询采购订单（主表）
 */
export function fetchPurchaseOrderPage(page, size) {
  return request.get('/purchase/orders/page', {
    params: { page, size }
  })
}

/**
 * 创建采购订单
 * ⚠️ 当前只创建主表 + 明细（items）
 */
export function createPurchaseOrder(data) {
  return request.post('/purchase/orders', data)
}

/**
 * 查询采购订单详情（含 items）
 */
export function getPurchaseOrder(id) {
  return request.get(`/purchase/orders/${id}`)
}

/**
 * 确认采购订单
 */
export function confirmPurchaseOrder(id) {
  return request.post(`/purchase/orders/${id}/confirm`)
}
