<template>
  <el-card>
    <!-- 头部 -->
    <template #header>
      <div class="header">
        <span>Journal Management</span>
      </div>
    </template>

    <!-- 查询条件 -->
    <el-form inline class="search-form">
      <el-form-item label="Source Type">
        <el-select v-model="sourceType" style="width: 140px">
          <el-option label="AR" value="AR" />
          <el-option label="AP" value="AP" />
          <el-option label="EXPENSE" value="EXPENSE" />
        </el-select>
      </el-form-item>

      <el-form-item label="Source ID">
        <el-input-number
          v-model="sourceId"
          :min="1"
          style="width: 140px"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="loadData">
          Search
        </el-button>
      </el-form-item>
    </el-form>

    <!-- Journal 表格 -->
    <el-table
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="account" label="Account" />
      <el-table-column prop="direction" label="Direction" />
      <el-table-column prop="amount" label="Amount" />

      <el-table-column label="Status" width="120">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'warning' : 'success'">
            {{ row.status === 0 ? 'Draft' : 'Posted' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="createdAt" label="Created At" />
    </el-table>

    <!-- 过账 -->
    <div class="post-area">
      <el-button
        type="success"
        :disabled="!canPost"
        @click="post"
      >
        Post Journal
      </el-button>
    </div>
  </el-card>
</template>

<script setup>
/* 👇 原有业务逻辑，完整保留 */
import { ref, computed } from 'vue'
import { fetchJournalList, postJournal } from '@/api/journal'

const sourceType = ref('AR')
const sourceId = ref(null)

const list = ref([])

/**
 * 是否允许过账：
 * 只要存在 status = 0 的分录即可
 */
const canPost = computed(() => {
  return list.value.some(item => item.status === 0)
})

const loadData = async () => {
  if (!sourceId.value) {
    return
  }

  const res = await fetchJournalList(
    sourceType.value,
    sourceId.value
  )

  list.value = res.data
}

const post = async () => {
  if (!sourceId.value) return

  await postJournal(
    sourceType.value,
    sourceId.value
  )

  loadData()
}
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 16px;
}

.post-area {
  margin-top: 16px;
  text-align: right;
}
</style>
