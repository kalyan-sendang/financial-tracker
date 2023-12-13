package com.project.financialtracker.repository.expenserepo;

import com.project.financialtracker.model.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

}
