package com.project.financialtracker.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String name;
    private Double maxLimit;
}
