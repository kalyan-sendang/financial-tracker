package com.project.financialtracker.incomecategory;

import com.project.financialtracker.expensecategory.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IncomeCategoryRepo extends JpaRepository<IncomeCategory, Integer> {
    @Query(value = "select * from income_category where user_id = :userId ",nativeQuery = true)
    List<IncomeCategory> getExpenseCategoriesByUserId(Integer userId);

    @Query(value = "select * from income_category where expense_category_id = :incomeCategoryId ",nativeQuery = true)
    IncomeCategory findByExpenseCategoryId(Integer incomeCategoryId);


    @Query(value = "select * from income_category where user_id = :userId and name ILIKE '%' || :name || '%'", nativeQuery = true)
    List<IncomeCategory> getCategoriesByUserIdAndName(Integer userId, String name);
}
