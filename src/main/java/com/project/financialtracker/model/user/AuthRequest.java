package com.project.financialtracker.model.user;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String userName;
    private String password;
}
