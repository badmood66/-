import request from '@/utils/request'

/**
 * AR 分页查询
 * GET /ar?page=&size=
 */
export function fetchARPage(page, size) {
  return request.get('/ar/page', {
    params: { page, size }
  })
}

/**
 * 由销售订单生成 AR
 * POST /ar/from-sales/{salesOrderId}
 */
export function createARFromSales(salesOrderId) {
  return request.post(`/ar/from-sales/${salesOrderId}`)
}

/**
 * AR 收款
 * POST /ar/{arId}/receive?amount=&remark=
 */
export function receiveAR(arId, amount, remark) {
  return request.post(`/ar/${arId}/receive`, null, {
    params: { amount, remark }
  })
}

/**
 * AR 详情
 */
export function getARDetail(id) {
  return request.get(`/ar/${id}`)
}

/**
 * 查询 AR 下的收款记录
 * GET /ar/receipt/{arId}
 */
export function fetchARReceipts(arId) {
  return request.get(`/ar/receipt/${arId}`)
}

/**
 * 收款对账
 * POST /ar/receipt/reconcile/{receiptId}
 */
export function reconcileReceipt(receiptId) {
  return request.post(`/ar/receipt/reconcile/${receiptId}`)
}
