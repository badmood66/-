package com.finance.financesystembackend.report.controller;

import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.report.dto.CashFlowReportDTO;
import com.finance.financesystembackend.report.service.CashFlowReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report/cash-flow")
@RequiredArgsConstructor
public class CashFlowReportController {

    private final CashFlowReportService cashFlowReportService;

    @GetMapping
    public ApiResponse<CashFlowReportDTO> generate() {
        return ApiResponse.success(cashFlowReportService.generate());
    }
}
