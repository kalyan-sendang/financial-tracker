package com.project.financialtracker.saving;

import com.project.financialtracker.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saving")
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_id")
    private Integer savingId;

    @Column(name = "goal")
    private String goal;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "goal_amount")
    private Double goalAmount;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    Saving(SavingRequest savingRequest, User user){
        this.goal = savingRequest.getGoal();
        this.amount = savingRequest.getAmount();
        this.goalAmount= savingRequest.getGoalAmount();
        this.user = user;
    }
}
