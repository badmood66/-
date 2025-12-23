package com.finance.financesystembackend.report.controller;

import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.report.dto.BalanceSheetReportDTO;
import com.finance.financesystembackend.report.service.BalanceSheetReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report/balance-sheet")
@RequiredArgsConstructor
public class BalanceSheetReportController {

    private final BalanceSheetReportService service;

    @GetMapping
    public ApiResponse<BalanceSheetReportDTO> generate() {
        return ApiResponse.success(service.generate());
    }
}

