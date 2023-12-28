package com.project.financialtracker.incomecategory;

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
@Table(name = "income_category")
public class IncomeCategory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_category_id")
    private Integer incomeCategoryId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private Boolean status = true;

    IncomeCategory(IncomeCategoryReq incomeCategoryReq, User user){
        this.name = incomeCategoryReq.getName();
        this.user = user;
    }

}
