package com.project.financialtracker.income;

import com.project.financialtracker.expense.Expense;
import com.project.financialtracker.expense.ExpenseDto;
import com.project.financialtracker.expense.ExpenseSummaryDto;
import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.ResponseWrapper;
import com.project.financialtracker.wallet.Wallet;
import com.project.financialtracker.wallet.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final WalletRepository walletRepository;

    public IncomeService(IncomeRepository incomeRepository, WalletRepository walletRepository) {
        this.incomeRepository = incomeRepository;
        this.walletRepository = walletRepository;
    }

    public List<IncomeDto> getIncome(Integer userId){
        List<Income> incomeList = incomeRepository.getIncomeByUserId(userId);
        return incomeList.stream().map(IncomeDto::new).toList();
    }

    public IncomeDto addIncome(Income income){
        Income newIncome = incomeRepository.save(income);
        Integer walletId = newIncome.getWallet().getWalletId();
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            wallet.setAmount(wallet.getAmount() + income.getAmount());
            walletRepository.save(wallet);
        }
        return new IncomeDto(newIncome);
    }

    public List<IncomeSummaryDto> getDataByCategory(Integer id){
        User user = new User();
        user.setUserId(id);
        List<Object[]> result = incomeRepository.getMonthlyIncomeSummaryByCategory(user);
        List<IncomeSummaryDto> dtos = new ArrayList<>();
        for (Object[] row : result) {
            Integer year = (Integer) row[0];
            Integer month = (Integer) row[1];
            String category = (String) row[2];
            Double totalAmount = (Double) row[3];

            IncomeSummaryDto dto = new IncomeSummaryDto(year, month, category, totalAmount);
            dtos.add(dto);
        }
        return dtos;
    }

    public Double getTotalIncomeAmount(Integer userId){
        return incomeRepository.getTotalIncomeByUserId(userId);
    }
    public List<IncomeDto> getAllIncomePerMonth(Integer userId, Integer month, Integer year){
        List<Income> incomes = incomeRepository.getExpensesByMonthAndYear(userId, month, year);
        return incomes.stream().map(IncomeDto::new).toList();
    }
}
