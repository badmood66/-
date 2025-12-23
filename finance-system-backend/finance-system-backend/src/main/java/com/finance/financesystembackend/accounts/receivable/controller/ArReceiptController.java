package com.finance.financesystembackend.accounts.receivable.controller;

import com.finance.financesystembackend.accounts.receivable.entity.ArReceipt;
import com.finance.financesystembackend.accounts.receivable.service.ArReceiptService;
import com.finance.financesystembackend.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/ar")
@RequiredArgsConstructor
public class ArReceiptController {

    private final ArReceiptService receiptService;

    /**
     * 创建收款
     */
    @PostMapping("/receipt/{arId}")
    public ApiResponse<Long> receipt(
            @PathVariable Long arId,
            @RequestParam BigDecimal amount
    ) {
        return ApiResponse.success(
                receiptService.createReceipt(arId, amount)
        );
    }

    /**
     * 查询 AR 下的收款
     */
    @GetMapping("/receipt/{arId}")
    public ApiResponse<List<ArReceipt>> list(@PathVariable Long arId) {
        return ApiResponse.success(
                receiptService.listByAr(arId)
        );
    }

    /**
     * 对账
     */
    @PostMapping("/receipt/reconcile/{receiptId}")
    public ApiResponse<Void> reconcile(@PathVariable Long receiptId) {
        receiptService.reconcile(receiptId);
        return ApiResponse.success(null);
    }
}
