package com.finance.financesystembackend.report.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.finance.financesystembackend.accounts.payable.entity.ApPayment;
import com.finance.financesystembackend.accounts.payable.mapper.ApPaymentMapper;
import com.finance.financesystembackend.accounts.receivable.entity.ArReceipt;
import com.finance.financesystembackend.accounts.receivable.mapper.ArReceiptMapper;
import com.finance.financesystembackend.expense.entity.Expense;
import com.finance.financesystembackend.expense.mapper.ExpenseMapper;
import com.finance.financesystembackend.report.dto.CashFlowReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CashFlowReportServiceImpl implements CashFlowReportService {

    private final ArReceiptMapper arReceiptMapper;
    private final ApPaymentMapper apPaymentMapper;
    private final ExpenseMapper expenseMapper;

    @Override
    public CashFlowReportDTO generate() {

        /* 1️⃣ 现金流入：客户收款 */
        BigDecimal cashIn = arReceiptMapper.selectList(null).stream()
                .map(ArReceipt::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        /* 2️⃣ 现金流出：AP 付款 */
        BigDecimal apOut = apPaymentMapper.selectList(null).stream()
                .map(ApPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        /*3️⃣ 现金流出：费用（status = 1 才算） */
        BigDecimal expenseOut = expenseMapper.selectList(
                        new LambdaQueryWrapper<Expense>()
                                .eq(Expense::getStatus, 1)
                ).stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal cashOut = apOut.add(expenseOut);

        /* 4️⃣ 汇总 */
        CashFlowReportDTO dto = new CashFlowReportDTO();
        dto.setCashIn(cashIn);
        dto.setCashOut(cashOut);
        dto.setNetCashFlow(cashIn.subtract(cashOut));

        return dto;
    }
}
