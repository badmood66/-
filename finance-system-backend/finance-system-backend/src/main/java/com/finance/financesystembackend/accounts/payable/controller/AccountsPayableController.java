package com.finance.financesystembackend.accounts.payable.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.accounts.payable.entity.AccountsPayable;
import com.finance.financesystembackend.accounts.payable.service.AccountsPayableService;
import com.finance.financesystembackend.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/ap")
@RequiredArgsConstructor
public class AccountsPayableController {

    private final AccountsPayableService apService;

    /** 采购订单 → AP */
    @PostMapping("/from-purchase/{purchaseOrderId}")
    public ApiResponse<Long> create(@PathVariable Long purchaseOrderId) {
        return ApiResponse.success(apService.createFromPurchase(purchaseOrderId));
    }

    /** AP 付款 */
    @PostMapping("/pay/{apId}")
    public ApiResponse<Long> pay(
            @PathVariable Long apId,
            @RequestParam BigDecimal amount
    ) {
        return ApiResponse.success(apService.pay(apId, amount));
    }

    /** 付款对账 */
    @PostMapping("/pay/reconcile/{paymentId}")
    public ApiResponse<Void> reconcile(@PathVariable Long paymentId) {
        apService.reconcilePayment(paymentId);
        return ApiResponse.success();
    }

    /** 查询 AP 详情 */
    @GetMapping("/{id}")
    public ApiResponse<AccountsPayable> get(@PathVariable Long id) {
        return ApiResponse.success(apService.getById(id));
    }

    /** ✅ 分页查询 AP（关键改动） */
    @GetMapping("/page")
    public ApiResponse<Page<AccountsPayable>> page(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ApiResponse.success(apService.page(page, size));
    }
}

