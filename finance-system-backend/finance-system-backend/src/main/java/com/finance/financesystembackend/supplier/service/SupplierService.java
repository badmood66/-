package com.finance.financesystembackend.supplier.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.supplier.entity.Supplier;

public interface SupplierService {

    void create(Supplier supplier);

    Supplier getById(Long id);

    Page<Supplier> page(int page, int size);

    void update(Supplier supplier);

    void delete(Long id);
}
