package com.finance.financesystembackend.report.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class IncomeReportDTO {

    /** 营业收入 */
    private BigDecimal revenue;

    /** 营业成本 */
    private BigDecimal cost;

    /** 利润 */
    private BigDecimal profit;
}
