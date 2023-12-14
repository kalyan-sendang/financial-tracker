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
    private Double amount;
    private String category;
    private String note;
    private LocalDate date;
}
