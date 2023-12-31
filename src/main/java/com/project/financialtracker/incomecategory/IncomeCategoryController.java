package com.project.financialtracker.incomecategory;


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
public class IncomeCategoryController {
    private final IncomeCategoryService incomeCategoryService;

    public IncomeCategoryController(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }
    static final String ERROR = "Internal Server Error";

    static final String ID = "userId";

    @GetMapping("/income-category")
    public ResponseEntity<ResponseWrapper<List<IncomeCategoryDto>>> getAllCategory(HttpServletRequest request) {
        ResponseWrapper<List<IncomeCategoryDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
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
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/income-category")
    public ResponseEntity<ResponseWrapper<IncomeCategoryDto>> addAllExpense(@RequestBody IncomeCategoryReq incomeCategoryReq, HttpServletRequest request) {
        ResponseWrapper<IncomeCategoryDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Income Category added successfully");
            response.setSuccess(true);
            response.setResponse(incomeCategoryService.addCategory(incomeCategoryReq, id));
            return ResponseEntity.ok(response);
        }catch(CustomException e) {
            response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            response.setMessage("Income Category is already present");
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/income-category/{incomeCategoryId}")
    public ResponseEntity<ResponseWrapper<List<IncomeCategoryDto>>> deleteExpenseCategory(@PathVariable("incomeCategoryId") int expenseCategoryId,HttpServletRequest request) {
        ResponseWrapper<List<IncomeCategoryDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Income categories deleted successfully");
            response.setSuccess(true);
            response.setResponse(incomeCategoryService.deleteCategory(expenseCategoryId, id));
            return ResponseEntity.ok(response);
        } catch(CustomException e) {
            response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            response.setMessage("Income Category is not found.");
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
