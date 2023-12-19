package com.project.financialtracker.income;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeRequest {
    private Double amount;
    private Integer incomeCategoryId;
    private LocalDateTime date = LocalDateTime.now();
    private String note;
}
