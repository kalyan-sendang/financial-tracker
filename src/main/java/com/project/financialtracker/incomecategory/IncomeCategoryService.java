package com.project.financialtracker.incomecategory;

import com.project.financialtracker.expensecategory.ExpenseCategory;
import com.project.financialtracker.expensecategory.ExpenseCategoryDto;
import com.project.financialtracker.expensecategory.ExpenseCategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeCategoryService {
    private final IncomeCategoryRepo incomeCategoryRepo;

    public IncomeCategoryService(IncomeCategoryRepo incomeCategoryRepo) {
        this.incomeCategoryRepo = incomeCategoryRepo;
    }

    public List<IncomeCategoryDto> getAllCategory(Integer id){
        List<IncomeCategory>categories = incomeCategoryRepo.getExpenseCategoriesByUserId(id);
        return categories.stream().map(category -> new IncomeCategoryDto(category.getIncomeCategoryId(), category.getName())).toList();
    }

    public IncomeCategoryDto addCategory( IncomeCategory category){
        IncomeCategory newCategory = incomeCategoryRepo.save(category);
        return new IncomeCategoryDto(newCategory.getIncomeCategoryId(),newCategory.getName());
    }

    public IncomeCategoryDto updateCategory(Integer id){
        Optional<IncomeCategory> newCategory = incomeCategoryRepo.findById(id);
        if (newCategory.isPresent()) {
            IncomeCategory category1 = newCategory.get();
            category1.setIncomeCategoryId(id);
            category1.setName(category1.getName());
            return new IncomeCategoryDto(category1.getIncomeCategoryId(), category1.getName());
        }else{
            return null;
        }
    }
}
