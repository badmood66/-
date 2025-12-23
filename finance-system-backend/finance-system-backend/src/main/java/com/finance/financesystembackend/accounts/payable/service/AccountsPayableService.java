package com.finance.financesystembackend.accounts.payable.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finance.financesystembackend.accounts.payable.entity.AccountsPayable;

import java.math.BigDecimal;

public interface AccountsPayableService {

    /** 由采购订单生成 AP */
    Long createFromPurchase(Long purchaseOrderId);

    /** AP 付款 */
    Long pay(Long apId, BigDecimal amount);

    /** 付款对账 */
    void reconcilePayment(Long paymentId);

    AccountsPayable getById(Long id);

    Page<AccountsPayable> page(int page, int size);
}
