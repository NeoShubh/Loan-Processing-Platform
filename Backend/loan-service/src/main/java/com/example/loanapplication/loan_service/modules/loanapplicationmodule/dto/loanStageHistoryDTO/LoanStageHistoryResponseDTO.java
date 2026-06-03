package com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanStageHistoryResponseDTO {

    private UUID loanStageHistoryId;
    private UUID loanApplicationId;
    private LoanStage oldStage;
    private LoanStage currentStage;
    private UUID changedBy;
    private LocalDateTime changedAt;
}
