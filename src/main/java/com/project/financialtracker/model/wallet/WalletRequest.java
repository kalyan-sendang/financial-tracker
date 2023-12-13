package com.project.financialtracker.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequest {
    private String name;
    private Double amount;
}
