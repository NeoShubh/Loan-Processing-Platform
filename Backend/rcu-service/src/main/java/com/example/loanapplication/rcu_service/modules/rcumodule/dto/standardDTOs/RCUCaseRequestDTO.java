package com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs;

import com.example.loanapplication.rcu_service.modules.rcumodule.enums.RCUStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RCUCaseRequestDTO {
    @NotNull(message = "RCU Case ID can not be null")
    private UUID rcuCaseId;
    @NotNull(message = "loanID can not be null")
    private UUID loanId;
    @NotNull(message = "RCU status can not be null")
    private RCUStatus rcuStatus;
    @NotNull(message = "Assigned User can not be null")
    private UUID  assignedTo;

}
