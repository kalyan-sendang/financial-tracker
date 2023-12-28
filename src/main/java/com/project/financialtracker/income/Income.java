package com.project.financialtracker.income;

import com.project.financialtracker.incomecategory.IncomeCategory;
import com.project.financialtracker.user.User;
import com.project.financialtracker.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private Integer incomeId;

    @ManyToOne
    @JoinColumn(name = "income_category_id", nullable = false)
    private IncomeCategory incomeCategory;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "walletId", nullable = false)
    private Wallet wallet;


    public Income(IncomeRequest incomeRequest, User user) {
        this.user = user;
        IncomeCategory newIncomeCategory = new IncomeCategory();
        newIncomeCategory.setIncomeCategoryId(incomeRequest.getIncomeCategoryId());
        this.incomeCategory = newIncomeCategory;
        this.note = incomeRequest.getNote();
        this.date = incomeRequest.getDate();
        this.amount = incomeRequest.getAmount();
    }


}
