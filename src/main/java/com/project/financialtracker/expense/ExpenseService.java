package com.project.financialtracker.expense;

import com.project.financialtracker.wallet.Wallet;
import com.project.financialtracker.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;

    public ExpenseService(ExpenseRepository expenseRepository, WalletRepository walletRepository) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
    }

    public List<ExpenseDto> getAllExpense(Integer id) {
        List<Expense> expenses = expenseRepository.getExpenseByUserId(id);
        return expenses.stream().map(expense -> new ExpenseDto(expense.getExpenseId(), expense.getAmount(), expense.getCategory(), expense.getNote(), expense.getDate())).toList();
    }

    public ExpenseDto addExpense(Expense expense) {
        Expense newExpense = expenseRepository.save(expense);
        Integer walletId = expense.getWallet().getWalletId();
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            wallet.setAmount(wallet.getAmount() - expense.getAmount());
            walletRepository.save(wallet);
        }
        return new ExpenseDto(newExpense.getExpenseId(), newExpense.getAmount(), newExpense.getCategory(), newExpense.getNote(), expense.getDate());
    }

}


