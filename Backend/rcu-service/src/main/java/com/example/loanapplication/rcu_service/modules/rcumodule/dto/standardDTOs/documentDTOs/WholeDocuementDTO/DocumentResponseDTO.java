package com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.documentDTOs.WholeDocuementDTO;

import com.example.loanapplication.rcu_service.modules.rcumodule.enums.document.DocumentStatus;
import com.example.loanapplication.rcu_service.modules.rcumodule.enums.document.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseDTO {
    private UUID documentId;
    private UUID loanApplication;
    private UUID applicant;
    private DocumentStatus documentStatus;
    private DocumentType documentType;
    private String fileUrl;
    private UUID uploadedBy;
    private LocalDateTime uploadedAt;
    private UUID verifiedBy;
    private LocalDateTime verifiedAt;
    private LocalDateTime updatedAt;
    private String remarks;
}
