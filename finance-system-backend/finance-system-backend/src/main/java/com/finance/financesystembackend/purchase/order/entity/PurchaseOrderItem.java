package com.finance.financesystembackend.purchase.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("purchase_order_item")
public class PurchaseOrderItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String productName;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal amount;
}
