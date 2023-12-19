package com.project.financialtracker.expense;

import com.project.financialtracker.wallet.Wallet;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
