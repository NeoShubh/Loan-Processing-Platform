package com.example.loanapplication.loan_service.modules.loanapplicationmodule.repository;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity.LoanStageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanStageHistoryRepository extends JpaRepository<LoanStageHistory, UUID> {
    List<LoanStageHistory> findByLoanApplicationLoanID(UUID loanID);
    Long deleteAllByLoanApplicationLoanID(UUID loanID);
}
