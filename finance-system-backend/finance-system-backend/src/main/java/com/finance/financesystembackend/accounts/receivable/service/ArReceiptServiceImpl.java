package com.finance.financesystembackend.accounts.receivable.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finance.financesystembackend.accounts.receivable.entity.AccountsReceivable;
import com.finance.financesystembackend.accounts.receivable.entity.ArReceipt;
import com.finance.financesystembackend.accounts.receivable.mapper.AccountsReceivableMapper;
import com.finance.financesystembackend.accounts.receivable.mapper.ArReceiptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArReceiptServiceImpl
        extends ServiceImpl<ArReceiptMapper, ArReceipt>
        implements ArReceiptService {

    private final ArReceiptMapper receiptMapper;
    private final AccountsReceivableMapper arMapper;

    /**
     * 创建收款
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createReceipt(Long arId, BigDecimal amount) {

        if (arId == null || amount == null) {
            throw new IllegalArgumentException("收款参数不能为空");
        }

        AccountsReceivable ar = arMapper.selectById(arId);
        if (ar == null) {
            throw new IllegalArgumentException("AR 不存在");
        }

        ArReceipt receipt = new ArReceipt();
        receipt.setArId(arId);
        receipt.setReceiptNo(generateReceiptNo());
        receipt.setAmount(amount);
        receipt.setReceiptDate(LocalDate.now());
        receipt.setStatus(0); // 未对账

        receiptMapper.insert(receipt);
        return receipt.getId();
    }

    /**
     * 查询 AR 下的所有收款
     */
    @Override
    public List<ArReceipt> listByAr(Long arId) {
        return this.lambdaQuery()
                .eq(ArReceipt::getArId, arId)
                .orderByAsc(ArReceipt::getId)
                .list();
    }

    /**
     * 对账（核心）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reconcile(Long receiptId) {

        ArReceipt receipt = receiptMapper.selectById(receiptId);
        if (receipt == null) {
            throw new IllegalArgumentException("收款记录不存在");
        }

        if (receipt.getStatus() != null && receipt.getStatus() == 1) {
            throw new IllegalStateException("该收款已对账");
        }

        receipt.setStatus(1);
        receiptMapper.updateById(receipt);
    }

    private String generateReceiptNo() {
        return "RCPT-" +
                LocalDate.now().toString().replace("-", "") +
                "-" +
                UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
