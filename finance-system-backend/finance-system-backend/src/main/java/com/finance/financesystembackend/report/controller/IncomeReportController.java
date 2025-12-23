package com.finance.financesystembackend.report.controller;

import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.report.dto.IncomeReportDTO;
import com.finance.financesystembackend.report.service.IncomeReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class IncomeReportController {

    private final IncomeReportService incomeReportService;

    @GetMapping("/income")
    public ApiResponse<IncomeReportDTO> income() {
        return ApiResponse.success(incomeReportService.generate());
    }
}
