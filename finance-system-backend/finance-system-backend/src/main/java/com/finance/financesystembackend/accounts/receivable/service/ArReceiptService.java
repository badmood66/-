package com.finance.financesystembackend.accounts.receivable.service;

import com.finance.financesystembackend.accounts.receivable.entity.ArReceipt;

import java.math.BigDecimal;
import java.util.List;

public interface ArReceiptService {

    Long createReceipt(Long arId, BigDecimal amount);

    List<ArReceipt> listByAr(Long arId);

    void reconcile(Long receiptId);
}
