package com.project.financialtracker.income;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    @Query(value="select * from Income where user_id = :userId", nativeQuery = true)
    List<Income> findIncomeByUserId(Integer userId);
}
