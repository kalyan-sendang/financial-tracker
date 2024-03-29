package com.project.financialtracker.wallet;

import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api")
@Tag(name = "Wallet Controller", description = "This is Wallet api for wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    static final String ID = "userId";
    @GetMapping("/wallet")
    public ResponseEntity<ResponseWrapper<WalletResponse>> getWalletByUserId(HttpServletRequest request){
        ResponseWrapper<WalletResponse> response = new ResponseWrapper<>();
        try {
            Integer userId = (Integer) request.getAttribute(ID);
            if (walletService.getWalletByUserId(userId) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("Wallet retrieved successfully");
                response.setResponse(walletService.getWalletByUserId(userId));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Wallet of user with "+ userId +" is not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/wallet")
    public  ResponseEntity<ResponseWrapper<WalletDto>> addWallet(@RequestBody WalletRequest walletRequest, HttpServletRequest request){
        ResponseWrapper<WalletDto> response = new ResponseWrapper<>();
        try {
            Integer userId = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Your Wallet added successfully");
            response.setResponse(walletService.addWallet(walletRequest , userId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/wallet/{walletId}")
    public ResponseEntity<ResponseWrapper<WalletDto>> updateWallet(@PathVariable Integer walletId, @RequestBody String name, HttpServletRequest request){
        try {
            Integer userId = (Integer) request.getAttribute(ID);
            ResponseWrapper<WalletDto> response = new ResponseWrapper<>();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("Wallet updated successfully");
            response.setSuccess(true);
            response.setResponse(walletService.updateWallet(walletId, name, userId));
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @DeleteMapping("/wallet/{walletId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteWallet(@PathVariable Integer walletId){
        try{
            walletService.deleteWallet(walletId);
            ResponseWrapper<Void> response = new ResponseWrapper<>();
            response.setMessage("Wallet with Id "+ walletId+" deleted successfully");
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }
}
