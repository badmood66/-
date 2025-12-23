package com.finance.financesystembackend.accounts.payable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finance.financesystembackend.accounts.payable.entity.ApPayment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApPaymentMapper extends BaseMapper<ApPayment> {
}
