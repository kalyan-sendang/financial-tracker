package com.project.financialtracker.expense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Integer expenseId;
    private Integer categoryId;
    private String categoryName;
    private Double amount;
    private String note;
    private LocalDate date;

    public ExpenseDto(Expense expense) {
        this.expenseId = expense.getExpenseId();
        this.categoryId = expense.getCategory().getCategoryId();
        this.categoryName = expense.getCategory().getName();
        this.amount = expense.getAmount();
        this.note = expense.getNote();
        this.date = expense.getDate();
    }
}
