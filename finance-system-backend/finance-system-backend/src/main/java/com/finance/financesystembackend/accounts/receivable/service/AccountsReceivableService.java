package com.finance.financesystembackend.accounts.receivable.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.finance.financesystembackend.accounts.receivable.entity.AccountsReceivable;

import java.math.BigDecimal;

public interface AccountsReceivableService extends IService<AccountsReceivable> {

    Long createFromSalesOrder(Long salesOrderId);

    AccountsReceivable getById(Long id);

    Page<AccountsReceivable> page(int page, int size);

    /** 收款/核销 */
    void receive(Long arId, BigDecimal amount, String remark);
}
