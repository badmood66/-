<template>
  <el-container style="height: 100vh">
    <!-- 左侧菜单 -->
    <el-aside width="220px" class="aside">
      <div class="logo">Finance System</div>

      <el-menu
        router
        background-color="#2d3a4b"
        text-color="#fff"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/supplier">供应商管理</el-menu-item>
        <el-menu-item index="/customer">客户管理</el-menu-item>

        <el-sub-menu index="1">
          <template #title>业务单据</template>
          <el-menu-item index="/purchase">采购订单</el-menu-item>
          <el-menu-item index="/sales">销售订单</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>往来账款</template>
          <el-menu-item index="/ap">应付 AP</el-menu-item>
          <el-menu-item index="/ar">应收 AR</el-menu-item>
        </el-sub-menu>

        <el-menu-item index="/journal">会计凭证</el-menu-item>
        <el-menu-item index="/report">财务报表</el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧主体 -->
    <el-container>
      <el-header class="header">
        <div>企业财务管理系统</div>
        <el-button type="danger" size="small" @click="logout">退出</el-button>
      </el-header>

      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

const logout = () => {
  router.push('/login')
}
</script>

<style scoped>
.aside {
  background-color: #2d3a4b;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-weight: bold;
  color: #fff;
  font-size: 18px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #eee;
}

.main {
  background: #f5f7fa;
  padding: 15px;
}
</style>
