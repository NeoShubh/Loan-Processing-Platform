package com.example.loanapplication.rcu_service.external.fallbacks;



import com.example.loanapplication.rcu_service.external.services.DocumentService;
import com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.documentDTOs.DocumentStatusDTO.DocumentStatusRequestDTO;
import com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.documentDTOs.WholeDocuementDTO.DocumentResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class DocumentServiceFallback implements DocumentService {


    @Override
    public ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByLoanId(UUID loanId) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.emptyList());
    }

    @Override
    public ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByApplicantId(UUID applicantId) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.emptyList());
    }

    @Override
    public ResponseEntity<DocumentResponseDTO> getDocumentById(UUID documentId) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .build();
    }

    @Override
    public ResponseEntity<String> deleteAllDocumentsByLoanId(String loanId) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Document Service is currently unavailable");
    }

    @Override
    public ResponseEntity<String> deleteAllDocumentsByApplicantId(String applicantId) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Document Service is currently unavailable");
    }

    @Override
    public ResponseEntity<DocumentResponseDTO> updateDocumentStatus(String documentId, DocumentStatusRequestDTO documentStatusRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .build();
    }
}