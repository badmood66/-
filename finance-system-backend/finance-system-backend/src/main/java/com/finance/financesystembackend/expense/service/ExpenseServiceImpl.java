package com.finance.financesystembackend.expense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finance.financesystembackend.expense.entity.Expense;
import com.finance.financesystembackend.expense.entity.ExpensePayment;
import com.finance.financesystembackend.expense.mapper.ExpenseMapper;
import com.finance.financesystembackend.expense.mapper.ExpensePaymentMapper;
import com.finance.financesystembackend.journal.service.JournalEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl
        extends ServiceImpl<ExpenseMapper, Expense>
        implements ExpenseService {

    private final ExpensePaymentMapper paymentMapper;
    private final JournalEntryService journalService;

    @Override
    @Transactional
    public Long create(String employeeName, BigDecimal amount) {

        if (employeeName == null || amount == null) {
            throw new IllegalArgumentException("报销参数不完整");
        }

        Expense expense = new Expense();
        expense.setEmployeeName(employeeName);
        expense.setAmount(amount);
        expense.setExpenseNo("EXP-" + System.currentTimeMillis());
        expense.setStatus(0);

        this.save(expense);
        return expense.getId();
    }

    @Override
    @Transactional
    public Long pay(Long expenseId) {

        Expense expense = this.getById(expenseId);
        if (expense == null) {
            throw new IllegalArgumentException("报销单不存在");
        }

        if (expense.getStatus() == 1) {
            throw new IllegalStateException("该报销单已支付");
        }

        ExpensePayment payment = new ExpensePayment();
        payment.setExpenseId(expenseId);
        payment.setAmount(expense.getAmount());
        payment.setStatus(0);

        paymentMapper.insert(payment);

        expense.setStatus(1);
        this.updateById(expense);

        journalService.createFromExpensePayment(
                payment.getId(),
                expense.getAmount()
        );

        return payment.getId();
    }

    @Override
    @Transactional
    public void reconcile(Long paymentId) {

        ExpensePayment payment = paymentMapper.selectById(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("付款记录不存在");
        }

        payment.setStatus(1);
        paymentMapper.updateById(payment);
    }

    /** ✅ 新增：分页查询 */
    @Override
    public Page<Expense> page(int page, int size) {
        return this.page(
                new Page<>(page, size)
        );
    }
}
