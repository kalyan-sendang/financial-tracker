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
public class IncomeRequest {
    private Double amount;
    private String category;
    private LocalDate date;
    private String note;
}
