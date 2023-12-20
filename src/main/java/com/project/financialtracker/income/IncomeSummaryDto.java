package com.project.financialtracker.income;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeSummaryDto {

    private Integer year;
    private Integer month;
    private String incomeCategoryName;
    private Double totalAmount;
}