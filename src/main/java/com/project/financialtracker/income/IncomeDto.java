package com.project.financialtracker.income;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDto {
    private Integer incomeId;
    private Double amount;
    private Integer categoryId;
    private String categoryName;
    private String note;
    private LocalDate date;

    public IncomeDto(Income income) {
        this.incomeId = income.getIncomeId();
        this.categoryId = income.getCategory().getCategoryId();
        this.categoryName = income.getCategory().getName();
        this.amount = income.getAmount();
        this.note = income.getNote();
        this.date = income.getDate();
    }
}
