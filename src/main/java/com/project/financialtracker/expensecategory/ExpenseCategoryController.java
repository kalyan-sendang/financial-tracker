package com.project.financialtracker.expensecategory;

import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping("/expenseCategory")
    public ResponseEntity<ResponseWrapper<List<ExpenseCategoryDto>>> getAllCategory(HttpServletRequest request) {
        ResponseWrapper<List<ExpenseCategoryDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            if (expenseCategoryService.getAllCategory(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense categories retrieved successfully");
                response.setSuccess(true);
                response.setResponse(expenseCategoryService.getAllCategory(id));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Expense Categories not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/expenseCategory")
    public ResponseEntity<ResponseWrapper<ExpenseCategoryDto>> addAllExpense(@RequestBody ExpenseCategoryReq expenseCategoryReq, HttpServletRequest request) {
        ResponseWrapper<ExpenseCategoryDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense categories retrieved successfully");
                response.setSuccess(true);
                response.setResponse(expenseCategoryService.addCategory(expenseCategoryReq, id));
                return ResponseEntity.ok(response);
        } catch(CustomException e) {
            response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            response.setMessage("Expense Category is already present");
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
