package com.finance.financesystembackend.sales.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("sales_order")
public class SalesOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderNo;

    private Long customerId;

    private LocalDate orderDate;

    /** 订单总金额（后端计算） */
    private BigDecimal totalAmount;

    /**
     * 状态：
     * 0 - 草稿
     * 1 - 已确认
     * 2 - 已完成
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /** 非表字段：订单明细 */
    @TableField(exist = false)
    private List<SalesOrderItem> items;
}
