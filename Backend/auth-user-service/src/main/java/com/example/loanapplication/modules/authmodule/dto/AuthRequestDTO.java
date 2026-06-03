package com.example.loanapplication.modules.authmodule.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {
    @Email
    @NotNull(message = "email can not be null")
    private String email;
    @NotNull(message = "password can not be null")
    private String password;
}
