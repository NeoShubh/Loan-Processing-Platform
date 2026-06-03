package com.example.loanapplication.modules.usermodule.controller;

import com.example.loanapplication.modules.usermodule.dto.UserRequestDTO;
import com.example.loanapplication.modules.usermodule.dto.UserResponseDTO;
import com.example.loanapplication.modules.usermodule.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email/{email}")
    ResponseEntity<UserResponseDTO> getUserByEmailID ( @PathVariable String email){
        UserResponseDTO userResponseDTO = userService.getUserByEmailID(email);
        return ResponseEntity.ok(userResponseDTO);
    }


    @GetMapping("/{userID}")
    ResponseEntity<UserResponseDTO> getUserByUserID ( @PathVariable String userID){
        UserResponseDTO userResponseDTO = userService.getUserByUserID(userID);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{userID}")
    ResponseEntity<UserResponseDTO> updateUser(@PathVariable String userID,@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUser(userID,userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/email/{email}")
    ResponseEntity<?> deleteUserByEmailID(@PathVariable String email){
        userService.deleteUserByEmailID(email);
        System.out.println("Delete successfully");
        return ResponseEntity.noContent().build();
//        return ResponseEntity.ok("User deleted successfully.");
    }

    @DeleteMapping("/id/{userID}")
    ResponseEntity<?> deleteUserByUserID(@PathVariable String userID){
        userService.deleteUserByUserID(userID);
        return ResponseEntity.noContent().build();
//        return ResponseEntity.ok("User deleted successfully.");
    }
}