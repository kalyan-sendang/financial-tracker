package com.project.financialtracker.expense;

import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.utils.ResponseWrapper;
import com.project.financialtracker.utils.NewCustomException;
import com.project.financialtracker.wallet.Wallet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    static final String ERROR = "Internal Server Error";

    final Integer YEAR = 2023;

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<ExpenseDto>>> getAllExpense(@RequestParam(name = "query", defaultValue = "", required = false) String category,
                                                                           @RequestParam(name = "query", defaultValue = "", required = false) String note,
                                                                           HttpServletRequest request) {
        ResponseWrapper<List<ExpenseDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            if (expenseService.getAllExpense(id, category, note) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense retrieved successfully");
                response.setSuccess(true);
                response.setResponse(expenseService.getAllExpense(id, category,note));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Expenses not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{walletId}")
    public ResponseEntity<ResponseWrapper<ExpenseDto>> addExpenses(@PathVariable Integer walletId, @RequestBody ExpenseRequest expenseRequest, HttpServletRequest request) {
        ResponseWrapper<ExpenseDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            User user = new User();
            Wallet wallet = new Wallet();
            wallet.setWalletId(walletId);
            user.setUserId(id);
            Expense expense = new Expense(expenseRequest, user);
            expense.setWallet(wallet);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Your wallet is debited by amount RS." + expense.getAmount());
            response.setResponse(expenseService.addExpense(expense));
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NewCustomException ex) {
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/expenseData")
    public ResponseEntity<ResponseWrapper<List<ExpenseSummaryDto>>> getData(HttpServletRequest request) {
        ResponseWrapper<List<ExpenseSummaryDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Data retrieved Successfully");
            response.setResponse(expenseService.getData(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/totalExpense")
    public ResponseEntity<ResponseWrapper<Double>> getTotalAmount(HttpServletRequest request) {
        ResponseWrapper<Double> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Data retrieved Successfully");
            Double totalAmount = expenseService.getTotalExpenseAmount(id);
            response.setResponse(totalAmount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/expense-per-cat")
    public ResponseEntity<ResponseWrapper<Map<Integer, Double>>> getTotalCategoryAmount(HttpServletRequest request) {
        ResponseWrapper<Map<Integer, Double>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");

            Map<Integer, Double> expenseList = expenseService.getTotalCategoryAmount(id);
//
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Data retrieved Successfully");
            response.setResponse(expenseList);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/expense-per-month/{month}")
    public ResponseEntity<ResponseWrapper<List<ExpenseDto>>> getAllExpense(@PathVariable Integer month, HttpServletRequest request ) {
        ResponseWrapper<List<ExpenseDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            if (expenseService.getAllExpensePerMonth(id, month, YEAR) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense retrieved successfully");
                response.setSuccess(true);
                response.setResponse(expenseService.getAllExpensePerMonth(id, month, YEAR));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Expenses not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


//    @PutMapping("/expense/{expenseId}")
//    public ResponseEntity<ResponseWrapper<ExpenseDto>> updateExpense(@PathVariable Integer expenseId, @RequestBody ExpenseRequest expenseRequest) {
//        ResponseWrapper<ExpenseDto> response = new ResponseWrapper<>();
//        try {
//            if (expenseService.updateExpense(expenseId, expenseRequest) != null) {
//                response.setStatusCode(HttpStatus.CREATED.value());
//                response.setMessage("Your wallet is credited by amount Rs." + expenseRequest.getAmount());
//                response.setSuccess(true);
//                response.setResponse(expenseService.updateExpense(expenseId, expenseRequest));
//                return ResponseEntity.ok(response);
//            }else{
//                response.setStatusCode(HttpStatus.NOT_FOUND.value());
//                response.setMessage("Expense not found");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//        } catch (Exception e) {
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setMessage(ERROR);
//            response.setSuccess(false);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
}
