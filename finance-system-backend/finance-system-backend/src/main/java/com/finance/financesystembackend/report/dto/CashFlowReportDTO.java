package com.finance.financesystembackend.report.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashFlowReportDTO {

    /** 现金流入 */
    private BigDecimal cashIn;

    /** 现金流出 */
    private BigDecimal cashOut;

    /** 净现金流 */
    private BigDecimal netCashFlow;
}
