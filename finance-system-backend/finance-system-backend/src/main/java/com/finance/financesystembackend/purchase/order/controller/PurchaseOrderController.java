package com.finance.financesystembackend.purchase.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.purchase.order.entity.PurchaseOrder;
import com.finance.financesystembackend.purchase.order.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase/orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    /** 创建采购订单 */
    @PostMapping
    public ApiResponse<Long> create(@RequestBody PurchaseOrder order) {
        return ApiResponse.success(purchaseOrderService.create(order));
    }

    /** ✅ 分页查询采购订单 */
    @GetMapping("/page")
    public ApiResponse<Page<PurchaseOrder>> page(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ApiResponse.success(purchaseOrderService.page(page, size));
    }

    /** 查询采购订单详情 */
    @GetMapping("/{id}")
    public ApiResponse<PurchaseOrder> get(@PathVariable Long id) {
        return ApiResponse.success(purchaseOrderService.getById(id));
    }

    /** 确认采购订单 */
    @PostMapping("/{id}/confirm")
    public ApiResponse<Void> confirm(@PathVariable Long id) {
        purchaseOrderService.confirm(id);
        return ApiResponse.success();
    }
}

