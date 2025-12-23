package com.finance.financesystembackend.supplier.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.supplier.entity.Supplier;
import com.finance.financesystembackend.supplier.mapper.SupplierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierMapper supplierMapper;

    @Override
    public void create(Supplier supplier) {
        supplierMapper.insert(supplier);
    }

    @Override
    public Supplier getById(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public Page<Supplier> page(int page, int size) {
        return supplierMapper.selectPage(
                new Page<>(page, size),
                new QueryWrapper<>()
        );
    }

    @Override
    public void update(Supplier supplier) {
        supplierMapper.updateById(supplier);
    }

    @Override
    public void delete(Long id) {
        supplierMapper.deleteById(id);
    }
}
