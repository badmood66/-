package com.finance.financesystembackend.purchase.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finance.financesystembackend.purchase.order.entity.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder> {
}
