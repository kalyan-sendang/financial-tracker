package com.project.financialtracker.transaction;

import com.project.financialtracker.expense.Expense;
import com.project.financialtracker.income.Income;
import com.project.financialtracker.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "expenseId", nullable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "incomeId", nullable = false)
    private Income income;

}
