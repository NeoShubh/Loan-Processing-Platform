package com.example.loanapplication.modules.documentmodule.dto.WholeDocuementDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequestDTO {

    @NotNull(message = "Loan application ID can not be blank")
    private String loanApplication;
    @NotNull(message = "Applicant can not be blank")
    private String applicant;
    @NotNull(message = "Document Status can not be blank")
    private String documentStatus ;
    @NotNull(message = "document type can not be blank")
    private String documentType;
    @NotNull(message = "file URL can not be blank")
    private String fileUrl;
    @NotNull(message = "Uploaded by can not be blank")
    private String uploadedBy;
    @NotNull(message = "verified  can not be blank")
    private String verifiedBy;
    @NotNull(message = "remarks can not be blank")
    private String remarks;

}
