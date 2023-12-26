package com.project.financialtracker.expense;

import com.project.financialtracker.expensecategory.ExpenseCategory;
import com.project.financialtracker.expensecategory.ExpenseCategoryRepo;
import com.project.financialtracker.notification.Notification;
import com.project.financialtracker.notification.NotificationRepository;
import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.utils.NewCustomException;
import com.project.financialtracker.wallet.Wallet;
import com.project.financialtracker.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

//    public List<ExpenseDto> getAllExpensePerMonth(Integer month, Integer year) {
//        List<Expense> expenses = expenseRepository.getExpensesByMonthAndYear(month, year);
//        return expenses.stream().map(ExpenseDto::new).toList();
//    }

    public ExpenseDto addExpense(Expense expense) {
        Integer walletId = expense.getWallet().getWalletId();
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        Integer userId = expense.getUser().getUserId();
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            if(wallet.getAmount() < expense.getAmount())
            {
                String message = "Your Wallet amount is less than your expense";
                Notification notification = new Notification();
                User user = new User();
                user.setUserId(userId);
                notification.setUser(user);
                notification.setAlerts(message);
                notification.setTimeStamp(LocalDateTime.now());
                notificationRepository.save(notification);
                throw new CustomException("your expense amount exceed the wallet amount");
            }
            Integer categoryId = expense.getExpenseCategory().getExpenseCategoryId();
            ExpenseCategory expenseCategory = expenseCategoryRepo.findByExpenseCategoryId(categoryId);
            Double maxLimit = expenseCategory.getMaxLimit();
            List<Expense>expenses = expenseRepository.getExpenseByUserIdAndCategoryId(userId, categoryId);
            double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
            String categoryName = expenseCategory.getName();
            if(totalExpense >= maxLimit) {
                checkAndSendNotification(categoryName, userId);
                throw new NewCustomException("Your Expense exceeds the maximum expense limit for "+ categoryName);
            }
            wallet.setAmount(wallet.getAmount() - expense.getAmount());
            walletRepository.save(wallet);

        }

        Expense newExpense = expenseRepository.save(expense);
        return new ExpenseDto(newExpense);
    }

    public void checkAndSendNotification(String categoryName, Integer userId){
            String message = "Expense limit exceeded for " + categoryName + "category.";
            User user = new User();
            user.setUserId(userId);
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setAlerts(message);
            notification.setTimeStamp(LocalDateTime.now());
            notificationRepository.save(notification);
    }

    public List<ExpenseSummaryDto> getData(Integer id){
        User user = new User();
        user.setUserId(id);
        List<Object[]> result = expenseRepository.getMonthlyExpenseSummaryByCategory(user);
        List<ExpenseSummaryDto> dtos = new ArrayList<>();
        for (Object[] row : result) {
            Integer year = (Integer) row[0];
            Integer month = (Integer) row[1];
            String category = (String) row[2];
            Double totalAmount = (Double) row[3];

            ExpenseSummaryDto dto = new ExpenseSummaryDto(year, month, category, totalAmount);
            dtos.add(dto);
        }

        return dtos;
    }

    public Double getTotalExpenseAmount(Integer userId){
        return expenseRepository.getTotalExpenseByUserId(userId);
    }

    public Double getTotalCategoryAmount(Integer userId ,Integer expenseCategoryId){

        Double expenseAmount = expenseRepository.getTotalExpenseByCategoryIdAndUserId(userId,expenseCategoryId);
        if(expenseAmount == null){
            return null;
        }
        ExpenseCategory expenseCategory = expenseCategoryRepo.findByExpenseCategoryId(expenseCategoryId);
        Double maxLimit = expenseCategory.getMaxLimit();
        String categoryName = expenseCategory.getName();
        if(expenseAmount >= maxLimit) {
            checkAndSendNotification(categoryName, userId);
        }
        return expenseAmount;

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


