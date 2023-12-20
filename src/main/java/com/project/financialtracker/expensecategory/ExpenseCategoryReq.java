package com.project.financialtracker.expensecategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategoryReq{

    private String name;
    private Double maxLimit;
}
