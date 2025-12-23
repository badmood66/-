package com.finance.financesystembackend.purchase.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finance.financesystembackend.purchase.order.entity.PurchaseOrder;
import com.finance.financesystembackend.purchase.order.entity.PurchaseOrderItem;
import com.finance.financesystembackend.purchase.order.mapper.PurchaseOrderItemMapper;
import com.finance.financesystembackend.purchase.order.mapper.PurchaseOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl
        extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder>
        implements PurchaseOrderService {

    private final PurchaseOrderMapper orderMapper;
    private final PurchaseOrderItemMapper itemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(PurchaseOrder order) {

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("采购订单明细不能为空");
        }

        order.setPoNo(generatePoNo());
        order.setOrderDate(LocalDate.now());
        order.setStatus(0);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PurchaseOrderItem item : order.getItems()) {
            BigDecimal amount = item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setAmount(amount);
            totalAmount = totalAmount.add(amount);
        }

        order.setTotalAmount(totalAmount);
        orderMapper.insert(order);

        for (PurchaseOrderItem item : order.getItems()) {
            item.setOrderId(order.getId());
            itemMapper.insert(item);
        }

        return order.getId();
    }

    /** 分页查询采购订单（只查主表） */
    @Override
    public Page<PurchaseOrder> page(int page, int size) {
        Page<PurchaseOrder> p = new Page<>(page, size);
        return this.page(p);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirm(Long orderId) {

        PurchaseOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("采购订单不存在");
        }

        if (order.getStatus() != 0) {
            throw new IllegalStateException("订单状态不允许确认");
        }

        order.setStatus(1);
        orderMapper.updateById(order);
    }

    @Override
    public PurchaseOrder getById(Long id) {

        PurchaseOrder order = orderMapper.selectById(id);
        if (order == null) {
            return null;
        }

        List<PurchaseOrderItem> items = itemMapper.selectList(
                new LambdaQueryWrapper<PurchaseOrderItem>()
                        .eq(PurchaseOrderItem::getOrderId, id)
        );
        order.setItems(items);
        return order;
    }

    private String generatePoNo() {
        return "PO-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
