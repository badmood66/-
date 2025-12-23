import { createRouter, createWebHistory } from 'vue-router'

// 登录页
import Login from '@/views/Login.vue'

// 主界面（你刚刚粘贴的 Main.vue）
import Main from '@/views/Main.vue'

// ===== 基础资料 =====
import Supplier from '@/views/basic/Supplier.vue'
import Customer from '@/views/basic/Customer.vue'

// ===== 业务单据 =====
import SalesOrder from '@/views/business/SalesOrder.vue'
import PurchaseOrder from '@/views/business/PurchaseOrder.vue'

// ===== 往来账款 =====
import AR from '@/views/account/AR.vue'
import AP from '@/views/account/AP.vue'

// ===== 费用与凭证 =====
import Expense from '@/views/finance/Expense.vue'
import Journal from '@/views/finance/Journal.vue'

// ===== 财务报表 =====
import CashFlow from '@/views/report/CashFlow.vue'
import IncomeStatement from '@/views/report/IncomeStatement.vue'
import BalanceSheet from '@/views/report/BalanceSheet.vue'

const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    component: Main,
    redirect: '/basic/supplier', // 登录后的默认页面
    children: [
      // 基础资料
      { path: 'basic/supplier', component: Supplier },
      { path: 'basic/customer', component: Customer },

      // 业务单据
      { path: 'business/sales-order', component: SalesOrder },
      { path: 'business/purchase-order', component: PurchaseOrder },

      // 往来账款
      { path: 'account/ar', component: AR },
      { path: 'account/ap', component: AP },

      // 费用与凭证
      { path: 'finance/expense', component: Expense },
      { path: 'finance/journal', component: Journal },

      // 财务报表
      { path: 'report/cash-flow', component: CashFlow },
      { path: 'report/income-statement', component: IncomeStatement },
      { path: 'report/balance-sheet', component: BalanceSheet }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// ===== 登录鉴权守卫 =====
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')

  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
