package com.project.financialtracker.expensecategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseCategoryRepo extends JpaRepository<ExpenseCategory, Integer> {
    @Query(value = "select * from expense_category where user_id = :userId ",nativeQuery = true)
    List<ExpenseCategory> getExpenseCategoriesByUserId(Integer userId);

    @Query(value = "select * from expense_category where name ILIKE '%' || :name || '%'",nativeQuery = true)
    ExpenseCategory getExpenseCategoryByName(String name);


    @Query(value = "select * from expense_category where expense_category_id = :expenseCategoryId ",nativeQuery = true)
    ExpenseCategory findByExpenseCategoryId(Integer expenseCategoryId);


    @Query(value = "select * from expense_category where user_id = :userId and name ILIKE '%' || :name || '%'", nativeQuery = true)
    List<ExpenseCategory> getCategoriesByUserIdAndName(Integer userId, String name);
}
