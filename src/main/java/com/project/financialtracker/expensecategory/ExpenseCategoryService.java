package com.project.financialtracker.expensecategory;

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

    public List<ExpenseCategoryDto> getAllCategory(Integer id){
        List<ExpenseCategory>categories = expenseCategoryRepo.getExpenseCategoriesByUserId(id);
        return categories.stream().map(category -> new ExpenseCategoryDto(category.getExpenseCategoryId(), category.getName(), category.getMaxLimit())).toList();
    }

    public ExpenseCategoryDto addCategory( ExpenseCategoryReq expenseCategoryReq, Integer id){
        String name = expenseCategoryReq.getName().trim();
        ExpenseCategory existingCategory = expenseCategoryRepo.getExpenseCategoryByName(name);
        if(existingCategory != null){
           throw new CustomException("Category is already present");
        }else{
            User user = new User();
            user.setUserId(id);
            ExpenseCategory expenseCategory = new ExpenseCategory(expenseCategoryReq, user);
            System.out.println("asfasfasf");
            ExpenseCategory newCategory = expenseCategoryRepo.save(expenseCategory);
            return new ExpenseCategoryDto(newCategory.getExpenseCategoryId(),newCategory.getName(), newCategory.getMaxLimit());
        }

    }

    public ExpenseCategoryDto updateCategory(Integer id, Double maxLimit){
        Optional<ExpenseCategory> newCategory = expenseCategoryRepo.findById(id);
        if (newCategory.isPresent()) {
            ExpenseCategory category1 = newCategory.get();
            category1.setExpenseCategoryId(id);
            category1.setMaxLimit(maxLimit);
            category1.setName(category1.getName());
            return new ExpenseCategoryDto(category1.getExpenseCategoryId(), category1.getName(), category1.getMaxLimit());
        }else{
            return null;
        }
    }
}
