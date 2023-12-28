package com.project.financialtracker.expense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    private Integer expenseCategoryId;
    private Double amount;
    private String note;
    private LocalDateTime date = LocalDateTime.now();
}
