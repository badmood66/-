package com.finance.financesystembackend.expense.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("expense_payment")
public class ExpensePayment {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long expenseId;

    private BigDecimal amount;

    /** 0-未对账 1-已对账 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
