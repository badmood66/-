import request from '@/utils/request'

/**
 * 分页查询报销单
 */
export function fetchExpensePage(page, size) {
  return request.get('/expense/page', {
    params: { page, size }
  })
}

/**
 * 新增报销
 */
export function createExpense(employeeName, amount) {
  return request.post('/expense', null, {
    params: { employeeName, amount }
  })
}

/**
 * 报销付款
 */
export function payExpense(expenseId) {
  return request.post(`/expense/pay/${expenseId}`)
}
