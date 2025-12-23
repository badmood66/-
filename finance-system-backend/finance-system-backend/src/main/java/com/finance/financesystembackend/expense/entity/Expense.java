package com.finance.financesystembackend.expense.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("expense")
public class Expense {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String employeeName;

    private String expenseNo;

    private BigDecimal amount;

    /** 0-未支付 1-已支付 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
