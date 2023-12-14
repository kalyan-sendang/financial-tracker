package com.project.financialtracker.income;

import com.project.financialtracker.wallet.Wallet;
import com.project.financialtracker.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final WalletRepository walletRepository;

    public IncomeService(IncomeRepository incomeRepository, WalletRepository walletRepository) {
        this.incomeRepository = incomeRepository;
        this.walletRepository = walletRepository;
    }

    public List<IncomeDto> getIncome(Integer userId){
        List<Income> incomeList = incomeRepository.findIncomeByUserId(userId);
        return incomeList.stream().map(income -> new IncomeDto(income.getIncomeId(), income.getAmount(), income.getCategory(), income.getNote(),income.getDate())).toList();
    }

    public IncomeDto addIncome(Income income){
        Income newIncome = incomeRepository.save(income);
        Integer walletId = newIncome.getWallet().getWalletId();
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            wallet.setAmount(wallet.getAmount() + income.getAmount());
            walletRepository.save(wallet);
        }
        return new IncomeDto(newIncome.getIncomeId(), newIncome.getAmount(), newIncome.getCategory(), newIncome.getNote(), newIncome.getDate());
    }
}
