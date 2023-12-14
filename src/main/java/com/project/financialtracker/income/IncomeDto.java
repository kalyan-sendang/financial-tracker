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
    private String category;
    private String note;
    private LocalDate date;
}
