package com.finance.financesystembackend.expense.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.expense.entity.Expense;
import com.finance.financesystembackend.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    /** 创建报销单 */
    @PostMapping
    public ApiResponse<Long> create(
            @RequestParam String employeeName,
            @RequestParam BigDecimal amount
    ) {
        return ApiResponse.success(
                expenseService.create(employeeName, amount)
        );
    }

    /** 报销付款 */
    @PostMapping("/pay/{expenseId}")
    public ApiResponse<Long> pay(@PathVariable Long expenseId) {
        return ApiResponse.success(
                expenseService.pay(expenseId)
        );
    }

    /** 对账 */
    @PostMapping("/reconcile/{paymentId}")
    public ApiResponse<Void> reconcile(@PathVariable Long paymentId) {
        expenseService.reconcile(paymentId);
        return ApiResponse.success();
    }

    /** ✅ 分页查询报销单 */
    @GetMapping("/page")
    public ApiResponse<Page<Expense>> page(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ApiResponse.success(
                expenseService.page(page, size)
        );
    }
}
