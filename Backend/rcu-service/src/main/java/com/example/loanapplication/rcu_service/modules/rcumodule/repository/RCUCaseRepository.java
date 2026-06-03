package com.example.loanapplication.rcu_service.modules.rcumodule.repository;


import com.example.loanapplication.rcu_service.modules.rcumodule.entity.RCUCase;
import com.example.loanapplication.rcu_service.modules.rcumodule.enums.rcu.RCUStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RCUCaseRepository extends MongoRepository<RCUCase, UUID> {
    boolean existsByLoanIdAndRcuStatus(UUID loanId, RCUStatus rcuStatus);
    Optional<RCUCase> findByLoanId(UUID loanID);
}
