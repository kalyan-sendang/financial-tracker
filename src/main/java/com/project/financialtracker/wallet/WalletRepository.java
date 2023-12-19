package com.project.financialtracker.wallet;

import com.project.financialtracker.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query(value = "select * from Wallet  where user_id = :userId", nativeQuery= true)
    Wallet findWalletByUserId(Integer userId);
}
