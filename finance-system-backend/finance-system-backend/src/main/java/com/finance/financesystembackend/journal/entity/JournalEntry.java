package com.finance.financesystembackend.journal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("journal_entry")
public class JournalEntry {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 来源类型：AR / AP / EXPENSE */
    private String sourceType;

    /** 来源业务ID */
    private Long sourceId;

    /** 科目名称（简化） */
    private String account;

    /** 借贷方向：DEBIT / CREDIT */
    private String direction;

    /** 金额 */
    private BigDecimal amount;

    /** 状态：0-草稿 1-已过账 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
