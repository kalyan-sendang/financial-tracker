package com.project.financialtracker.expense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Integer expenseId;
    private Integer expenseCategoryId;
    private String expenseCategoryName;
    private Double amount;
    private String note;
    private LocalDateTime date;

    public ExpenseDto(Expense expense) {
        this.expenseId = expense.getExpenseId();
        this.expenseCategoryId = expense.getExpenseCategory().getExpenseCategoryId();
        this.expenseCategoryName = expense.getExpenseCategory().getName();
        this.amount = expense.getAmount();
        this.note = expense.getNote();
        this.date = expense.getDate();
    }
}
