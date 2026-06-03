package com.example.loanapplication.loan_service.kafka.events;

import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoanStageHistoryEvent {
    private String loanID;
    private String userID;
    private LoanStageHistoryRequestDTO loanStageHistoryRequestDTO;
}
