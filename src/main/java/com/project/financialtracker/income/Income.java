package com.project.financialtracker.income;

import com.project.financialtracker.expense.Expense;
import com.project.financialtracker.user.User;
import com.project.financialtracker.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private Integer incomeId;

    @Column(name = "category")
    private String category;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "walletId", nullable = false)
    private Wallet wallet;

}
