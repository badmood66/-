package com.finance.financesystembackend.accounts.receivable.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("accounts_receivable")
public class AccountsReceivable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long salesOrderId;

    private Long customerId;

    private String arNo;

    private BigDecimal amount;

    private BigDecimal receivedAmount;

    /**
     * 0-未收 1-部分收 2-已收
     */
    private Integer status;

    private LocalDate dueDate;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
