package com.finance.financesystembackend.sales.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finance.financesystembackend.sales.order.entity.SalesOrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesOrderItemMapper extends BaseMapper<SalesOrderItem> {
}
