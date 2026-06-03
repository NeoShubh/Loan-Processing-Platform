package com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanapplicationDTO;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.CreditStatus;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanType;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.RCUStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponseDTO {

    private UUID loanID;
    private LoanType loanType;
    private LoanStage loanStage;
    private RCUStatus rcuStatus;
    private CreditStatus creditStatus;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
