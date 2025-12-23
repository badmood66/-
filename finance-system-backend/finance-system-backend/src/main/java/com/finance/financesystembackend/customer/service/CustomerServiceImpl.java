package com.finance.financesystembackend.customer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.customer.entity.Customer;
import com.finance.financesystembackend.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;

    @Override
    public void create(Customer customer) {
        customerMapper.insert(customer);
    }

    @Override
    public Customer getById(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public Page<Customer> page(int page, int size) {
        Page<Customer> p = new Page<>(page, size);
        return customerMapper.selectPage(p, null);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateById(customer);
    }

    @Override
    public void delete(Long id) {
        customerMapper.deleteById(id);
    }
}
