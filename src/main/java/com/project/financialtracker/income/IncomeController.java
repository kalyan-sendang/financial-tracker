package com.project.financialtracker.income;

import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.ResponseWrapper;
import com.project.financialtracker.wallet.Wallet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/income")
    public ResponseEntity<ResponseWrapper<List<IncomeDto>>> getIncome(HttpServletRequest request){
        ResponseWrapper<List<IncomeDto>> response = new ResponseWrapper<>();
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            if (incomeService.getIncome(userId) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("Income retrieved successfully");
                response.setResponse(incomeService.getIncome(userId));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Income of user with "+ userId +" is not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/income/{walletId}")
    public ResponseEntity<ResponseWrapper<IncomeDto>> addIncome(@PathVariable Integer walletId, @RequestBody IncomeRequest incomeRequest, HttpServletRequest request){
        ResponseWrapper<IncomeDto> response = new ResponseWrapper<>();
        try{
            Integer id = (Integer) request.getAttribute("userId");
            User user = new User();
            Wallet wallet = new Wallet();
            wallet.setWalletId(walletId);
            user.setUserId(id);
            wallet.setWalletId(walletId);
            Income income = new Income(incomeRequest, user);
            income.setWallet(wallet);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Your wallet is credited by amount RS."+ income.getAmount());
            response.setResponse(incomeService.addIncome(income));
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
