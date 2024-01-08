package com.project.financialtracker.plannedpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannedPaymentRepo extends JpaRepository<PlannedPayment, Integer> {
    @Query(value = "select * from plannedpayment where user_id = :userId and expense_id = :id", nativeQuery = true)
    PlannedPayment getPlannedPaymentByUserIdAndId(Integer userId, Integer id);

    @Query(value = "select * from plannedpayment where user_id = :userId", nativeQuery = true)
    List<PlannedPayment> getPlannedPaymentByUserId(Integer userId);

}
