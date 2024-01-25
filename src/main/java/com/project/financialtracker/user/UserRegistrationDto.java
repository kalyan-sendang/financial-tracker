package com.project.financialtracker.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    private String userName;
    private String email;
    private String password;
    private String profession;
    private LocalDate dob;
}
