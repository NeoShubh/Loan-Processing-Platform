package com.example.loanapplication.modules.documentmodule.dto.DocumentStatusDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentStatusRequestDTO {
    private String verifiedBy;
    private String documentStatus;
    private String remarks;
}