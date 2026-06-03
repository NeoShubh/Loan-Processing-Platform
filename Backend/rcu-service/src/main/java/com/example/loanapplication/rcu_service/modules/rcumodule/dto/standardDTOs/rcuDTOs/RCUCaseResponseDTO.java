package com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.rcuDTOs;

import com.example.loanapplication.rcu_service.modules.rcumodule.enums.rcu.RCUStatus;
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
