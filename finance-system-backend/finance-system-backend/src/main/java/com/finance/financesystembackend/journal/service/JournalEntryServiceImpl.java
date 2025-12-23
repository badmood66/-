package com.finance.financesystembackend.journal.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finance.financesystembackend.journal.entity.JournalEntry;
import com.finance.financesystembackend.journal.mapper.JournalEntryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalEntryServiceImpl
        extends ServiceImpl<JournalEntryMapper, JournalEntry>
        implements JournalEntryService {

    private final JournalEntryMapper journalEntryMapper;

    /**
     * AR → 会计分录
     * 借：应收账款
     * 贷：主营业务收入
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createForAR(Long arId, String arNo, BigDecimal amount) {

        if (arId == null || amount == null) {
            throw new IllegalArgumentException("AR 参数不完整");
        }

        // ① 防止重复生成分录
        Long count = this.lambdaQuery()
                .eq(JournalEntry::getSourceType, "AR")
                .eq(JournalEntry::getSourceId, arId)
                .count();

        if (count > 0) {
            throw new IllegalStateException("该 AR 已生成会计分录");
        }

        // ② 借：应收账款
        JournalEntry debit = new JournalEntry();
        debit.setSourceType("AR");
        debit.setSourceId(arId);
        debit.setAccount("Accounts Receivable");
        debit.setDirection("DEBIT");
        debit.setAmount(amount);
        debit.setStatus(0); // 0 = 草稿

        // ③ 贷：收入
        JournalEntry credit = new JournalEntry();
        credit.setSourceType("AR");
        credit.setSourceId(arId);
        credit.setAccount("Revenue");
        credit.setDirection("CREDIT");
        credit.setAmount(amount);
        credit.setStatus(0); // 0 = 草稿

        journalEntryMapper.insert(debit);
        journalEntryMapper.insert(credit);
    }

    /**
     * ⭐ AP Payment → Journal
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createForApPayment(Long paymentId, BigDecimal amount) {

        if (paymentId == null || amount == null) {
            throw new IllegalArgumentException("AP Payment 参数不完整");
        }

        long count = this.lambdaQuery()
                .eq(JournalEntry::getSourceType, "AP_PAYMENT")
                .eq(JournalEntry::getSourceId, paymentId)
                .count();

        if (count > 0) {
            throw new IllegalStateException("该付款已生成分录");
        }

        // 借：应付账款
        JournalEntry debit = new JournalEntry();
        debit.setSourceType("AP_PAYMENT");
        debit.setSourceId(paymentId);
        debit.setAccount("Accounts Payable");
        debit.setDirection("DEBIT");
        debit.setAmount(amount);
        debit.setStatus(0);

        // 贷：银行存款
        JournalEntry credit = new JournalEntry();
        credit.setSourceType("AP_PAYMENT");
        credit.setSourceId(paymentId);
        credit.setAccount("Bank");
        credit.setDirection("CREDIT");
        credit.setAmount(amount);
        credit.setStatus(0);

        journalEntryMapper.insert(debit);
        journalEntryMapper.insert(credit);
    }

    /**
     * ⭐ Journal 过账
     * 将同一业务来源的所有分录：
     * status: 0 → 1
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postBySource(String sourceType, Long sourceId) {

        if (sourceType == null || sourceId == null) {
            throw new IllegalArgumentException("过账参数不能为空");
        }

        List<JournalEntry> entries = this.lambdaQuery()
                .eq(JournalEntry::getSourceType, sourceType)
                .eq(JournalEntry::getSourceId, sourceId)
                .list();

        if (entries.isEmpty()) {
            throw new IllegalStateException("未找到可过账的分录");
        }

        // 防止重复过账（只要有一条已过账，就整体拒绝）
        boolean alreadyPosted = entries.stream()
                .anyMatch(e -> e.getStatus() != null && e.getStatus() == 1);

        if (alreadyPosted) {
            throw new IllegalStateException("该业务分录已过账");
        }

        // 执行过账
        for (JournalEntry entry : entries) {
            entry.setStatus(1); // 1 = 已过账
            journalEntryMapper.updateById(entry);
        }
    }

    @Override
    @Transactional
    public void createFromExpensePayment(Long paymentId, BigDecimal amount) {

        // 借：费用
        JournalEntry debit = new JournalEntry();
        debit.setSourceType("EXPENSE_PAYMENT");
        debit.setSourceId(paymentId);
        debit.setAccount("Expense");
        debit.setDirection("DEBIT");
        debit.setAmount(amount);
        debit.setStatus(0);

        // 贷：现金
        JournalEntry credit = new JournalEntry();
        credit.setSourceType("EXPENSE_PAYMENT");
        credit.setSourceId(paymentId);
        credit.setAccount("Cash");
        credit.setDirection("CREDIT");
        credit.setAmount(amount);
        credit.setStatus(0);

        this.save(debit);
        this.save(credit);
    }


    /**
     * 查询业务对应的 Journal 分录
     */
    @Override
    public List<JournalEntry> listBySource(String sourceType, Long sourceId) {
        return this.lambdaQuery()
                .eq(JournalEntry::getSourceType, sourceType)
                .eq(JournalEntry::getSourceId, sourceId)
                .orderByAsc(JournalEntry::getId)
                .list();
    }
}
