package com.finance.financesystembackend.purchase.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.purchase.order.entity.PurchaseOrder;

public interface PurchaseOrderService {

    Long create(PurchaseOrder order);

    void confirm(Long orderId);

    PurchaseOrder getById(Long id);

    /** 分页查询采购订单（主表） */
    Page<PurchaseOrder> page(int page, int size);
}
