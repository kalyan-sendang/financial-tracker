package com.project.financialtracker.expense;

import com.project.financialtracker.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query(value = "select e from Expense e where e.user.userId = :userId and (lower(e.expenseCategory.name) like lower(concat('%', :category, '%')) or lower(e.note) like lower(concat('%', :note, '%')))")
    List<Expense> getExpenseByUserId(Integer userId, String category, String note);

    @Query(value = "select * from expense where user_id = :userId and expense_category_id = :expenseCategoryId", nativeQuery = true)
    List<Expense> getExpenseByUserIdAndCategoryId(Integer userId, Integer expenseCategoryId);


    @Query("SELECT e FROM Expense e WHERE e.user.userId = :userId AND e.expenseCategory.expenseCategoryId = :expenseCategoryId AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Expense> getExpenseByUserIdAndCategoryIdAndYearAndMonth(Integer userId, Integer expenseCategoryId, Integer year, Integer month);


    @Query(value = "SELECT YEAR(e.date) as year, MONTH(e.date) as month, e.expenseCategory.name as category, SUM(e.amount) as totalAmount " + "FROM Expense e " + "WHERE e.user = :user " + "GROUP BY YEAR(e.date), MONTH(e.date), e.expenseCategory.name " + "ORDER BY YEAR(e.date) DESC, MONTH(e.date) DESC, e.expenseCategory.name")
    List<Object[]> getMonthlyExpenseSummaryByCategory(User user);

    @Query(value = "SELECT SUM(amount) FROM Expense WHERE user_id = :userId", nativeQuery = true)
    Double getTotalExpenseByUserId(Integer userId);

    @Query("SELECT e FROM Expense e " + "WHERE MONTH(e.date) = :month " + "AND e.user.userId = :userId " + "AND YEAR(e.date) = :year " + "ORDER BY e.date DESC")
    List<Expense> getExpensesByMonthAndYear(int userId, int month, int year);


//    @Query("SELECT e.expenseCategory.expenseCategoryId, SUM(e.amount) FROM Expense e " + "WHERE e.user.userId = :userId " + "GROUP BY e.expenseCategory")
//    List<Object[]> getTotalAmountPerExpenseCategoryByUserId(Integer userId);

    @Query("SELECT e.expenseCategory.expenseCategoryId, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.user.userId = :userId " +
            "AND YEAR(e.date) = YEAR(CURRENT_DATE) " +
            "AND MONTH(e.date) = MONTH(CURRENT_DATE) " +
            "GROUP BY e.expenseCategory")
    List<Object[]> getTotalAmountPerExpenseCategoryByUserId(Integer userId);



}
