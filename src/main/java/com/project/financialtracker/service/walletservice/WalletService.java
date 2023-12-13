package com.project.financialtracker.service.walletservice;

import com.project.financialtracker.model.wallet.Wallet;
import com.project.financialtracker.model.wallet.WalletDto;
import com.project.financialtracker.model.wallet.WalletRequest;
import com.project.financialtracker.repository.walletrepo.WalletRepository;
import com.project.financialtracker.utils.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Wallet> getWalletByUserId(Integer userId){
        return walletRepository.findWalletByUserId(userId);
    }

    public WalletDto addWallet(Wallet wallet){
        Wallet newWallet = walletRepository.save(wallet);
        return new WalletDto(newWallet.getWalletId(), newWallet.getName());
    }

    public WalletDto updateWallet(Integer id, WalletRequest walletRequest, Integer userId){
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if (optionalWallet.isPresent()){
            Wallet newWallet = optionalWallet.get();
            if(newWallet.getUser().getUserId().equals(userId)){
                newWallet.setName(walletRequest.getName());
                newWallet.setAmount(walletRequest.getAmount());
                Wallet wallet = walletRepository.save(newWallet);
                return new WalletDto(wallet.getWalletId(), wallet.getName());
            }
        }else{
            throw new CustomException("User with " + userId+ "not found");
        }
        return null;
    }

    public void deleteWallet(Integer id){
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            walletRepository.deleteById(id);
        }else{
            throw new CustomException("User with"+ id +" not found");
        }
    }
}
