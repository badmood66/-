import request from '@/utils/request'

/**
 * AP 分页查询
 */
export function fetchApPage(page, size) {
  return request.get('/ap/page', {
    params: { page, size }
  })
}

/**
 * 根据采购订单生成 AP
 */
export function createApFromPurchase(purchaseOrderId) {
  return request.post(`/ap/from-purchase/${purchaseOrderId}`)
}

/**
 * AP 付款
 */
export function payAp(apId, amount) {
  return request.post(`/ap/pay/${apId}`, null, {
    params: { amount }
  })
}

/**
 * 查询 AP 详情
 */
export function getApDetail(id) {
  return request.get(`/ap/${id}`)
}

/**
 * 付款对账
 */
export function reconcilePayment(paymentId) {
  return request.post(`/ap/pay/reconcile/${paymentId}`)
}
