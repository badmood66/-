<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Sales Order Management</span>
        <el-button type="primary" @click="showAdd = true">
          Add Sales Order
        </el-button>
      </div>
    </template>

    <!-- 列表 -->
    <el-table
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="orderNo" label="Order No" />
      <el-table-column prop="customerId" label="Customer" />
      <el-table-column prop="orderDate" label="Order Date" />
      <el-table-column prop="totalAmount" label="Total Amount" />

      <el-table-column label="Status" width="120">
        <template #default="{ row }">
          <el-tag
            :type="row.status === 0
              ? 'info'
              : row.status === 1
              ? 'warning'
              : 'success'"
          >
            {{ row.status === 0
              ? 'Draft'
              : row.status === 1
              ? 'Confirmed'
              : 'Completed'
            }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="Action" width="120">
        <template #default="{ row }">
          <el-button size="small" @click="view(row.id)">View</el-button>
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

  <!-- 新增订单 -->
  <el-dialog
    v-model="showAdd"
    title="Add Sales Order"
    width="800px"
  >
    <el-form label-width="120px">
      <el-form-item label="Customer ID">
        <el-input v-model="form.customerId" />
      </el-form-item>

      <el-form-item label="Items">
        <el-table
          :data="form.items"
          border
          size="small"
        >
          <el-table-column label="Product ID">
            <template #default="{ row }">
              <el-input v-model="row.productId" />
            </template>
          </el-table-column>

          <el-table-column label="Name">
            <template #default="{ row }">
              <el-input v-model="row.productName" />
            </template>
          </el-table-column>

          <el-table-column label="Qty" width="100">
            <template #default="{ row }">
              <el-input-number
                v-model="row.quantity"
                :min="1"
              />
            </template>
          </el-table-column>

          <el-table-column label="Price" width="120">
            <template #default="{ row }">
              <el-input-number
                v-model="row.price"
                :min="0"
                :step="1"
              />
            </template>
          </el-table-column>

          <el-table-column label="Action" width="100">
            <template #default="{ $index }">
              <el-button
                size="small"
                type="danger"
                @click="removeItem($index)"
              >
                Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-button
          type="primary"
          plain
          size="small"
          style="margin-top: 10px"
          @click="addItem"
        >
          Add Item
        </el-button>
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="close">Cancel</el-button>
      <el-button type="primary" @click="submit">Submit</el-button>
    </template>
  </el-dialog>

  <!-- 订单详情 -->
  <el-dialog
    v-model="detailVisible"
    title="Sales Order Detail"
    width="600px"
  >
    <pre>{{ detail }}</pre>

    <template #footer>
      <el-button @click="detailVisible = false">Close</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
/* 👇 原逻辑完整保留 */
import { ref, onMounted, computed } from 'vue'
import {
  fetchSalesOrderPage,
  createSalesOrder,
  getSalesOrderDetail
} from '@/api/salesOrder'

const list = ref([])
const page = ref(1)
const size = ref(5)
const total = ref(0)

const showAdd = ref(false)
const detail = ref(null)
const detailVisible = ref(false)

const form = ref({
  customerId: '',
  items: []
})

const totalPage = computed(() =>
  Math.ceil(total.value / size.value) || 1
)

const loadData = async () => {
  const res = await fetchSalesOrderPage(page.value, size.value)
  list.value = res.data.records
  total.value = res.data.total
}

const addItem = () => {
  form.value.items.push({
    productId: '',
    productName: '',
    quantity: 1,
    price: 0
  })
}

const removeItem = (index) => {
  form.value.items.splice(index, 1)
}

const submit = async () => {
  await createSalesOrder(form.value)
  close()
  loadData()
}

const close = () => {
  showAdd.value = false
  form.value = { customerId: '', items: [] }
}

const view = async (id) => {
  const res = await getSalesOrderDetail(id)
  detail.value = res.data
  detailVisible.value = true
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
