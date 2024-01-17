package com.project.financialtracker.plannedpayment;

import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.utils.ResponseWrapper;
import com.project.financialtracker.wallet.Wallet;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planned-payment")
@Tag(name = "Planned Payment Controller", description = "This is Planned Payment api for schedule payment")
public class PlannedPaymentController {
    private final PlannedPaymentService plannedPaymentService;

    public PlannedPaymentController(PlannedPaymentService plannedPaymentService) {
        this.plannedPaymentService = plannedPaymentService;
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<PlannedPaymentDto>>> getAllExpense(HttpServletRequest request) {
        ResponseWrapper<List<PlannedPaymentDto>> response = new ResponseWrapper<>();
        try {
            Integer id = (Integer) request.getAttribute("userId");
            if (plannedPaymentService.getScheduledPayments(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Planned Schedule retrieved successfully");
                response.setSuccess(true);
                response.setResponse(plannedPaymentService.getScheduledPayments(id));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Schedule planned not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{walletId}")
    public ResponseEntity<ResponseWrapper<PlannedPaymentDto>> setPaymentSchedule(@PathVariable Integer walletId, @RequestBody PlannedPaymentReq plannedPaymentReq, HttpServletRequest request) {
        ResponseWrapper<PlannedPaymentDto> response = new ResponseWrapper<>();
        Integer id = (Integer) request.getAttribute("userId");
        try {
            User user = new User();
            Wallet wallet = new Wallet();
            wallet.setWalletId(walletId);
            user.setUserId(id);
            PlannedPayment plannedPayment = new PlannedPayment(plannedPaymentReq, user);
            plannedPayment.setWallet(wallet);
            response.setMessage("Payment is scheduled successfully");
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setResponse(plannedPaymentService.setPayment(plannedPayment));
            return ResponseEntity.ok(response);
        } catch (CustomException ex) {
            response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
            response.setMessage("Payment is already initiated");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<ResponseWrapper<PlannedPaymentDto>> payScheduledPayment(@PathVariable Integer id) {
        ResponseWrapper<PlannedPaymentDto> response = new ResponseWrapper<>();
        try {
            response.setMessage("Payment successfully initiated");
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setResponse(plannedPaymentService.payScheduledPayment(id));
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
