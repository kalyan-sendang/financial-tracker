package com.project.financialtracker.saving;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SavingRequest {
    private String goal;
    private Double amount;
    private Double goalAmount;
    private LocalDateTime date = LocalDateTime.now();
}
