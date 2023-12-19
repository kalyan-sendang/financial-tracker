package com.project.financialtracker.incomecategory;


import com.project.financialtracker.user.User;
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
public class IncomeCategoryController {
    private final IncomeCategoryService incomeCategoryService;

    public IncomeCategoryController(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }

    @GetMapping("/incomeCategory")
    public ResponseEntity<ResponseWrapper<List<IncomeCategoryDto>>> getAllCategory(HttpServletRequest request) {
        ResponseWrapper<List<IncomeCategoryDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            if (incomeCategoryService.getAllCategory(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Income Categories retrieved successfully");
                response.setSuccess(true);
                response.setResponse(incomeCategoryService.getAllCategory(id));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Income Categories not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/incomeCategory")
    public ResponseEntity<ResponseWrapper<IncomeCategoryDto>> addAllExpense(@RequestBody IncomeCategoryReq incomeCategoryReq, HttpServletRequest request) {
        ResponseWrapper<IncomeCategoryDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            User user = new User();
            user.setUserId(id);
            IncomeCategory incomeCategory = new IncomeCategory(incomeCategoryReq, user);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Income Categories retrieved successfully");
            response.setSuccess(true);
            response.setResponse(incomeCategoryService.addCategory(incomeCategory));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
