package com.project.financialtracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query(value = "select * from Expense where user_id = :userId", nativeQuery = true)
    List<Expense> getExpenseByUserId(Integer userId);
}
