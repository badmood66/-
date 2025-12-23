package com.finance.financesystembackend.accounts.receivable.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.accounts.receivable.entity.AccountsReceivable;
import com.finance.financesystembackend.accounts.receivable.service.AccountsReceivableService;
import com.finance.financesystembackend.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/ar")
@RequiredArgsConstructor
public class AccountsReceivableController {

    private final AccountsReceivableService arService;

    /**
     * 由销售订单生成 AR
     */
    @PostMapping("/from-sales/{salesOrderId}")
    public ApiResponse<Long> create(@PathVariable Long salesOrderId) {
        return ApiResponse.success(arService.createFromSalesOrder(salesOrderId));
    }

    /**
     * AR 收款
     * POST /ar/{arId}/receive
     */
    @PostMapping("/{arId}/receive")
    public ApiResponse<Void> receive(
            @PathVariable Long arId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String remark
    ) {
        arService.receive(arId, amount, remark);
        return ApiResponse.success(null);
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountsReceivable> get(@PathVariable Long id) {
        return ApiResponse.success(arService.getById(id));
    }

    @GetMapping("/page")
    public ApiResponse<Page<AccountsReceivable>> page(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ApiResponse.success(arService.page(page, size));
    }
}
