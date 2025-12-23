import request from '@/utils/request'

/**
 * 查询 Journal（按来源）
 */
export function fetchJournalList(sourceType, sourceId) {
  return request.get('/journal', {
    params: {
      sourceType,
      sourceId
    }
  })
}

/**
 * Journal 过账
 */
export function postJournal(sourceType, sourceId) {
  return request.post('/journal/post', null, {
    params: {
      sourceType,
      sourceId
    }
  })
}
