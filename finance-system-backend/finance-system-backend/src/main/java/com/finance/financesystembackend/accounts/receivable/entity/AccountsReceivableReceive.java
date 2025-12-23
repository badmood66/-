package com.finance.financesystembackend.accounts.receivable.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("accounts_receivable_receive")
public class AccountsReceivableReceive {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 对应应收账款ID */
    private Long arId;

    /** 收款日期 */
    private LocalDate receiveDate;

    /** 收款金额 */
    private BigDecimal amount;

    /** 备注 */
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
