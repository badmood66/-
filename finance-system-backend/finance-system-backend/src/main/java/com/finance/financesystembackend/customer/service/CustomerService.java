package com.finance.financesystembackend.customer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.customer.entity.Customer;

public interface CustomerService {

    void create(Customer customer);

    Customer getById(Long id);

    Page<Customer> page(int page, int size);

    void update(Customer customer);

    void delete(Long id);
}
