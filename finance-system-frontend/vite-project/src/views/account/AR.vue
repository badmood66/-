<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Accounts Receivable Management</span>
      </div>
    </template>

    <!-- AR 列表 -->
    <el-table
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="arNo" label="AR No" />
      <el-table-column prop="salesOrderId" label="Sales Order" />
      <el-table-column prop="customerId" label="Customer" />
      <el-table-column prop="amount" label="Amount" />
      <el-table-column prop="receivedAmount" label="Received" />

      <el-table-column label="Status" width="120">
        <template #default="{ row }">
          <el-tag
            :type="
              row.status === 0
                ? 'info'
                : row.status === 1
                ? 'warning'
                : 'success'
            "
          >
            {{ row.status === 0
              ? 'Unreceived'
              : row.status === 1
              ? 'Partial'
              : 'Completed'
            }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="dueDate" label="Due Date" />

      <el-table-column label="Action" width="220">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="openReceive(row)">
            Receive
          </el-button>
          <el-button size="small" @click="viewReceipts(row)">
            Receipts
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

  <!-- 收款弹窗 -->
  <el-dialog
    v-model="showReceive"
    :title="`Receive Payment - AR ${currentAR?.arNo || ''}`"
    width="400px"
  >
    <el-form label-width="100px">
      <el-form-item label="Amount">
        <el-input-number
          v-model="receiveAmount"
          :min="0"
        />
      </el-form-item>

      <el-form-item label="Remark">
        <el-input v-model="receiveRemark" />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="closeReceive">Cancel</el-button>
      <el-button type="primary" @click="submitReceive">
        Submit
      </el-button>
    </template>
  </el-dialog>

  <!-- 收款记录 -->
  <el-dialog
    v-model="receiptVisible"
    title="Receipts"
    width="600px"
  >
    <el-table
      :data="receipts"
      border
      style="width: 100%"
    >
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="receiptNo" label="Receipt No" />
      <el-table-column prop="receiptDate" label="Date" />
      <el-table-column prop="amount" label="Amount" />

      <el-table-column label="Status" width="120">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'warning' : 'success'">
            {{ row.status === 0 ? 'Unreconciled' : 'Reconciled' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="Action" width="120">
        <template #default="{ row }">
          <el-button
            v-if="row.status === 0"
            size="small"
            type="primary"
            @click="reconcile(row.id)"
          >
            Reconcile
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <template #footer>
      <el-button @click="receiptVisible = false">Close</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
/* 👇 原业务逻辑，全部保留 */
import { ref, onMounted, computed } from 'vue'
import {
  fetchARPage,
  receiveAR,
  fetchARReceipts,
  reconcileReceipt
} from '@/api/ar'

const list = ref([])
const page = ref(1)
const size = ref(5)
const total = ref(0)

const showReceive = ref(false)
const currentAR = ref(null)
const receiveAmount = ref(0)
const receiveRemark = ref('')

const receipts = ref([])
const receiptVisible = ref(false)

const totalPage = computed(() =>
  Math.ceil(total.value / size.value) || 1
)

const loadData = async () => {
  const res = await fetchARPage(page.value, size.value)
  list.value = res.data.records
  total.value = res.data.total
}

const openReceive = (ar) => {
  currentAR.value = ar
  receiveAmount.value = 0
  receiveRemark.value = ''
  showReceive.value = true
}

const closeReceive = () => {
  showReceive.value = false
}

const submitReceive = async () => {
  await receiveAR(
    currentAR.value.id,
    receiveAmount.value,
    receiveRemark.value
  )
  showReceive.value = false
  loadData()
}

const viewReceipts = async (ar) => {
  const res = await fetchARReceipts(ar.id)
  receipts.value = res.data
  receiptVisible.value = true
}

const reconcile = async (receiptId) => {
  await reconcileReceipt(receiptId)
  receiptVisible.value = false
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
