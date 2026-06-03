package com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.applicantDTO;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.ApplicantType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantRequestDTO {

    @NotNull(message = "Loan application ID can not be blank")
    private String loanApplication;

    @NotNull(message = "Name can not be blank")
    private String name;

    @NotNull(message = "PAN number can not be blank")
    private String panNumber;

    @NotNull(message = "Address can not be blank")
    private String address;

    @NotNull(message = "Applicant Type can not be blank")
    private ApplicantType applicantType;

}
