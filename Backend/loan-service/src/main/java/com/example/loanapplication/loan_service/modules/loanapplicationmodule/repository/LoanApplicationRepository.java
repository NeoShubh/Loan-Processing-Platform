package com.example.loanapplication.loan_service.modules.loanapplicationmodule.repository;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, UUID> {

    List<LoanApplication> findByCreatedBy(String userID);
    boolean existsByLoanID(UUID loanID);
}

