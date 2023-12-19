package com.project.financialtracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query(value = "select * from expense where user_id = :userId", nativeQuery = true)
    List<Expense> getExpenseByUserId(Integer userId);

    @Query(value = "select * from expense where user_id = :userId and expense_category_id = :expenseCategoryId", nativeQuery = true)
    List<Expense> getExpenseByUserIdAndCategoryId(Integer userId, Integer expenseCategoryId);
}
