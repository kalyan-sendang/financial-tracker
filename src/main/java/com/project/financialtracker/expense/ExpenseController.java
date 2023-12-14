package com.project.financialtracker.expense;

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
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense")
    public ResponseEntity<ResponseWrapper<List<ExpenseDto>>> getAllExpense(HttpServletRequest request) {
        ResponseWrapper<List<ExpenseDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            if (expenseService.getAllExpense(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense retrieved successfully");
                response.setSuccess(true);
                response.setResponse(expenseService.getAllExpense(id));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Expenses not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/expense/{walletId}")
    public ResponseEntity<ResponseWrapper<ExpenseDto>> addExpenses(@PathVariable Integer walletId,@RequestBody ExpenseRequest expenseRequest, HttpServletRequest request){
        ResponseWrapper<ExpenseDto> response = new ResponseWrapper<>();
        try{
            Integer id = (Integer) request.getAttribute("userId");
            Expense expense = new Expense();
            User user = new User();
            Wallet wallet = new Wallet();
            user.setUserId(id);
            wallet.setWalletId(walletId);
            expense.setAmount(expenseRequest.getAmount());
            expense.setCategory(expenseRequest.getCategory());
            expense.setNote(expenseRequest.getNote());
            expense.setDate(expenseRequest.getDate());
            expense.setUser(user);
            expense.setWallet(wallet);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Your wallet is credited by amount RS."+ expense.getAmount());
            response.setResponse(expenseService.addExpense(expense));
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
