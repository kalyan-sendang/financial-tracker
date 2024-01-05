package com.project.financialtracker.saving;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {

    @Query(value="select * from saving where user_id = :userId", nativeQuery = true)
    List<Saving> findSavingByUserId(Integer userId);

    @Query(value = "select * from saving where user_id = :userId and goal ILIKE '%' || :goal || '%' and status = true", nativeQuery = true)
    Saving findSavingByIdAndNameAndStatus(Integer userId, String goal);

    @Query(value = "select * from saving where user_id = :userId and saving_id = :savingId and status = true", nativeQuery = true)
    Saving findSavingByIdAndUserId(Integer userId, Integer savingId);

    @Query(value = "SELECT SUM(amount) FROM saving WHERE user_id = :userId", nativeQuery = true)
    Double getTotalSavingByUserId(Integer userId);
}
