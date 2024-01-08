package com.project.financialtracker.plannedpayment;

import com.project.financialtracker.expense.Expense;
import com.project.financialtracker.expense.ExpenseRepository;
import com.project.financialtracker.notification.Notification;
import com.project.financialtracker.notification.NotificationRepository;
import com.project.financialtracker.user.User;
import com.project.financialtracker.wallet.Wallet;
import com.project.financialtracker.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlannedPaymentService {
    private final PlannedPaymentRepo plannedPaymentRepo;
    private final TaskScheduler taskScheduler;
    private final NotificationRepository notificationRepository;
    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;

    public List<PlannedPaymentDto> getScheduledPayments(Integer id) {
        List<PlannedPayment> plannedPayments = plannedPaymentRepo.getPlannedPaymentByUserId(id);
        return plannedPayments.stream().map(PlannedPaymentDto::new).toList();
    }

    public PlannedPaymentDto setPayment(PlannedPayment plannedPayment) {
        plannedPaymentRepo.save(plannedPayment);
        scheduleNotification(plannedPayment);
        return new PlannedPaymentDto(plannedPayment);
    }

    private void scheduleNotification(PlannedPayment plannedPayment) {
        LocalDateTime notificationTime = plannedPayment.getDate().minusDays(1);
        taskScheduler.schedule(() -> {
            String message = "Payment due in 24 hours: " + plannedPayment.getAmount();
            Notification notification = new Notification();
            User user = new User();
            user.setUserId(plannedPayment.getUser().getUserId());
            notification.setUser(user);
            notification.setAlerts(message);
            notification.setTimeStamp(plannedPayment.getDate());
            notificationRepository.save(notification);

            schedulePayment(user, plannedPayment);
        }, Date.from(notificationTime.atZone(ZoneId.systemDefault()).toInstant()));
    }

    private void schedulePayment(User user, PlannedPayment plannedPayment) {
        LocalDateTime paymentTime = plannedPayment.getDate();
        taskScheduler.schedule(() -> {
            Integer walletId = plannedPayment.getWallet().getWalletId();
            Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
            if (optionalWallet.isPresent()) {
                Wallet wallet = optionalWallet.get();
                if (wallet.getAmount() < plannedPayment.getAmount()) {
                    String message = "Your Wallet amount is less than your expense";
                    Notification notification = new Notification();
                    notification.setUser(user);
                    notification.setAlerts(message);
                    notification.setTimeStamp(LocalDateTime.now());
                    notificationRepository.save(notification);
                }
                Expense expense = new Expense();
                expense.setUser(user);
                expense.setWallet(plannedPayment.getWallet());
                expense.setDate(plannedPayment.getDate());
                expense.setAmount(plannedPayment.getAmount());
                expense.setNote(plannedPayment.getNote());
                expense.setExpenseCategory(plannedPayment.getExpenseCategory());
                System.out.println("---expense---" + expense.getAmount());
                expenseRepository.save(expense);
                wallet.setAmount(wallet.getAmount() - plannedPayment.getAmount());
                walletRepository.save(wallet);
            }
        }, Date.from(paymentTime.atZone(ZoneId.systemDefault()).toInstant()));
    }
}