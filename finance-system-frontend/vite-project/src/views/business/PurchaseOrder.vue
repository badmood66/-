<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Purchase Order Management</span>
        <el-button type="primary" @click="openAdd">
          Add Purchase Order
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
      <el-table-column prop="poNo" label="PO No" />
      <el-table-column prop="supplierId" label="Supplier" />
      <el-table-column prop="orderDate" label="Order Date" />
      <el-table-column prop="totalAmount" label="Total Amount" />

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
              ? 'Draft'
              : row.status === 1
              ? 'Confirmed'
              : 'Completed'
            }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="Action" width="220">
        <template #default="{ row }">
          <el-button size="small" @click="view(row.id)">View</el-button>
          <el-button
            v-if="row.status === 0"
            size="small"
            type="primary"
            @click="confirmOrder(row.id)"
          >
            Confirm
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

  <!-- 新建采购订单 -->
  <el-dialog
    v-model="showForm"
    title="Add Purchase Order"
    width="700px"
  >
    <el-form label-width="120px">
      <el-form-item label="Supplier ID">
        <el-input-number v-model="form.supplierId" :min="1" />
      </el-form-item>

      <el-form-item label="Items">
        <el-table
          :data="form.items"
          border
          size="small"
        >
          <el-table-column label="Product">
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
              />
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

  <!-- 详情弹窗 -->
  <el-dialog
    v-model="showDetail"
    title="Purchase Order Detail"
    width="600px"
  >
    <el-descriptions border column="1">
      <el-descriptions-item label="PO No">
        {{ detail.poNo }}
      </el-descriptions-item>
      <el-descriptions-item label="Supplier">
        {{ detail.supplierId }}
      </el-descriptions-item>
      <el-descriptions-item label="Status">
        {{ detail.status }}
      </el-descriptions-item>
    </el-descriptions>

    <el-table
      :data="detail.items || []"
      border
      size="small"
      style="margin-top: 15px"
    >
      <el-table-column prop="productName" label="Product" />
      <el-table-column prop="quantity" label="Qty" />
      <el-table-column prop="price" label="Price" />
      <el-table-column prop="amount" label="Amount" />
    </el-table>

    <template #footer>
      <el-button @click="showDetail = false">Close</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
/* 👇 原有逻辑，完整保留 */
import { ref, computed, onMounted } from 'vue'
import {
  fetchPurchaseOrderPage,
  createPurchaseOrder,
  getPurchaseOrder,
  confirmPurchaseOrder
} from '@/api/purchaseOrder'

const list = ref([])
const page = ref(1)
const size = ref(5)
const total = ref(0)

const showForm = ref(false)
const showDetail = ref(false)
const detail = ref({})

const form = ref({
  supplierId: null,
  items: [{ productName: '', quantity: 1, price: 0 }]
})

const totalPage = computed(() => Math.ceil(total.value / size.value) || 1)

const loadData = async () => {
  const res = await fetchPurchaseOrderPage(page.value, size.value)
  list.value = res.data.records
  total.value = res.data.total
}

onMounted(loadData)

/* 分页 */
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

/* 新建 */
const openAdd = () => {
  showForm.value = true
  form.value = {
    supplierId: null,
    items: [{ productName: '', quantity: 1, price: 0 }]
  }
}

const addItem = () => {
  form.value.items.push({ productName: '', quantity: 1, price: 0 })
}

const submit = async () => {
  await createPurchaseOrder(form.value)
  showForm.value = false
  loadData()
}

const close = () => {
  showForm.value = false
}

/* 详情 */
const view = async (id) => {
  const res = await getPurchaseOrder(id)
  detail.value = res.data
  showDetail.value = true
}

/* 确认 */
const confirmOrder = async (id) => {
  if (confirm('Confirm this order?')) {
    await confirmPurchaseOrder(id)
    loadData()
  }
}
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
