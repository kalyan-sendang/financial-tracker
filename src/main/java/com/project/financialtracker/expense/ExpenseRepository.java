package com.project.financialtracker.expense;

import com.project.financialtracker.user.User;
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

    @Query(value = "SELECT YEAR(e.date) as year, MONTH(e.date) as month, e.expenseCategory.name as category, SUM(e.amount) as totalAmount " +
            "FROM Expense e " +
            "WHERE e.user = :user " +
            "GROUP BY YEAR(e.date), MONTH(e.date), e.expenseCategory.name " +
            "ORDER BY YEAR(e.date) DESC, MONTH(e.date) DESC, e.expenseCategory.name")
    List<Object[]> getMonthlyExpenseSummaryByCategory(User user);
}
