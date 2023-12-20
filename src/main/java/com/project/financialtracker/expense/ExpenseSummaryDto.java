package com.project.financialtracker.expense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSummaryDto {
    private Integer year;
    private Integer month;
    private String expenseCategoryName;
    private Double totalAmount;
}
