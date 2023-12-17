package com.project.financialtracker.expense;

import com.project.financialtracker.category.Category;
import com.project.financialtracker.user.User;
import com.project.financialtracker.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

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

    public Expense(ExpenseRequest expenseRequest,User user) {
        this.user = user;
        Category newCategory = new Category();
        newCategory.setCategoryId(expenseRequest.getCategoryId());
        this.category = newCategory;
        this.note = expenseRequest.getNote();
        this.date = expenseRequest.getDate();
        this.amount = expenseRequest.getAmount();
    }
}
