package com.finance.financesystembackend.accounts.payable.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("accounts_payable")
public class AccountsPayable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 采购订单ID */
    private Long purchaseOrderId;

    /** 供应商ID */
    private Long supplierId;

    /** AP 编号 */
    private String apNo;

    /** 应付金额 */
    private BigDecimal amount;

    /** 已付金额 */
    private BigDecimal paidAmount;

    /** 状态：0-未付 1-已付 */
    private Integer status;

    /** 到期日 */
    private LocalDate dueDate;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
