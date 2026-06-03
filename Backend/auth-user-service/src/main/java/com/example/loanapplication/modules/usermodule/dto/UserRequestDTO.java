package com.example.loanapplication.modules.usermodule.dto;

import com.example.loanapplication.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "User name cannot be blank")
    private String name;
    @Email(message = "Invalid email format")
    @NotBlank(message = "User email cannot be blank")
    private String email;
    @NotBlank(message = "User password cannot be blank")
    private String password;
    @NotNull
    private Role role;
}
