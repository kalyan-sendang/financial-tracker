package com.project.financialtracker.expense;

import com.project.financialtracker.user.User;
import com.project.financialtracker.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Integer expenseId;

    @Column(name = "category")
    private String category;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "note")
    private String note;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
