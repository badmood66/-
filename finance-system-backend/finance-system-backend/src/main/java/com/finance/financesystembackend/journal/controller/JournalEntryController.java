package com.finance.financesystembackend.journal.controller;

import com.finance.financesystembackend.accounts.receivable.entity.AccountsReceivable;
import com.finance.financesystembackend.accounts.receivable.mapper.AccountsReceivableMapper;
import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.journal.entity.JournalEntry;
import com.finance.financesystembackend.journal.service.JournalEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalEntryController {

    private final JournalEntryService journalService;
    private final AccountsReceivableMapper arMapper;

    /**
     * 由 AR 生成会计分录
     * POST /journal/from-ar/{arId}
     */
    @PostMapping("/from-ar/{arId}")
    public ApiResponse<Void> createFromAr(@PathVariable Long arId) {

        AccountsReceivable ar = arMapper.selectById(arId);
        if (ar == null) {
            throw new IllegalArgumentException("应收账款不存在");
        }

        journalService.createForAR(
                ar.getId(),
                ar.getArNo(),
                ar.getAmount()
        );

        return ApiResponse.success();
    }

    /**
     * 查询分录
     */
    @GetMapping
    public ApiResponse<List<JournalEntry>> list(
            @RequestParam String sourceType,
            @RequestParam Long sourceId
    ) {
        return ApiResponse.success(
                journalService.listBySource(sourceType, sourceId)
        );
    }

    /** ⭐ AP Payment → Journal */
    @PostMapping("/from-ap-payment/{paymentId}")
    public ApiResponse<Void> createFromApPayment(
            @PathVariable Long paymentId,
            @RequestParam BigDecimal amount
    ) {
        journalService.createForApPayment(paymentId, amount);
        return ApiResponse.success();
    }

    /**
     * ⭐ Journal 过账
     */
    @PostMapping("/post")
    public ApiResponse<Void> post(
            @RequestParam String sourceType,
            @RequestParam Long sourceId
    ) {
        journalService.postBySource(sourceType, sourceId);
        return ApiResponse.success(null);
    }
}
