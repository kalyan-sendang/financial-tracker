package com.project.financialtracker.expensecategory;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategoryDto {
    private Integer expenseCategoryId;
    private String name;
    private Double maxLimit;
}

