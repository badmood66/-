package com.finance.financesystembackend.report.service;

import com.finance.financesystembackend.accounts.payable.entity.AccountsPayable;
import com.finance.financesystembackend.accounts.payable.mapper.AccountsPayableMapper;
import com.finance.financesystembackend.accounts.receivable.entity.AccountsReceivable;
import com.finance.financesystembackend.accounts.receivable.mapper.AccountsReceivableMapper;
import com.finance.financesystembackend.report.dto.BalanceSheetReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceSheetReportServiceImpl implements BalanceSheetReportService {

    private final AccountsReceivableMapper accountsReceivableMapper;
    private final AccountsPayableMapper accountsPayableMapper;

    @Override
    public BalanceSheetReportDTO generate() {

        // 1️⃣ 应收账款（资产）
        BigDecimal accountsReceivable = accountsReceivableMapper.selectList(null)
                .stream()
                .map(AccountsReceivable::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2️⃣ 应付账款（负债）
        BigDecimal accountsPayable = accountsPayableMapper.selectList(null)
                .stream()
                .map(AccountsPayable::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3️⃣ 资产负债表封装
        BalanceSheetReportDTO dto = new BalanceSheetReportDTO();
        dto.setCash(BigDecimal.ZERO); // 你当前系统未做现金账户，留 0 是完全合理的
        dto.setAccountsReceivable(accountsReceivable);
        dto.setAccountsPayable(accountsPayable);

        dto.setTotalAssets(dto.getCash().add(accountsReceivable));
        dto.setTotalLiabilities(accountsPayable);
        dto.setEquity(dto.getTotalAssets().subtract(dto.getTotalLiabilities()));

        return dto;
    }
}
