package com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanStage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanStageHistoryRequestDTO {
    @NotNull(message = "loan application ID can not be blank")
    private String loanApplicationId;
    @NotNull(message = "old Stage can not be null")
    private LoanStage oldStage;
    @NotNull(message = "current Stage can not be null")
    private LoanStage currentStage;
    @NotNull(message = "changed by field can not be null")
    private String changedBy;

}
