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
@RequestMapping("/api/income")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    static final Integer YEAR = 2023;
    static final String ERROR = "Internal Server Error";

    static final String ID = "userId";
    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<IncomeDto>>> getIncome(@RequestParam(name = "query", defaultValue = "", required = false) String category,
                                                                      @RequestParam(name = "query", defaultValue = "", required = false) String note,
                                                                      HttpServletRequest request){
        ResponseWrapper<List<IncomeDto>> response = new ResponseWrapper<>();
        try {
            Integer userId = (Integer) request.getAttribute(ID);
            if (incomeService.getIncome(userId, category, note) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("Income retrieved successfully");
                response.setResponse(incomeService.getIncome(userId, category, note));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Income of user with "+ userId +" is not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/{walletId}")
    public ResponseEntity<ResponseWrapper<IncomeDto>> addIncome(@PathVariable Integer walletId, @RequestBody IncomeRequest incomeRequest, HttpServletRequest request){
        ResponseWrapper<IncomeDto> response = new ResponseWrapper<>();
        try{
            Integer id = (Integer) request.getAttribute(ID);
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
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/income-data")
    public ResponseEntity<ResponseWrapper<List<IncomeSummaryDto>>> getData(HttpServletRequest request){
        ResponseWrapper<List<IncomeSummaryDto>> response = new ResponseWrapper<>();
        try{
            Integer id = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Income Data retrieved Successfully");
            response.setResponse(incomeService.getDataByCategory(id));
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/total-income")
    public ResponseEntity<ResponseWrapper<Double>> getTotalAmount(HttpServletRequest request){
        ResponseWrapper<Double> response = new ResponseWrapper<>();
        try{
            Integer id = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Data retrieved Successfully");
            Double totalAmount = incomeService.getTotalIncomeAmount(id);
            response.setResponse(totalAmount);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/income-per-month/{month}")
    public ResponseEntity<ResponseWrapper<List<IncomeDto>>> getAllExpense(@PathVariable Integer month, HttpServletRequest request ) {
        ResponseWrapper<List<IncomeDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            if (incomeService.getAllIncomePerMonth(id, month, YEAR) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Income retrieved successfully");
                response.setSuccess(true);
                response.setResponse(incomeService.getAllIncomePerMonth(id, month, YEAR));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Incomes not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
