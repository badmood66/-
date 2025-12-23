package com.finance.financesystembackend.expense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.expense.entity.Expense;

import java.math.BigDecimal;

public interface ExpenseService {

    Long create(String employeeName, BigDecimal amount);

    Long pay(Long expenseId);

    void reconcile(Long paymentId);

    /** 分页查询报销单 */
    Page<Expense> page(int page, int size);
}
