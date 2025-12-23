package com.finance.financesystembackend.purchase.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@TableName("purchase_order")
public class PurchaseOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String poNo;

    private Long supplierId;

    private LocalDate orderDate;

    private BigDecimal totalAmount;

    /** 0-草稿 1-已确认 2-已完成 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private java.time.LocalDateTime createdAt;

    /** 非数据库字段 */
    @TableField(exist = false)
    private List<PurchaseOrderItem> items;
}
