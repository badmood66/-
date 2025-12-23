package com.finance.financesystembackend.journal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finance.financesystembackend.journal.entity.JournalEntry;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JournalEntryMapper extends BaseMapper<JournalEntry> {
}
