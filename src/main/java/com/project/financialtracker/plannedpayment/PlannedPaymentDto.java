package com.project.financialtracker.plannedpayment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlannedPaymentDto {
    private Integer id;
    private LocalDateTime date;
    private Integer plannedPaymentCategoryId;
    private String plannedPaymentCategoryName;
    private Double amount;
    private String note;
    
    public PlannedPaymentDto(PlannedPayment plannedPayment){
        this.id = plannedPayment.getId();
        this.plannedPaymentCategoryId = plannedPayment.getExpenseCategory().getExpenseCategoryId();
        this.plannedPaymentCategoryName = plannedPayment.getExpenseCategory().getName();
        this.amount = plannedPayment.getAmount();
        this.note = plannedPayment.getNote();
        this.date = plannedPayment.getDate();
    }

}
