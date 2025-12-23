package com.finance.financesystembackend.accounts.receivable.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("ar_receipt")
public class ArReceipt {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 对应 AR */
    private Long arId;

    /** 收款编号 */
    private String receiptNo;

    /** 收款金额 */
    private BigDecimal amount;

    /** 收款日期 */
    private LocalDate receiptDate;

    /** 0-未对账 1-已对账 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
