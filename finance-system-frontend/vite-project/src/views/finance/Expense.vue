<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Expense Management</span>
        <el-button type="primary" @click="openAdd">
          Add Expense
        </el-button>
      </div>
    </template>

    <!-- 列表 -->
    <el-table
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="expenseNo" label="Expense No" />
      <el-table-column prop="employeeName" label="Employee" />
      <el-table-column prop="amount" label="Amount" />

      <el-table-column label="Status" width="120">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'warning' : 'success'">
            {{ row.status === 0 ? 'Unpaid' : 'Paid' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="createdAt" label="Created At" />

      <el-table-column label="Action" width="120">
        <template #default="{ row }">
          <el-button
            v-if="row.status === 0"
            size="small"
            type="primary"
            @click="pay(row.id)"
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

  <!-- 新增报销 -->
  <el-dialog
    v-model="addVisible"
    title="Add Expense"
    width="400px"
  >
    <el-form label-width="120px">
      <el-form-item label="Employee">
        <el-input v-model="form.employeeName" />
      </el-form-item>

      <el-form-item label="Amount">
        <el-input-number
          v-model="form.amount"
          :min="0"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="addVisible = false">Cancel</el-button>
      <el-button type="primary" @click="submit">
        Submit
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
/* 👇 原有逻辑，全部保留 */
import { ref, onMounted, computed } from 'vue'
import {
  fetchExpensePage,
  createExpense,
  payExpense
} from '@/api/expense'

const list = ref([])

const page = ref(1)
const size = ref(5)
const total = ref(0)

const addVisible = ref(false)

const form = ref({
  employeeName: '',
  amount: null
})

const totalPage = computed(() =>
  Math.ceil(total.value / size.value) || 1
)

const loadData = async () => {
  const res = await fetchExpensePage(page.value, size.value)
  list.value = res.data.records
  total.value = res.data.total
}

const openAdd = () => {
  addVisible.value = true
  form.value = {
    employeeName: '',
    amount: null
  }
}

const submit = async () => {
  if (!form.value.employeeName || !form.value.amount) {
    return
  }

  await createExpense(
    form.value.employeeName,
    form.value.amount
  )

  addVisible.value = false
  loadData()
}

const pay = async (id) => {
  if (!confirm('Confirm payment?')) return
  await payExpense(id)
  loadData()
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
