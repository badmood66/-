import request from '@/utils/request'

/**
 * 现金流量表
 */
export function fetchCashFlowReport() {
  return request.get('/report/cash-flow')
}

/**
 * 利润表（Income Statement）
 */
export function fetchIncomeReport() {
  return request.get('/report/income')
}

/**
 * 资产负债表
 */
export function fetchBalanceSheet() {
  return request.get('/report/balance-sheet')
}