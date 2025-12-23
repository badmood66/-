<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Income Statement</span>
        <el-button type="primary" size="small" @click="loadData">
          Refresh
        </el-button>
      </div>
    </template>

    <!-- 报表主体 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="metric-card">
          <div class="label">Revenue</div>
          <div class="value positive">
            {{ income.revenue }}
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card class="metric-card">
          <div class="label">Cost</div>
          <div class="value negative">
            {{ income.cost }}
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card class="metric-card">
          <div class="label">Profit</div>
          <div
            class="value"
            :class="income.profit >= 0 ? 'positive' : 'negative'"
          >
            {{ income.profit }}
          </div>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
/* 👇 原有逻辑，完整保留 */
import { ref, onMounted } from 'vue'
import { fetchIncomeReport } from '@/api/report'

const income = ref({
  revenue: 0,
  cost: 0,
  profit: 0
})

const loadData = async () => {
  const res = await fetchIncomeReport()
  income.value = res.data
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 指标卡片 */
.metric-card {
  text-align: center;
}

.label {
  font-size: 14px;
  color: #888;
  margin-bottom: 10px;
}

.value {
  font-size: 26px;
  font-weight: bold;
}

.positive {
  color: #67c23a;
}

.negative {
  color: #f56c6c;
}
</style>
