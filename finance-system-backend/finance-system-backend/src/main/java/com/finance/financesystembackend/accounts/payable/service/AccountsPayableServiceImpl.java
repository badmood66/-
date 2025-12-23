package com.finance.financesystembackend.accounts.payable.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finance.financesystembackend.accounts.payable.entity.AccountsPayable;
import com.finance.financesystembackend.accounts.payable.entity.ApPayment;
import com.finance.financesystembackend.accounts.payable.mapper.AccountsPayableMapper;
import com.finance.financesystembackend.accounts.payable.mapper.ApPaymentMapper;
import com.finance.financesystembackend.purchase.order.entity.PurchaseOrder;
import com.finance.financesystembackend.purchase.order.mapper.PurchaseOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountsPayableServiceImpl
        extends ServiceImpl<AccountsPayableMapper, AccountsPayable>
        implements AccountsPayableService {

    private final AccountsPayableMapper apMapper;
    private final ApPaymentMapper paymentMapper;
    private final PurchaseOrderMapper purchaseOrderMapper;

    /**
     * 采购订单 → AP
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFromPurchase(Long purchaseOrderId) {

        if (purchaseOrderId == null) {
            throw new IllegalArgumentException("采购订单ID不能为空");
        }

        PurchaseOrder po = purchaseOrderMapper.selectById(purchaseOrderId);
        if (po == null) {
            throw new IllegalArgumentException("采购订单不存在");
        }

        // 防重复
        AccountsPayable exist = this.lambdaQuery()
                .eq(AccountsPayable::getPurchaseOrderId, purchaseOrderId)
                .one();
        if (exist != null) {
            throw new IllegalStateException("该采购订单已生成应付账款");
        }

        AccountsPayable ap = new AccountsPayable();
        ap.setPurchaseOrderId(po.getId());
        ap.setSupplierId(po.getSupplierId());
        ap.setApNo(generateApNo());
        ap.setAmount(po.getTotalAmount());
        ap.setPaidAmount(BigDecimal.ZERO);
        ap.setStatus(0);
        ap.setDueDate(LocalDate.now().plusDays(30));

        apMapper.insert(ap);
        return ap.getId();
    }

    /**
     * AP 付款
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long pay(Long apId, BigDecimal amount) {

        AccountsPayable ap = apMapper.selectById(apId);
        if (ap == null) {
            throw new IllegalArgumentException("AP 不存在");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("付款金额非法");
        }

        ApPayment payment = new ApPayment();
        payment.setApId(apId);
        payment.setPaymentNo(generatePaymentNo());
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus(0);

        paymentMapper.insert(payment);
        return payment.getId();
    }

    /**
     * 付款对账
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reconcilePayment(Long paymentId) {

        ApPayment payment = paymentMapper.selectById(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("付款记录不存在");
        }

        if (payment.getStatus() == 1) {
            throw new IllegalStateException("该付款已对账");
        }

        AccountsPayable ap = apMapper.selectById(payment.getApId());
        if (ap == null) {
            throw new IllegalStateException("关联 AP 不存在");
        }

        // 更新付款状态
        payment.setStatus(1);
        paymentMapper.updateById(payment);

        // 更新 AP
        BigDecimal newPaid = ap.getPaidAmount().add(payment.getAmount());
        ap.setPaidAmount(newPaid);

        if (newPaid.compareTo(ap.getAmount()) >= 0) {
            ap.setStatus(1); // 已付
        }

        apMapper.updateById(ap);
    }

    @Override
    public AccountsPayable getById(Long id) {
        return apMapper.selectById(id);
    }

    @Override
    public Page<AccountsPayable> page(int page, int size) {
        return apMapper.selectPage(new Page<>(page, size), null);
    }

    private String generateApNo() {
        return "AP-" +
                LocalDate.now().toString().replace("-", "") +
                "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generatePaymentNo() {
        return "PAY-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
