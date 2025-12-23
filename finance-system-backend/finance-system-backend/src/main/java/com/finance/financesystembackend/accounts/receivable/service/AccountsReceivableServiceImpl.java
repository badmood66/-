package com.finance.financesystembackend.accounts.receivable.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finance.financesystembackend.accounts.receivable.entity.AccountsReceivable;
import com.finance.financesystembackend.accounts.receivable.entity.AccountsReceivableReceive;
import com.finance.financesystembackend.accounts.receivable.mapper.AccountsReceivableMapper;
import com.finance.financesystembackend.accounts.receivable.mapper.AccountsReceivableReceiveMapper;
import com.finance.financesystembackend.sales.order.entity.SalesOrder;
import com.finance.financesystembackend.sales.order.mapper.SalesOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountsReceivableServiceImpl
        extends ServiceImpl<AccountsReceivableMapper, AccountsReceivable>
        implements AccountsReceivableService {

    private final AccountsReceivableMapper arMapper;
    private final AccountsReceivableReceiveMapper receiveMapper;
    private final SalesOrderMapper salesOrderMapper;

    /**
     * 从销售订单生成应收账款（AR）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFromSalesOrder(Long salesOrderId) {

        if (salesOrderId == null) {
            throw new IllegalArgumentException("销售订单ID不能为空");
        }

        SalesOrder order = salesOrderMapper.selectById(salesOrderId);
        if (order == null) {
            throw new IllegalArgumentException("销售订单不存在");
        }

        AccountsReceivable exist = this.lambdaQuery()
                .eq(AccountsReceivable::getSalesOrderId, salesOrderId)
                .one();

        if (exist != null) {
            throw new IllegalStateException("该销售订单已生成应收账款");
        }

        AccountsReceivable ar = new AccountsReceivable();
        ar.setSalesOrderId(order.getId());
        ar.setCustomerId(order.getCustomerId());
        ar.setArNo(generateArNo());
        ar.setAmount(order.getTotalAmount());
        ar.setReceivedAmount(BigDecimal.ZERO);
        ar.setStatus(0); // 0 = 未收
        ar.setDueDate(LocalDate.now().plusDays(30));

        arMapper.insert(ar);
        return ar.getId();
    }

    /**
     * 收款（AR 核销）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receive(Long arId, BigDecimal amount, String remark) {

        if (arId == null) {
            throw new IllegalArgumentException("AR ID不能为空");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("收款金额必须大于0");
        }

        AccountsReceivable ar = arMapper.selectById(arId);
        if (ar == null) {
            throw new IllegalArgumentException("应收账款不存在");
        }

        // 已收清不允许再收
        if (ar.getStatus() != null && ar.getStatus() == 2) {
            throw new IllegalStateException("该应收账款已收清");
        }

        BigDecimal total = ar.getAmount();
        BigDecimal received = ar.getReceivedAmount();
        BigDecimal newReceived = received.add(amount);

        if (newReceived.compareTo(total) > 0) {
            throw new IllegalArgumentException("收款金额超出应收金额");
        }

        // 1️⃣ 插入收款明细
        AccountsReceivableReceive rec = new AccountsReceivableReceive();
        rec.setArId(arId);
        rec.setReceiveDate(LocalDate.now());
        rec.setAmount(amount);
        rec.setRemark(remark);
        receiveMapper.insert(rec);

        // 2️⃣ 更新 AR 汇总
        ar.setReceivedAmount(newReceived);

        if (newReceived.compareTo(total) == 0) {
            ar.setStatus(2); // 已收清
        } else {
            ar.setStatus(1); // 部分收
        }

        arMapper.updateById(ar);
    }

    @Override
    public AccountsReceivable getById(Long id) {
        return arMapper.selectById(id);
    }

    @Override
    public Page<AccountsReceivable> page(int page, int size) {
        return arMapper.selectPage(new Page<>(page, size), null);
    }

    /**
     * 生成 AR 编号
     */
    private String generateArNo() {
        return "AR-" +
                LocalDate.now().toString().replace("-", "") +
                "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
