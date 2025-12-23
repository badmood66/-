<template>
  <div class="main">
    <!-- 左侧菜单 -->
    <aside class="menu">
      <!-- 系统标题 -->
      <div class="logo">
        企业财务管理系统
      </div>

      <!-- 基础资料 -->
      <div class="group">
        <div class="group-title">基础资料</div>
        <div class="item" @click="go('/basic/supplier')">
          供应商管理
        </div>
        <div class="item" @click="go('/basic/customer')">
          客户管理
        </div>
      </div>

      <!-- 业务单据 -->
      <div class="group">
        <div class="group-title">业务单据</div>
        <div class="item" @click="go('/business/sales-order')">
          销售订单
        </div>
        <div class="item" @click="go('/business/purchase-order')">
          采购订单
        </div>
      </div>

      <!-- 往来账款 -->
      <div class="group">
        <div class="group-title">往来账款</div>
        <div class="item" @click="go('/account/ar')">
          应收账款（AR）
        </div>
        <div class="item" @click="go('/account/ap')">
          应付账款（AP）
        </div>
      </div>

      <!-- 费用与凭证 -->
      <div class="group">
        <div class="group-title">费用与凭证</div>
        <div class="item" @click="go('/finance/expense')">
          费用管理
        </div>
        <div class="item" @click="go('/finance/journal')">
          会计凭证
        </div>
      </div>

      <!-- 财务报表 -->
      <div class="group">
        <div class="group-title">财务报表</div>
        <div class="item" @click="go('/report/cash-flow')">
          现金流量表
        </div>
        <div class="item" @click="go('/report/income-statement')">
          利润表
        </div>
        <div class="item" @click="go('/report/balance-sheet')">
          资产负债表
        </div>
      </div>

      <!-- 退出 -->
      <div class="logout" @click="logout">
        退出登录
      </div>
    </aside>

    <!-- 右侧内容区 -->
    <section class="content">
      <router-view />
    </section>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const go = (path) => {
  router.push(path)
}

const logout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}
</script>

<style scoped>
.main {
  display: flex;
  height: 100vh;
  overflow: hidden;
  background-color: #f5f7fa;
}

/* 左侧菜单整体 */
.menu {
  width: 220px;
  background-color: #2b2f3a;
  color: #bfcbd9;
  padding: 0 0 16px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}

/* 系统标题 */
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 16px;
  font-weight: bold;
  color: #ffffff;
  background-color: #242832;
  margin-bottom: 10px;
}

/* 分组 */
.group {
  margin-bottom: 14px;
}

.group-title {
  padding: 10px 20px 6px;
  font-size: 12px;
  color: #8a8f99;
}

/* 菜单项 */
.item {
  padding: 10px 20px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.item:hover {
  background-color: #1f2d3d;
  color: #409eff;
}

/* 退出按钮 */
.logout {
  margin-top: auto;
  padding: 12px 20px;
  text-align: center;
  font-size: 14px;
  cursor: pointer;
  color: #f56c6c;
}

.logout:hover {
  background-color: #1f2d3d;
}

/* 右侧内容区 */
.content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f5f7fa;
}
</style>
