package com.example.loanapplication.modules.authmodule.service.impl;

import com.example.loanapplication.config.JWTService;
import com.example.loanapplication.exceptions.user.InvalidCredentialsException;
import com.example.loanapplication.exceptions.user.UserAlreadyExistsException;
import com.example.loanapplication.modules.authmodule.dto.AuthRequestDTO;
import com.example.loanapplication.modules.authmodule.dto.AuthResponseDTO;
import com.example.loanapplication.modules.authmodule.service.AuthService;
import com.example.loanapplication.modules.usermodule.dto.UserRequestDTO;
import com.example.loanapplication.modules.usermodule.dto.UserResponseDTO;
import com.example.loanapplication.modules.usermodule.entity.User;
import com.example.loanapplication.modules.usermodule.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JWTService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        System.out.print(request);

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        return new UserResponseDTO(user.getUserID(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt(),user.getModifiedAt());
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        User user = userRepository
                    .findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid Credentials.");
        }
        String accessToken = jwtService.generateAccessToken(user.getEmail(), String.valueOf(user.getRole()));
        String refreshToken = jwtService.generateRefreshToken(user.getEmail(), String.valueOf(user.getRole()));
        return new AuthResponseDTO("Login Successful",accessToken,refreshToken);
    }

}
