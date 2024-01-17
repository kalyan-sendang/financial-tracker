package com.project.financialtracker.saving;

import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
@Tag(name = "Saving Controller", description = "This is saving api in saving")
public class SavingController {

    private final SavingService savingService;

    public SavingController(SavingService savingService) {
        this.savingService = savingService;
    }

    static final String ID = "userId";

    @GetMapping("/saving")
    public ResponseEntity<ResponseWrapper<List<SavingDto>>> getAllSaving(HttpServletRequest request) {
        ResponseWrapper<List<SavingDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            if (savingService.getSaving(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Savings retrieved successfully");
                response.setSuccess(true);
                response.setResponse(savingService.getSaving(id));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("No saving  found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("InternalServerError");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/saving/{savingId}")
    public ResponseEntity<ResponseWrapper<SavingDto>> getSaving(@PathVariable Integer savingId, HttpServletRequest request) {
        ResponseWrapper<SavingDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            if (savingService.getASaving(id, savingId) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Savings retrieved successfully");
                response.setSuccess(true);
                response.setResponse(savingService.getASaving(id, savingId));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("No saving  found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("InternalServerError");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/saving")
    public ResponseEntity<ResponseWrapper<SavingDto>> addSaving(@RequestBody SavingRequest savingRequest, HttpServletRequest request) {
        ResponseWrapper<SavingDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Goal is set successfully");
            response.setSuccess(true);
            response.setResponse(savingService.addSaving(savingRequest, id));
            return ResponseEntity.ok(response);
        } catch(CustomException e) {
            response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            response.setMessage("Goal is already present");
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/saving/{savingId}")
    public ResponseEntity<ResponseWrapper<SavingDto>> addAmount(@PathVariable Integer savingId, @RequestBody SavingAmountReq savingAmountReq, HttpServletRequest request) {
        ResponseWrapper<SavingDto> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Amount is added successfully");
            response.setSuccess(true);
            response.setResponse(savingService.addAmountToGoal(savingAmountReq, id, savingId));
            return ResponseEntity.ok(response);
        }catch(CustomException e) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Hurray...You have reached your Goal..");
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/total-saving")
    public ResponseEntity<ResponseWrapper<Double>> getTotalAmount(HttpServletRequest request) {
        ResponseWrapper<Double> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute(ID);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Total expense amount retrieved Successfully");
            Double totalAmount = savingService.getTotalSavingAmount(id);
            response.setResponse(totalAmount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Sever Error");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
