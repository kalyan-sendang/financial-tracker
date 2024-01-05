package com.project.financialtracker.expensecategory;

import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
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
    static final String ERROR = "Internal Server Error";


    static final String ID = "userId";

    @GetMapping("/expense-category")
    public ResponseEntity<ResponseWrapper<List<ExpenseCategoryDto>>> getAllCategory(HttpServletRequest request) {
        ResponseWrapper<List<ExpenseCategoryDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
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
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/expense-category/{expenseCategoryId}")
    public ResponseEntity<ResponseWrapper<ExpenseCategoryDto>> getAllCategory(@PathVariable int expenseCategoryId, HttpServletRequest request) {
        ResponseWrapper<ExpenseCategoryDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            if (expenseCategoryService.getAllCategory(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense category retrieved successfully");
                response.setSuccess(true);
                response.setResponse(expenseCategoryService.getACategory(id, expenseCategoryId));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Expense Categories not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/expense-category")
    public ResponseEntity<ResponseWrapper<ExpenseCategoryDto>> addAllExpense(@RequestBody ExpenseCategoryReq expenseCategoryReq, HttpServletRequest request) {
        ResponseWrapper<ExpenseCategoryDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense categories saved successfully");
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
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/expense-category/{expenseCategoryId}")
    public ResponseEntity<ResponseWrapper<ExpenseCategoryDto>> updateExpenseCategory(@PathVariable("expenseCategoryId") int expenseCategoryId,@RequestBody ExpenseCategoryReq expenseCategoryReq) {
        ResponseWrapper<ExpenseCategoryDto> response = new ResponseWrapper<>();
        try {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Expense categories updated successfully");
            response.setSuccess(true);
            response.setResponse(expenseCategoryService.updateCategory(expenseCategoryId, expenseCategoryReq));
            return ResponseEntity.ok(response);
        } catch(CustomException e) {
            response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            response.setMessage("Expense Category is not Found.");
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/expense-category/{expenseCategoryId}")
    public ResponseEntity<ResponseWrapper<List<ExpenseCategoryDto>>> deleteExpenseCategory(@PathVariable("expenseCategoryId") int expenseCategoryId, HttpServletRequest request) {
        ResponseWrapper<List<ExpenseCategoryDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Expense categories deleted successfully");
            response.setSuccess(true);
            response.setResponse(expenseCategoryService.deleteCategory(expenseCategoryId , id));
            return ResponseEntity.ok(response);
        } catch(CustomException e) {
            response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            response.setMessage("Expense Category is not found.");
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
