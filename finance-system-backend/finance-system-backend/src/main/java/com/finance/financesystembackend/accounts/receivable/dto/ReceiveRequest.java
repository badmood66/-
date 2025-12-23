package com.finance.financesystembackend.accounts.receivable.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiveRequest {
    private BigDecimal amount;
    private String remark;
}
