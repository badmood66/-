<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Cash Flow Report</span>
        <el-button type="primary" size="small" @click="loadData">
          Refresh
        </el-button>
      </div>
    </template>

    <!-- 报表主体 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="metric-card">
          <div class="label">Cash In</div>
          <div class="value positive">
            {{ cashIn }}
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card class="metric-card">
          <div class="label">Cash Out</div>
          <div class="value negative">
            {{ cashOut }}
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card class="metric-card">
          <div class="label">Net Cash Flow</div>
          <div
            class="value"
            :class="netCashFlow >= 0 ? 'positive' : 'negative'"
          >
            {{ netCashFlow }}
          </div>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
/* 👇 原逻辑完整保留 */
import { ref, onMounted } from 'vue'
import { fetchCashFlowReport } from '@/api/report'

const cashIn = ref(0)
const cashOut = ref(0)
const netCashFlow = ref(0)

const loadData = async () => {
  const res = await fetchCashFlowReport()
  cashIn.value = res.data.cashIn
  cashOut.value = res.data.cashOut
  netCashFlow.value = res.data.netCashFlow
}

onMounted(loadData)
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
