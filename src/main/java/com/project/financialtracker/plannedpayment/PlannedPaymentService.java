package com.project.financialtracker.plannedpayment;

import com.project.financialtracker.expense.Expense;
import com.project.financialtracker.expense.ExpenseRepository;
import com.project.financialtracker.notification.Notification;
import com.project.financialtracker.notification.NotificationRepository;
import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.wallet.Wallet;
import com.project.financialtracker.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        schedulePayment(plannedPayment.getUser(), plannedPayment.getId());
        return new PlannedPaymentDto(plannedPayment);
    }

    private void scheduleNotification(PlannedPayment plannedPayment) {
        LocalDateTime notificationTime = plannedPayment.getDate().minusDays(1).atZone(ZoneId.systemDefault()).toLocalDateTime();
        // Extract the day of the month from the user's input
        int dayOfMonth = notificationTime.getDayOfMonth();
        int minute = notificationTime.getMinute();
        int hour = notificationTime.getHour();
        // Generate the cron expression dynamically
        String cronExpression = String.format("0 %d %d %d * ?", minute, hour, dayOfMonth);
        System.out.println("----cron Expression--- "+ cronExpression);
        taskScheduler.schedule(() -> {
            String message = "Payment due in 24 hours: " + plannedPayment.getAmount();
            System.out.println(message);
            Notification notification = new Notification();
            User user = new User();
            user.setUserId(plannedPayment.getUser().getUserId());
            notification.setUser(user);
            notification.setAlerts(message);
            notification.setTimeStamp(plannedPayment.getDate());
            notificationRepository.save(notification);

            Integer id = plannedPayment.getId();
        }, new CronTrigger(cronExpression));
        System.out.println("After Scheduling");
    }

    private void schedulePayment(User user, Integer id) {
        PlannedPayment plannedPayment = plannedPaymentRepo.getPlannedPaymentById(id);
        LocalDateTime notificationTime = plannedPayment.getDate().atZone(ZoneId.systemDefault()).toLocalDateTime();
        int dayOfMonth = notificationTime.getDayOfMonth();
        int minute = notificationTime.getMinute();
        int hour = notificationTime.getHour();
        // Generate the cron expression dynamically
        String cronExpression = String.format("0 %d %d %d * ?", minute, hour, dayOfMonth);
        taskScheduler.schedule(() -> {
            if (plannedPayment.getPending()) {
                processPayment(user, plannedPayment);
            } else {
                throw new CustomException("Payment is already initiated.");
            }
        }, new CronTrigger(cronExpression));
    }

    public PlannedPaymentDto payScheduledPayment(Integer id) {
        PlannedPayment plannedPayment = plannedPaymentRepo.getPlannedPaymentById(id);
        User user = new User();
        user.setUserId(plannedPayment.getUser().getUserId());
        processPayment(user, plannedPayment);
        return new PlannedPaymentDto(plannedPayment);
    }

    public void processPayment(User user, PlannedPayment plannedPayment) {
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
            plannedPayment.setPending(false);
            plannedPayment.setDate(LocalDateTime.now());
            plannedPaymentRepo.save(plannedPayment);
        }
    }

}