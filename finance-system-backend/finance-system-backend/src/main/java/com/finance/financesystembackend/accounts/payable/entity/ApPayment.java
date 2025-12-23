package com.finance.financesystembackend.accounts.payable.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("ap_payment")
public class ApPayment {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** AP ID */
    private Long apId;

    /** 付款单号 */
    private String paymentNo;

    /** 付款金额 */
    private BigDecimal amount;

    /** 付款日期 */
    private LocalDate paymentDate;

    /** 状态：0-未对账 1-已对账 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
