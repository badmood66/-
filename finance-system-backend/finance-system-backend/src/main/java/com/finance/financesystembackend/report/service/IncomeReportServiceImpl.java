package com.finance.financesystembackend.report.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.finance.financesystembackend.accounts.payable.entity.ApPayment;
import com.finance.financesystembackend.accounts.payable.mapper.ApPaymentMapper;
import com.finance.financesystembackend.expense.entity.Expense;
import com.finance.financesystembackend.expense.mapper.ExpenseMapper;
import com.finance.financesystembackend.report.dto.IncomeReportDTO;
import com.finance.financesystembackend.report.service.IncomeReportService;
import com.finance.financesystembackend.sales.order.entity.SalesOrder;
import com.finance.financesystembackend.sales.order.mapper.SalesOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class IncomeReportServiceImpl implements IncomeReportService {

    private final SalesOrderMapper salesOrderMapper;
    private final ApPaymentMapper apPaymentMapper;
    private final ExpenseMapper expenseMapper;

    @Override
    public IncomeReportDTO generate() {

        /* 1️⃣ 营业收入：销售订单总额 */
        BigDecimal revenue = salesOrderMapper.selectList(null)
                .stream()
                .map(SalesOrder::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        /* 2️⃣ 成本一：AP 已付款 */
        BigDecimal apCost = apPaymentMapper.selectList(null)
                .stream()
                .map(ApPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        /* 3️⃣ 成本二：已支付费用 */
        BigDecimal expenseCost = expenseMapper.selectList(
                        new LambdaQueryWrapper<Expense>()
                                .eq(Expense::getStatus, 1)
                ).stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCost = apCost.add(expenseCost);

        /* 4️⃣ 利润 */
        BigDecimal profit = revenue.subtract(totalCost);

        IncomeReportDTO dto = new IncomeReportDTO();
        dto.setRevenue(revenue);
        dto.setCost(totalCost);
        dto.setProfit(profit);

        return dto;
    }
}
