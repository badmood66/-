package com.finance.financesystembackend.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finance.financesystembackend.customer.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
}
