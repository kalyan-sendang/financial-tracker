package com.project.financialtracker.expensecategory;

import com.project.financialtracker.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expense_category")
public class ExpenseCategory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_category_id")
    private Integer expenseCategoryId;

    @Column(name = "name",  unique = true)
    private String name;

    @Column(name = "max_limit")
    private Double maxLimit;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    ExpenseCategory(ExpenseCategoryReq expenseCategoryReq, User user){
        this.name = expenseCategoryReq.getName();
        this.maxLimit = expenseCategoryReq.getMaxLimit();
        this.user = user;
    }

}
