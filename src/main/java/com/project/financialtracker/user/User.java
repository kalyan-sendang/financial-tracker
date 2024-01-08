package com.project.financialtracker.user;

import com.project.financialtracker.expense.Expense;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", unique = true)
    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Invalid Username")
    private String userName;


    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(name = "password")
    private String password;

    @Column(name = "profession")
    private String profession;

    @Column(name = "dob")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    public User(Integer userId) {
        this.userId = userId;
    }
}
