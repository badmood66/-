<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Accounts Payable Management</span>
      </div>
    </template>

    <!-- AP 列表 -->
    <el-table
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="apNo" label="AP No" />
      <el-table-column prop="purchaseOrderId" label="Purchase Order" />
      <el-table-column prop="supplierId" label="Supplier" />
      <el-table-column prop="amount" label="Amount" />
      <el-table-column prop="paidAmount" label="Paid" />

      <el-table-column label="Status" width="120">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'warning'">
            {{ row.status === 1 ? 'Paid' : 'Unpaid' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="dueDate" label="Due Date" />

      <el-table-column label="Action" width="220">
        <template #default="{ row }">
          <el-button size="small" @click="view(row.id)">
            View
          </el-button>
          <el-button
            v-if="row.status === 0"
            size="small"
            type="primary"
            @click="openPay(row)"
          >
            Pay
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pager">
      <el-button @click="prevPage" :disabled="page === 1">Prev</el-button>
      <span>Page {{ page }} / {{ totalPage }}</span>
      <el-button @click="nextPage" :disabled="page >= totalPage">Next</el-button>
    </div>
  </el-card>

  <!-- 付款弹窗 -->
  <el-dialog
    v-model="payVisible"
    title="Pay Accounts Payable"
    width="400px"
  >
    <el-form label-width="120px">
      <el-form-item label="AP No">
        <el-input :value="currentAp?.apNo" disabled />
      </el-form-item>

      <el-form-item label="Amount">
        <el-input-number
          v-model="payAmount"
          :min="0"
          :max="maxPayAmount"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="payVisible = false">Cancel</el-button>
      <el-button type="primary" @click="submitPay">
        Submit
      </el-button>
    </template>
  </el-dialog>

  <!-- AP 详情 -->
  <el-dialog
    v-model="detailVisible"
    title="AP Detail"
    width="600px"
  >
    <pre>{{ detail }}</pre>

    <template #footer>
      <el-button @click="detailVisible = false">Close</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
/* 👇 原有业务逻辑，完整保留 */
import { ref, onMounted, computed } from 'vue'
import {
  fetchApPage,
  getApDetail,
  payAp
} from '@/api/ap'

const list = ref([])
const page = ref(1)
const size = ref(5)
const total = ref(0)

const detail = ref(null)
const detailVisible = ref(false)

const payVisible = ref(false)
const currentAp = ref(null)
const payAmount = ref(0)
const maxPayAmount = ref(0)

const totalPage = computed(() =>
  Math.ceil(total.value / size.value) || 1
)

const loadData = async () => {
  const res = await fetchApPage(page.value, size.value)
  list.value = res.data.records
  total.value = res.data.total
}

const prevPage = () => {
  if (page.value > 1) {
    page.value--
    loadData()
  }
}

const nextPage = () => {
  if (page.value < totalPage.value) {
    page.value++
    loadData()
  }
}

const view = async (id) => {
  const res = await getApDetail(id)
  detail.value = res.data
  detailVisible.value = true
}

/* 打开付款弹窗 */
const openPay = (row) => {
  currentAp.value = row
  maxPayAmount.value = row.amount - row.paidAmount
  payAmount.value = maxPayAmount.value
  payVisible.value = true
}

/* 提交付款 */
const submitPay = async () => {
  await payAp(currentAp.value.id, payAmount.value)
  payVisible.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pager {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  gap: 15px;
}
</style>
