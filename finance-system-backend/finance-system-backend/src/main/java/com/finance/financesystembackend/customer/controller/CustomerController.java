package com.finance.financesystembackend.customer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.customer.entity.Customer;
import com.finance.financesystembackend.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 新增客户
     */
    @PostMapping
    public ApiResponse<Long> create(@RequestBody Customer customer) {
        customerService.create(customer);
        return ApiResponse.success(customer.getId());
    }

    /**
     * 根据 ID 查询客户
     */
    @GetMapping("/{id}")
    public ApiResponse<Customer> get(@PathVariable Long id) {
        return ApiResponse.success(customerService.getById(id));
    }

    /**
     * 分页查询客户（⚠️ 关键：明确 page 路径）
     */
    @GetMapping("/page")
    public ApiResponse<Page<Customer>> page(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ApiResponse.success(customerService.page(page, size));
    }

    /**
     * 更新客户
     */
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Customer customer) {
        customerService.update(customer);
        return ApiResponse.success(null);
    }

    /**
     * 删除客户
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ApiResponse.success(null);
    }
}
