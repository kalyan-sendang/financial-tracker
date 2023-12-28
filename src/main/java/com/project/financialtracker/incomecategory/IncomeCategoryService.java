package com.project.financialtracker.incomecategory;

import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.CustomException;
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
        List<IncomeCategory>categories = incomeCategoryRepo.findIncomeCategoriesByUserIdAndStatus(id);
        return categories.stream().map(category -> new IncomeCategoryDto(category.getIncomeCategoryId(), category.getName())).toList();
    }

    public IncomeCategoryDto addCategory( IncomeCategoryReq incomeCategoryReq, Integer id){
        String name = incomeCategoryReq.getName().trim();
        IncomeCategory existingCategory = incomeCategoryRepo.getCategoriesByUserIdAndNameAndStatus(id, name);
        if (existingCategory != null) {
            throw new CustomException("Category is already present");
        } else {
            User user = new User();
            user.setUserId(id);
            IncomeCategory incomeCategory = new IncomeCategory(incomeCategoryReq, user);
            IncomeCategory newCategory = incomeCategoryRepo.save(incomeCategory);
            return new IncomeCategoryDto(newCategory.getIncomeCategoryId(), newCategory.getName());
        }

    }

    public List<IncomeCategoryDto> deleteCategory(Integer id, Integer userId){
        Optional<IncomeCategory> newCategory = incomeCategoryRepo.findById(id);
        if(newCategory.isPresent()){
            IncomeCategory incomeCategory = newCategory.get();
            incomeCategory.setIncomeCategoryId(id);
            incomeCategory.setStatus(false);
            incomeCategoryRepo.save(incomeCategory);
            List<IncomeCategory> incomeCategories = incomeCategoryRepo.findIncomeCategoriesByUserIdAndStatus(userId);
            return incomeCategories.stream().map(incomeCategory1 -> new IncomeCategoryDto(incomeCategory1.getIncomeCategoryId(), incomeCategory1.getName())).toList();
        }else{
            throw new CustomException("Expense Category is not found");
        }
    }
}
