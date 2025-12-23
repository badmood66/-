package com.finance.financesystembackend.sales.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.sales.order.entity.SalesOrder;
import com.finance.financesystembackend.sales.order.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales/orders")
@RequiredArgsConstructor
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    /**
     * 创建销售订单
     */
    @PostMapping
    public ApiResponse<Long> create(@RequestBody SalesOrder order) {
        Long orderId = salesOrderService.create(order);
        return ApiResponse.success(orderId);
    }

    /**
     * 根据 ID 查询订单
     */
    @GetMapping("/{id}")
    public ApiResponse<SalesOrder> get(@PathVariable Long id) {
        return ApiResponse.success(salesOrderService.getById(id));
    }

    /**
     * ✅ 分页查询订单（注意这里）
     */
    @GetMapping("/page")
    public ApiResponse<Page<SalesOrder>> page(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ApiResponse.success(salesOrderService.page(page, size));
    }
}

