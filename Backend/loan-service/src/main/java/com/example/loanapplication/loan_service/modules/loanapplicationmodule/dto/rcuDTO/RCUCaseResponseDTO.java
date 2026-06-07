package com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.rcuDTO;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.RCUStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RCUCaseResponseDTO {

    private UUID rcuCaseId;
    private UUID loan;
    private RCUStatus rcuStatus;
    private UUID assignedTo;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
}
