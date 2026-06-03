package com.example.loanapplication.modules.usermodule.service.impl;

import com.example.loanapplication.exceptions.user.UserNotFoundException;
import com.example.loanapplication.exceptions.user.UserNotFoundException;
import com.example.loanapplication.modules.usermodule.dto.UserRequestDTO;
import com.example.loanapplication.modules.usermodule.dto.UserResponseDTO;
import com.example.loanapplication.modules.usermodule.entity.User;
import com.example.loanapplication.modules.usermodule.repository.UserRepository;
import com.example.loanapplication.modules.usermodule.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO getUserByEmailID(String emailID) {
        User user = userRepository.findByEmail(emailID).orElseThrow(() -> new com.example.loanapplication.exceptions.user.UserNotFoundException("User Not Found"));
        return UserResponseDTO.builder().userID(user.getUserID()).name(user.getName()).email(user.getEmail()).role(user.getRole()).createdAt(user.getCreatedAt()).build();
    }

    @Override
    public UserResponseDTO getUserByUserID(String userID) {
        User user = userRepository.findByuserID(UUID.fromString(userID)).orElseThrow(() -> new com.example.loanapplication.exceptions.user.UserNotFoundException("User Not Found"));
        return UserResponseDTO.builder().userID(user.getUserID()).name(user.getName()).email(user.getEmail()).role(user.getRole()).createdAt(user.getCreatedAt()).build();
    }

    @Override
    public UserResponseDTO updateUser(String userID, UserRequestDTO userRequestDTO) {
        User user = userRepository.findByuserID(UUID.fromString(userID)).orElseThrow(() -> new com.example.loanapplication.exceptions.user.UserNotFoundException("User Not Found"));

        if (userRequestDTO.getEmail() != null) {
            user.setEmail(userRequestDTO.getEmail());
        }

        if (userRequestDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        if (userRequestDTO.getRole() != null)
            user.setRole(userRequestDTO.getRole());

        if (userRequestDTO.getName() != null)
            user.setName(userRequestDTO.getName());

        userRepository.save(user);
        return UserResponseDTO.builder().userID(user.getUserID()).name(user.getName()).email(user.getEmail()).role(user.getRole()).createdAt(user.getCreatedAt()).modifiedAt(user.getModifiedAt()).build();
    }

    @Override
    public void deleteUserByEmailID(String emailID) {
        User user = userRepository.findByEmail(emailID).orElseThrow(() -> new com.example.loanapplication.exceptions.user.UserNotFoundException("User Not Found"));
        userRepository.delete(user);
    }

    @Override
    public void deleteUserByUserID(String userID) {
        User user = userRepository.findByuserID(UUID.fromString(userID)).orElseThrow(() -> new com.example.loanapplication.exceptions.user.UserNotFoundException("User Not Found"));
        userRepository.delete(user);
    }

    @Override
    public boolean isUserAvailable(String userID) {
        return userRepository.existsByUserID(UUID.fromString(userID));
    }
}
