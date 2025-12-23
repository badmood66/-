<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Customer Management</span>
        <el-button type="primary" @click="openAdd">Add Customer</el-button>
      </div>
    </template>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="code" label="Code" />
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="contact" label="Contact" />
      <el-table-column prop="phone" label="Phone" />
      <el-table-column prop="address" label="Address" />
      <el-table-column label="Status" width="120">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? 'Enabled' : 'Disabled' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="Action" width="220">
        <template #default="{ row }">
          <el-button size="small" @click="view(row.id)">View</el-button>
          <el-button size="small" type="primary" @click="edit(row.id)">Edit</el-button>
          <el-button size="small" type="danger" @click="remove(row.id)">Delete</el-button>
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

  <!-- 弹窗表单 -->
  <el-dialog
    v-model="showForm"
    :title="mode === 'add' ? 'Add Customer' : mode === 'edit' ? 'Edit Customer' : 'Customer Detail'"
    width="500px"
  >
    <el-form label-width="100px">
      <el-form-item label="Code">
        <el-input v-model="form.code" :disabled="mode === 'view'" />
      </el-form-item>

      <el-form-item label="Name">
        <el-input v-model="form.name" :disabled="mode === 'view'" />
      </el-form-item>

      <el-form-item label="Contact">
        <el-input v-model="form.contact" :disabled="mode === 'view'" />
      </el-form-item>

      <el-form-item label="Phone">
        <el-input v-model="form.phone" :disabled="mode === 'view'" />
      </el-form-item>

      <el-form-item label="Address">
        <el-input v-model="form.address" :disabled="mode === 'view'" />
      </el-form-item>

      <el-form-item label="Status">
        <el-select v-model="form.status" :disabled="mode === 'view'">
          <el-option :value="1" label="Enabled" />
          <el-option :value="0" label="Disabled" />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="close">Cancel</el-button>
      <el-button
        v-if="mode !== 'view'"
        type="primary"
        @click="submit"
      >
        Submit
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
/* 👇 原有逻辑，全部保留 */
import { ref, computed, onMounted } from 'vue'
import {
  fetchCustomerPage,
  getCustomer,
  createCustomer,
  updateCustomer,
  deleteCustomer
} from '@/api/customer'

const list = ref([])
const page = ref(1)
const size = ref(5)
const total = ref(0)

const showForm = ref(false)
const mode = ref('add') // add | edit | view

const form = ref({
  id: null,
  code: '',
  name: '',
  contact: '',
  phone: '',
  address: '',
  status: 1
})

const totalPage = computed(() => Math.ceil(total.value / size.value) || 1)

const loadData = async () => {
  const res = await fetchCustomerPage(page.value, size.value)
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

/* 表单控制 */
const openAdd = () => {
  mode.value = 'add'
  showForm.value = true
  form.value = {
    id: null,
    code: '',
    name: '',
    contact: '',
    phone: '',
    address: '',
    status: 1
  }
}

const view = async (id) => {
  mode.value = 'view'
  const res = await getCustomer(id)
  form.value = res.data
  showForm.value = true
}

const edit = async (id) => {
  mode.value = 'edit'
  const res = await getCustomer(id)
  form.value = res.data
  showForm.value = true
}

const submit = async () => {
  if (mode.value === 'add') {
    await createCustomer(form.value)
  } else if (mode.value === 'edit') {
    await updateCustomer(form.value)
  }
  showForm.value = false
  loadData()
}

const remove = async (id) => {
  if (confirm('Confirm delete?')) {
    await deleteCustomer(id)
    loadData()
  }
}

const close = () => {
  showForm.value = false
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
