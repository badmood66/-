package com.finance.financesystembackend.supplier.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.common.response.ApiResponse;
import com.finance.financesystembackend.supplier.entity.Supplier;
import com.finance.financesystembackend.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    /**
     * 新增供应商
     * @return 新增成功后的 supplierId
     */
    @PostMapping
    public ApiResponse<Long> create(@RequestBody Supplier supplier) {
        supplierService.create(supplier);
        // MyBatis-Plus insert 后会自动回填 id
        return ApiResponse.success(supplier.getId());
    }

    /**
     * 根据 ID 查询供应商
     */
    @GetMapping("/{id}")
    public ApiResponse<Supplier> get(@PathVariable Long id) {
        return ApiResponse.success(supplierService.getById(id));
    }

    /**
     * 分页查询供应商
     */
    @GetMapping("/page")
    public ApiResponse<Page<Supplier>> page(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ApiResponse.success(supplierService.page(page, size));
    }


    /**
     * 更新供应商
     */
    @PutMapping
    public ApiResponse<Void> update(@RequestBody Supplier supplier) {
        supplierService.update(supplier);
        return ApiResponse.success(null);
    }

    /**
     * 删除供应商（你现在不测也没关系，代码是完整的）
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ApiResponse.success(null);
    }
}
