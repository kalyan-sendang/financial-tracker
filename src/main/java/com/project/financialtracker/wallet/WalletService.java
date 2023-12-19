package com.project.financialtracker.wallet;

import com.project.financialtracker.user.User;
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

    public WalletResponse getWalletByUserId(Integer userId){
        Wallet wallet = walletRepository.findWalletByUserId(userId);
        return  new WalletResponse(wallet.getWalletId(), wallet.getName(),wallet.getAmount());
    }

    public WalletDto addWallet(WalletRequest walletRequest, Integer id){
        Wallet existingWallet = walletRepository.findWalletByUserId(id);
        if(existingWallet != null){
            throw new CustomException("User already has a wallet");
        }else {
            Wallet wallet = new Wallet();
            User user = new User();
            user.setUserId(id);
            wallet.setName(walletRequest.getName());
            wallet.setAmount(walletRequest.getAmount());
            wallet.setUser(user);
            Wallet newWallet = walletRepository.save(wallet);
            return new WalletDto(newWallet.getWalletId(), newWallet.getName());
        }
    }

    public WalletDto updateWallet(Integer id, String name, Integer userId){
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if (optionalWallet.isPresent()){
            Wallet newWallet = optionalWallet.get();
            if(newWallet.getUser().getUserId().equals(userId)){
                newWallet.setName(name);
                Wallet wallet = walletRepository.save(newWallet);
                return new WalletDto(wallet.getWalletId(), wallet.getName());
            }else{
                throw new CustomException("User with " + userId+ " not found");
            }
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
