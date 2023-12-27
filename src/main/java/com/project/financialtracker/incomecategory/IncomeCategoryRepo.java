package com.project.financialtracker.incomecategory;

import com.project.financialtracker.expensecategory.ExpenseCategory;
import com.project.financialtracker.income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IncomeCategoryRepo extends JpaRepository<IncomeCategory, Integer> {
    @Query(value = "select * from income_category where user_id = :userId ",nativeQuery = true)
    List<IncomeCategory> getIncomeCategoriesByUserId(Integer userId);

    @Query(value = "select * from income_category where income_category_id = :incomeCategoryId ",nativeQuery = true)
    IncomeCategory findByIncomeCategoryId(Integer incomeCategoryId);

    @Query(value = "select * from income_category where user_id = :userId and status = true", nativeQuery = true)
    List<IncomeCategory>findIncomeCategoriesByUserIdAndStatus(Integer userId);
    @Query(value = "select * from income_category where user_id = :userId and name ILIKE '%' || :name || '%'", nativeQuery = true)
    IncomeCategory getCategoriesByUserIdAndName(Integer userId, String name);

    @Query(value = "select * from income_category where user_id = :userId and name ILIKE '%' || :name || '%' and status = true", nativeQuery = true)
    IncomeCategory getCategoriesByUserIdAndNameAndStatus(Integer userId, String name);
}
