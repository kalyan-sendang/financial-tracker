package com.project.financialtracker.model.expense;

import com.project.financialtracker.model.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="expense_id")
    private Integer expenseId;

    @Column(name= "category")
    private String category;

    @Column(name= "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "note")
    private String note;

}
