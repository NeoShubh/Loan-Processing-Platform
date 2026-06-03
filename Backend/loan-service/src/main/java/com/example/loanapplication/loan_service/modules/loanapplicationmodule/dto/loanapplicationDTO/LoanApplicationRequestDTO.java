package com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanapplicationDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequestDTO {

    @NotNull(message = "Loan type can not be null")
    private String loanType;

    @NotNull(message = "Loan stage can not be null")
    private String loanStage;

    @NotNull(message = "RCU status can not be null")
    private String rcuStatus;

    @NotNull(message = "credit status can not be null")
    private String creditStatus;

    @NotNull(message = "creator can not be null")
    private String createdBy;
}
