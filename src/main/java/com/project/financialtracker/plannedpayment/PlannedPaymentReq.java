package com.project.financialtracker.plannedpayment;

import com.project.financialtracker.expense.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlannedPaymentReq {
    private LocalDateTime date;
    private Integer expenseCategoryId;
    private Double amount;
    private String note;

    public PlannedPaymentReq(Expense expense, LocalDateTime date){
        this.expenseCategoryId = expense.getExpenseCategory().getExpenseCategoryId();
        this.amount = expense.getAmount();
        this.note = expense.getNote();
        this.date = date;
    }

}
