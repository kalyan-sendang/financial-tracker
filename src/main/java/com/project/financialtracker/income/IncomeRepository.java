package com.project.financialtracker.income;

import com.project.financialtracker.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    @Query(value="select * from Income where user_id = :userId", nativeQuery = true)
    List<Income> getIncomeByUserId(Integer userId);

    @Query(value = "SELECT YEAR(i.date) as year, MONTH(i.date) as month, i.incomeCategory.name as category, SUM(i.amount) as totalAmount " +
            "FROM Income i " +
            "WHERE i.user = :user " +
            "GROUP BY YEAR(i.date), MONTH(i.date), i.incomeCategory.name " +
            "ORDER BY YEAR(i.date) DESC, MONTH(i.date) DESC, i.incomeCategory.name")
    List<Object[]> getMonthlyIncomeSummaryByCategory(User user);
}
