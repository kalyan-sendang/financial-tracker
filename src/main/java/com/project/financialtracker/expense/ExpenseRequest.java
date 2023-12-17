package com.project.financialtracker.expense;

import com.project.financialtracker.wallet.Wallet;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    private Integer categoryId;
    private Double amount;
    private String note;
    private LocalDate date;
}
