package com.project.financialtracker.expensecategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseCategoryRepo extends JpaRepository<ExpenseCategory, Integer> {

    @Query(value = "select * from expense_category where user_id = :userId and status = true",nativeQuery = true)
    List<ExpenseCategory> getExpenseCategoriesByUserIdAndStatus(Integer userId);

    @Query(value = "select * from expense_category where user_id = :userId and status = true and expense_category_id = :id",nativeQuery = true)
    ExpenseCategory getExpenseCategoryByUserIdAndStatusAAndExpenseCategoryId(Integer userId, Integer id);


    @Query(value = "select * from expense_category where expense_category_id = :expenseCategoryId ",nativeQuery = true)
    ExpenseCategory findByExpenseCategoryId(Integer expenseCategoryId);


    @Query(value = "select * from expense_category where user_id = :userId and name ILIKE '%' || :name || '%' and status = true", nativeQuery = true)
    ExpenseCategory getCategoriesByUserIdAndNameAndStatus(Integer userId, String name);


}
