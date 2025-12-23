<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Balance Sheet</span>
        <el-button type="primary" size="small" @click="loadData">
          Refresh
        </el-button>
      </div>
    </template>

    <!-- 资产负债表主体 -->
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      :show-header="false"
    >
      <el-table-column prop="item" label="Item" width="300" />
      <el-table-column prop="amount" label="Amount">
        <template #default="{ row }">
          <span
            :style="row.highlight === 'positive'
              ? 'color:#67c23a;font-weight:bold'
              : row.highlight === 'negative'
              ? 'color:#f56c6c;font-weight:bold'
              : row.highlight === 'bold'
              ? 'font-weight:bold'
              : ''"
          >
            {{ row.amount }}
          </span>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
/* 👇 原有逻辑，完整保留 */
import { ref, onMounted, computed } from 'vue'
import { fetchBalanceSheet } from '@/api/report'

const data = ref({
  cash: 0,
  accountsReceivable: 0,
  totalAssets: 0,
  accountsPayable: 0,
  totalLiabilities: 0,
  equity: 0
})

const loadData = async () => {
  const res = await fetchBalanceSheet()
  data.value = res.data
}

onMounted(() => {
  loadData()
})

/**
 * 将原始数据转换为表格结构
 * 只做展示处理，不影响业务
 */
const tableData = computed(() => [
  { item: 'Assets', amount: '', highlight: 'bold' },
  { item: 'Cash', amount: data.value.cash },
  { item: 'Accounts Receivable', amount: data.value.accountsReceivable },
  {
    item: 'Total Assets',
    amount: data.value.totalAssets,
    highlight: 'bold'
  },

  { item: '', amount: '' },

  { item: 'Liabilities', amount: '', highlight: 'bold' },
  { item: 'Accounts Payable', amount: data.value.accountsPayable },
  {
    item: 'Total Liabilities',
    amount: data.value.totalLiabilities,
    highlight: 'bold'
  },

  { item: '', amount: '' },

  {
    item: 'Equity',
    amount: data.value.equity,
    highlight: data.value.equity >= 0 ? 'positive' : 'negative'
  }
])
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
