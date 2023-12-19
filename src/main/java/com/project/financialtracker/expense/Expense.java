package com.project.financialtracker.expense;

import com.project.financialtracker.expensecategory.ExpenseCategory;
import com.project.financialtracker.user.User;
import com.project.financialtracker.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Integer expenseId;

    @ManyToOne
    @JoinColumn(name = "expense_category_id", nullable = false)
    private ExpenseCategory expenseCategory;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "note")
    private String note;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Expense(ExpenseRequest expenseRequest,User user) {
        this.user = user;
        ExpenseCategory newExpenseCategory = new ExpenseCategory();
        newExpenseCategory.setExpenseCategoryId(expenseRequest.getExpenseCategoryId());
        this.expenseCategory = newExpenseCategory;
        this.note = expenseRequest.getNote();
        this.date = expenseRequest.getDate();
        this.amount = expenseRequest.getAmount();
    }
}
