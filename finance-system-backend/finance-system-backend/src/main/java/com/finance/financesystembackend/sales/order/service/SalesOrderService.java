package com.finance.financesystembackend.sales.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.sales.order.entity.SalesOrder;

public interface SalesOrderService {

    /**
     * 创建销售订单（主表 + 明细）
     */
    Long create(SalesOrder order);

    /**
     * 根据 ID 查询订单
     */
    SalesOrder getById(Long id);

    /**
     * 分页查询订单
     */
    Page<SalesOrder> page(int page, int size);
}
