package com.finance.financesystembackend.journal.service;

import com.finance.financesystembackend.journal.entity.JournalEntry;

import java.math.BigDecimal;
import java.util.List;

public interface JournalEntryService {

    /** 从 AR 生成会计分录 */
    void createForAR(Long arId, String arNo, BigDecimal amount);

    /** 查询业务对应分录 */
    List<JournalEntry> listBySource(String sourceType, Long sourceId);

    /** ⭐ 过账 */
    void postBySource(String sourceType, Long sourceId);

    /** AP Payment → Journal */
    void createForApPayment(Long paymentId, BigDecimal amount);

    void createFromExpensePayment(Long paymentId, BigDecimal amount);

}
