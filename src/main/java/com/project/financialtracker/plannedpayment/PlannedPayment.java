package com.project.financialtracker.plannedpayment;

import com.project.financialtracker.expensecategory.ExpenseCategory;
import com.project.financialtracker.user.User;
import com.project.financialtracker.wallet.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plannedpayment")
public class PlannedPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "expense_category_id", nullable = false)
    private ExpenseCategory expenseCategory;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "note")
    private String note;

    @Column(name = "cron_date", nullable = false)
    private LocalDateTime date;

    PlannedPayment(PlannedPaymentReq plannedPaymentReq, User user){
        this.user = user;
        ExpenseCategory newExpenseCategory = new ExpenseCategory();
        newExpenseCategory.setExpenseCategoryId(plannedPaymentReq.getExpenseCategoryId());
        this.expenseCategory = newExpenseCategory;
        this.note = plannedPaymentReq.getNote();
        this.date = plannedPaymentReq.getDate();
        this.amount = plannedPaymentReq.getAmount();
    }

}
