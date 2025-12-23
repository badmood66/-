package com.finance.financesystembackend.sales.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finance.financesystembackend.sales.order.entity.SalesOrder;
import com.finance.financesystembackend.sales.order.entity.SalesOrderItem;
import com.finance.financesystembackend.sales.order.mapper.SalesOrderItemMapper;
import com.finance.financesystembackend.sales.order.mapper.SalesOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesOrderServiceImpl
        extends ServiceImpl<SalesOrderMapper, SalesOrder>
        implements SalesOrderService {

    private final SalesOrderMapper salesOrderMapper;
    private final SalesOrderItemMapper salesOrderItemMapper;

    /**
     * 创建销售订单（主表 + 明细，事务）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(SalesOrder order) {

        // ========= 0️⃣ 基础校验 =========
        if (order == null) {
            throw new IllegalArgumentException("订单不能为空");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("订单明细不能为空");
        }

        // ========= 1️⃣ 设置订单主表字段 =========
        order.setOrderNo(generateOrderNo());
        order.setOrderDate(LocalDate.now());
        order.setStatus(0); // 0-草稿

        // ========= 2️⃣ 计算订单金额 =========
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SalesOrderItem item : order.getItems()) {

            // 明细校验
            validateItem(item);

            // 行金额 = 单价 × 数量
            BigDecimal itemAmount = item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            item.setAmount(itemAmount);

            totalAmount = totalAmount.add(itemAmount);
        }

        order.setTotalAmount(totalAmount);

        // ========= 3️⃣ 插入订单主表 =========
        salesOrderMapper.insert(order);
        Long orderId = order.getId();

        // ========= 4️⃣ 插入订单明细 =========
        for (SalesOrderItem item : order.getItems()) {
            item.setOrderId(orderId);
            salesOrderItemMapper.insert(item);
        }

        return orderId;
    }

    /**
     * 根据 ID 查询订单
     */
    @Override
    public SalesOrder getById(Long id) {
        return salesOrderMapper.selectById(id);
    }

    /**
     * 分页查询订单
     */
    @Override
    public Page<SalesOrder> page(int page, int size) {
        return salesOrderMapper.selectPage(
                new Page<>(page, size),
                null
        );
    }

    // ================= 私有方法 =================

    /**
     * 校验订单明细
     */
    private void validateItem(SalesOrderItem item) {

        if (item.getProductId() == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }

        if (item.getProductName() == null || item.getProductName().isBlank()) {
            throw new IllegalArgumentException("商品名称不能为空");
        }

        if (item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("商品数量必须大于 0");
        }

        if (item.getPrice() == null ||
                item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("商品价格必须大于 0");
        }
    }

    /**
     * 生成订单号
     * 规则：SO-YYYYMMDD-随机8位
     */
    private String generateOrderNo() {
        return "SO-" +
                LocalDate.now().toString().replace("-", "") +
                "-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase();
    }
}
