<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Supplier Management</span>
        <el-button type="primary" @click="openAdd">Add Supplier</el-button>
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
      <el-table-column
        prop="status"
        label="Status"
        width="100"
      >
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? 'Enabled' : 'Disabled' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="Action" width="220">
        <template #default="{ row }">
          <el-button size="small" @click="viewDetail(row.id)">View</el-button>
          <el-button size="small" type="primary" @click="editSupplier(row)">Edit</el-button>
          <el-button size="small" type="danger" @click="removeSupplier(row.id)">Delete</el-button>
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
    :title="mode === 'add' ? 'Add Supplier' : mode === 'edit' ? 'Edit Supplier' : 'Supplier Detail'"
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
      <el-button @click="closeForm">Cancel</el-button>
      <el-button
        v-if="mode !== 'view'"
        type="primary"
        @click="submitForm"
      >
        Submit
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
/* 👇 你原来的 script 几乎原封不动 */
import { ref, onMounted, computed } from 'vue'
import {
  fetchSupplierPage,
  createSupplier,
  updateSupplier,
  deleteSupplier,
  getSupplier
} from '@/api/supplier'

const list = ref([])
const page = ref(1)
const size = ref(5)
const total = ref(0)

const totalPage = computed(() => Math.ceil(total.value / size.value) || 1)

const showForm = ref(false)
const mode = ref('add')

const form = ref({
  id: null,
  code: '',
  name: '',
  contact: '',
  phone: '',
  address: '',
  status: 1
})

const loadData = async () => {
  const res = await fetchSupplierPage(page.value, size.value)
  console.log('【Supplier API 返回】', res)

  list.value = res?.data?.records || []
  total.value = res?.data?.total || 0
}


const openAdd = () => {
  mode.value = 'add'
  resetForm()
  showForm.value = true
}

const editSupplier = (item) => {
  mode.value = 'edit'
  form.value = { ...item }
  showForm.value = true
}

const viewDetail = async (id) => {
  mode.value = 'view'
  const res = await getSupplier(id)
  form.value = res.data.data
  showForm.value = true
}

const submitForm = async () => {
  if (!form.value.code || !form.value.name) {
    alert('Code and Name are required')
    return
  }

  if (mode.value === 'add') {
    await createSupplier(form.value)
  } else if (mode.value === 'edit') {
    await updateSupplier(form.value)
  }

  showForm.value = false
  loadData()
}

const removeSupplier = async (id) => {
  if (!confirm('Are you sure to delete this supplier?')) return
  await deleteSupplier(id)
  loadData()
}

const closeForm = () => {
  showForm.value = false
}

const resetForm = () => {
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

.pager {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  gap: 15px;
}
</style>
