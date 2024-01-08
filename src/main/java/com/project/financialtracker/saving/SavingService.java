package com.project.financialtracker.saving;

import com.project.financialtracker.notification.Notification;
import com.project.financialtracker.notification.NotificationRepository;
import com.project.financialtracker.user.User;
import com.project.financialtracker.utils.CustomException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SavingService {

    private final SavingRepository savingRepository;
    private final NotificationRepository notificationRepository;

    public SavingService(SavingRepository savingRepository, NotificationRepository notificationRepository) {
        this.savingRepository = savingRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<SavingDto> getSaving(Integer userId){
        List<Saving>savings = savingRepository.findSavingByUserId(userId);
        return savings.stream().map(saving -> new SavingDto(saving.getSavingId(), saving.getGoal(),saving.getAmount(),saving.getGoalAmount())).toList();
    }

    public SavingDto getASaving(Integer userId, Integer savingId){
        Saving saving = savingRepository.findSavingByIdAndUserId(userId, savingId);
        return new SavingDto(saving.getSavingId(), saving.getGoal(),saving.getAmount(),saving.getGoalAmount());
    }

    public SavingDto addSaving(SavingRequest savingRequest, Integer id){
        String goal = savingRequest.getGoal().trim();
        Saving saving = savingRepository.findSavingByIdAndNameAndStatus(id, goal);
        if (saving != null) {
            throw new CustomException("Goal is already present");
        } else {
            User user = new User();
            user.setUserId(id);
            Saving saving1 = new Saving(savingRequest, user);
            Saving saving2 = savingRepository.save(saving1);
            return new SavingDto(saving2.getSavingId(), saving2.getGoal(), saving2.getAmount(), saving2.getGoalAmount());
        }
    }

    public SavingDto addAmountToGoal(SavingAmountReq savingAmountReq, Integer id, Integer savingId){
        Saving saving = savingRepository.findSavingByIdAndUserId(id, savingId);
        Double goalAmount = saving.getGoalAmount();
        Double newAmount = savingAmountReq.getAmount()+ saving.getAmount();
        double notificationAmount = 0.8 * goalAmount;
        if(newAmount >= notificationAmount){
            String message = "Your saving amount for "+ saving.getGoal() +" is 80% complete.";
            Notification notification = new Notification();
            User user = new User();
            user.setUserId(id);
            notification.setUser(user);
            notification.setAlerts(message);
            notification.setTimeStamp(LocalDateTime.now());
            notificationRepository.save(notification);
        }
        if(goalAmount <= newAmount ){
            saving.setStatus(false);
            saving.setAmount(newAmount);
            saving.setGoalAmount(saving.getGoalAmount());
            saving.setGoal(saving.getGoal());
            throw new CustomException("You have reached your Goal..");
      }
        saving.setAmount(newAmount);
        saving.setGoalAmount(saving.getGoalAmount());
        saving.setGoal(saving.getGoal());
        savingRepository.save(saving);
        return new SavingDto(saving.getSavingId(), saving.getGoal(), saving.getAmount(), saving.getGoalAmount());
    }

    public Double getTotalSavingAmount(Integer userId) {
        return savingRepository.getTotalSavingByUserId(userId);
    }

    @Scheduled(cron = "0 0 12 ? * SUN") // Run every Sunday at 12:00 PM
    public void sendWeeklyProgressNotifications() {
        List<Saving> savings = savingRepository.findAll(); // Retrieve all savings

        for (Saving saving : savings) {
            double progressPercentage = (saving.getAmount() / saving.getGoalAmount()) * 100;
                String message = "Your saving amount for " + saving.getGoal() + " is " +
                        String.format("%.2f", progressPercentage) + "% complete.";

                Notification notification = new Notification();
                notification.setUser(saving.getUser());
                notification.setAlerts(message);
                notification.setTimeStamp(LocalDateTime.now());
                notificationRepository.save(notification);
            }
        }

}
