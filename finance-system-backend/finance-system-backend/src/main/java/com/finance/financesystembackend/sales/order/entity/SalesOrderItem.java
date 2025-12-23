package com.finance.financesystembackend.sales.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("sales_order_item")
public class SalesOrderItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Long productId;

    /** 商品名称（下单快照） */
    private String productName;

    private Integer quantity;

    private BigDecimal price;

    /** 行金额 = price * quantity */
    private BigDecimal amount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
