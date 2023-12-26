package com.project.financialtracker.expensecategory;

import com.project.financialtracker.category.Category;
import com.project.financialtracker.incomecategory.IncomeCategory;
import com.project.financialtracker.incomecategory.IncomeCategoryDto;
import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseCategoryService {
    private final ExpenseCategoryRepo expenseCategoryRepo;

    public ExpenseCategoryService(ExpenseCategoryRepo expenseCategoryRepo) {
        this.expenseCategoryRepo = expenseCategoryRepo;
    }

    public List<ExpenseCategoryDto> getAllCategory(Integer id) {
        List<ExpenseCategory> categories = expenseCategoryRepo.getExpenseCategoriesByUserIdAndStatus(id);
        return categories.stream().map(category -> new ExpenseCategoryDto(category.getExpenseCategoryId(), category.getName(), category.getMaxLimit())).toList();
    }

    public ExpenseCategoryDto getACategory(Integer userId, Integer id) {
        ExpenseCategory expenseCategory = expenseCategoryRepo.getExpenseCategoryByUserIdAndStatusAAndExpenseCategoryId(userId,id);
        return new ExpenseCategoryDto(expenseCategory.getExpenseCategoryId(), expenseCategory.getName(), expenseCategory.getMaxLimit());
    }

    public ExpenseCategoryDto addCategory(ExpenseCategoryReq expenseCategoryReq, Integer id) {
        String name = expenseCategoryReq.getName().trim();
        ExpenseCategory existingCategory = expenseCategoryRepo.getCategoriesByUserIdAndNameAndStatus(id, name);
        if (existingCategory != null) {
            throw new CustomException("Category is already present");
        } else {
            User user = new User();
            user.setUserId(id);
            ExpenseCategory expenseCategory = new ExpenseCategory(expenseCategoryReq, user);
            ExpenseCategory newCategory = expenseCategoryRepo.save(expenseCategory);
            return new ExpenseCategoryDto(newCategory.getExpenseCategoryId(), newCategory.getName(), newCategory.getMaxLimit());
        }

    }

    public ExpenseCategoryDto updateCategory(Integer id, ExpenseCategoryReq expenseCategoryReq) {
        Optional<ExpenseCategory> newCategory = expenseCategoryRepo.findById(id);
        if (newCategory.isPresent()) {
            ExpenseCategory category1 = newCategory.get();
            category1.setExpenseCategoryId(id);
            category1.setMaxLimit(expenseCategoryReq.getMaxLimit());
            category1.setName(expenseCategoryReq.getName());
            expenseCategoryRepo.save(category1);
            return new ExpenseCategoryDto(category1.getExpenseCategoryId(), category1.getName(), category1.getMaxLimit());
        } else {
            throw new CustomException("Expense Category is not found");
        }
    }

    public List<ExpenseCategoryDto> deleteCategory(Integer id, Integer userId) {
        Optional<ExpenseCategory> newCategory = expenseCategoryRepo.findById(id);
        if (newCategory.isPresent()) {
            ExpenseCategory expenseCategory = newCategory.get();
            expenseCategory.setExpenseCategoryId(id);
            expenseCategory.setStatus(false);
            expenseCategoryRepo.save(expenseCategory);
            List<ExpenseCategory> expenseCategories = expenseCategoryRepo.getExpenseCategoriesByUserIdAndStatus(userId);
            return expenseCategories.stream().map(expenseCategory1 -> new ExpenseCategoryDto(expenseCategory1.getExpenseCategoryId(), expenseCategory1.getName(), expenseCategory1.getMaxLimit())).toList();
        } else {
            throw new CustomException("Expense Category is not found");
        }
    }
}
