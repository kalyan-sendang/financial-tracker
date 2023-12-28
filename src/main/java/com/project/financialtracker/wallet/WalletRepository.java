package com.project.financialtracker.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query(value = "select * from Wallet  where user_id = :userId", nativeQuery= true)
    Wallet findWalletByUserId(Integer userId);
}
