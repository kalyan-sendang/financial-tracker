package com.project.financialtracker.wallet;

import com.project.financialtracker.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Integer walletId;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
