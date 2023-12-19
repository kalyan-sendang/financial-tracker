package com.project.financialtracker.expense;

import com.project.financialtracker.expensecategory.ExpenseCategory;
import com.project.financialtracker.expensecategory.ExpenseCategoryRepo;
import com.project.financialtracker.notification.Notification;
import com.project.financialtracker.notification.NotificationRepository;
import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.wallet.Wallet;
import com.project.financialtracker.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;
    private final ExpenseCategoryRepo expenseCategoryRepo;

    private final NotificationRepository notificationRepository;

    public ExpenseService(ExpenseRepository expenseRepository, WalletRepository walletRepository, NotificationRepository notificationRepository,ExpenseCategoryRepo expenseCategoryRepo) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
        this.notificationRepository = notificationRepository;
        this.expenseCategoryRepo = expenseCategoryRepo;
    }

    public List<ExpenseDto> getAllExpense(Integer id) {
        List<Expense> expenses = expenseRepository.getExpenseByUserId(id);
        return expenses.stream().map(ExpenseDto::new).toList();
    }

    public ExpenseDto addExpense(Expense expense) {
        Expense newExpense = expenseRepository.save(expense);
        Integer walletId = expense.getWallet().getWalletId();
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            if(wallet.getAmount() < expense.getAmount())
            {
                String message = "Your Wallet amount is less than your expense";
                Notification notification = new Notification();
                notification.setAlerts(message);
                notification.setTimeStamp(LocalDateTime.now());
                notificationRepository.save(notification);
                throw new CustomException("your expense amount exceed the wallet amount");
            }
            wallet.setAmount(wallet.getAmount() - expense.getAmount());
            walletRepository.save(wallet);
        }
        Integer userId = expense.getUser().getUserId();
        Integer categoryId = expense.getExpenseCategory().getExpenseCategoryId();
        ExpenseCategory expenseCategory = expenseCategoryRepo.findByExpenseCategoryId(categoryId);
        Double maxLimit = expenseCategory.getMaxLimit();

        checkAndSendNotification(userId,categoryId,maxLimit);

        return new ExpenseDto(newExpense);
    }

    public void checkAndSendNotification(Integer userId, Integer categoryId, Double maxLimit){
        List<Expense>expenses = expenseRepository.getExpenseByUserIdAndCategoryId(userId, categoryId);
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        if(totalExpense >= maxLimit){
            String message = "Expense limit exceeded for categoryId: " + categoryId;
            Notification notification = new Notification();
            notification.setAlerts(message);
            notification.setTimeStamp(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

//    public ExpenseDto updateExpense(Integer id, ExpenseRequest updatedExpense){
//        Optional<Expense> newExpense = expenseRepository.findById(id);
//        if (newExpense.isPresent()) {
//            Expense expense = newExpense.get();
//
//            //updating existing Expense from updatedExpense
//            expense.setExpenseId(id);
//            expense.setDate(updatedExpense.getDate());
//            expense.setNote(updatedExpense.getNote());
//            expense.setAmount(updatedExpense.getAmount());
//            expense.setMaxAmount(updatedExpense.getMaxAmount());
//            expense.setCategory(updatedExpense.getCategory());
//            Expense expense1 = expenseRepository.save(expense);
//            Integer walletId = expense1.getWallet().getWalletId();
//            Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
//            if (optionalWallet.isPresent()) {
//                Wallet wallet = optionalWallet.get();
//                wallet.setAmount(wallet.getAmount() + expense.getAmount() - expense1.getAmount());
//                walletRepository.save(wallet);
//            }
//
//            return new ExpenseDto(expense1.getExpenseId(), expense1.getAmount(), expense.getCategory(), expense1.getNote(), expense1.getDate());
//        }else{
//            return null;
//        }
//    }

}


