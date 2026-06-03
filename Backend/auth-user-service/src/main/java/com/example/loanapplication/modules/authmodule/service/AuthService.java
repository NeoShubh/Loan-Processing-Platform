package com.example.loanapplication.modules.authmodule.service;

import com.example.loanapplication.modules.authmodule.dto.AuthRequestDTO;
import com.example.loanapplication.modules.authmodule.dto.AuthResponseDTO;
import com.example.loanapplication.modules.usermodule.dto.UserRequestDTO;
import com.example.loanapplication.modules.usermodule.dto.UserResponseDTO;

public interface AuthService {
    public UserResponseDTO registerUser(UserRequestDTO request);
    AuthResponseDTO login(AuthRequestDTO authRequest);
}
