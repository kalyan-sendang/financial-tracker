package com.project.financialtracker.category;

import com.project.financialtracker.expense.Expense;
import com.project.financialtracker.expense.ExpenseDto;
import com.project.financialtracker.expense.ExpenseRequest;
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
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseWrapper<List<CategoryDto>>> getAllCategory(HttpServletRequest request) {
        ResponseWrapper<List<CategoryDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            if (categoryService.getAllCategory(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense retrieved successfully");
                response.setSuccess(true);
                response.setResponse(categoryService.getAllCategory(id));
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

    @PostMapping("/category")
    public ResponseEntity<ResponseWrapper<CategoryDto>> addAllExpense(@RequestBody CategoryRequest categoryRequest, HttpServletRequest request) {
        ResponseWrapper<CategoryDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            User user = new User();
            user.setUserId(id);
            Category category = new Category(categoryRequest, user);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Expense retrieved successfully");
            response.setSuccess(true);
            response.setResponse(categoryService.addCategory(category));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
