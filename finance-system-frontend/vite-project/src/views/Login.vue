<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="title">Finance System</h2>

      <el-form :model="form" @keyup.enter="login">
        <el-form-item>
          <el-input
            v-model="form.username"
            placeholder="用户名"
            size="large"
            prefix-icon="User"
          />
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="form.password"
            type="password"
            placeholder="密码"
            size="large"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            size="large"
            style="width: 100%"
            @click="login"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>

      <div class="tip">测试账号：admin / 123456</div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const form = reactive({
  username: '',
  password: ''
})

const login = () => {
  // 模拟登录成功，存 token
  localStorage.setItem('token', 'admin-token')

  // 跳转到主界面
  router.push('/')
}

</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(120deg, #4e9af1, #67c23a);
}

.login-card {
  width: 380px;
  padding: 30px 25px;
  border-radius: 10px;
}

.title {
  text-align: center;
  margin-bottom: 25px;
  font-weight: bold;
}

.tip {
  text-align: center;
  color: #999;
  font-size: 13px;
  margin-top: 10px;
}
</style>
