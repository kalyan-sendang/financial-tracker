package com.project.financialtracker.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
    private Integer walletId;
    private String name;
    private Double amount;
}
