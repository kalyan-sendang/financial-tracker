package com.project.financialtracker.wallet;

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

    public List<WalletResponse> getWalletByUserId(Integer userId){
        List<Wallet> wallets = walletRepository.findWalletByUserId(userId);
        return wallets.stream().map(wallet -> new WalletResponse(wallet.getWalletId(), wallet.getName(),wallet.getAmount())).toList();
    }

    public WalletDto addWallet(Wallet wallet){
        Wallet newWallet = walletRepository.save(wallet);
        return new WalletDto(newWallet.getWalletId(), newWallet.getName());
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
