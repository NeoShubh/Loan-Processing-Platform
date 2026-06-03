package com.example.loanapplication.modules.usermodule.repository;

import com.example.loanapplication.modules.usermodule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByuserID(UUID userID);
    boolean existsByUserID(UUID userID);


}
