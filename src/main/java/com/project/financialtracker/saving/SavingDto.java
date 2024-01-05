package com.project.financialtracker.saving;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingDto {
    private Integer savingId;
    private String goal;
    private Double amount;
    private Double goalAmount;
}
