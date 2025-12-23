package com.finance.financesystembackend.report.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceSheetReportDTO {

    /** 资产 */
    private BigDecimal cash;
    private BigDecimal accountsReceivable;
    private BigDecimal totalAssets;

    /** 负债 */
    private BigDecimal accountsPayable;
    private BigDecimal totalLiabilities;

    /** 所有者权益 */
    private BigDecimal equity;
}
