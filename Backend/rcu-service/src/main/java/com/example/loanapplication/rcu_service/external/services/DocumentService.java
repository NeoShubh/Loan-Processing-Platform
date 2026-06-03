package com.example.loanapplication.rcu_service.external.services;

import com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.documentDTOs.DocumentStatusDTO.DocumentStatusRequestDTO;
import com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.documentDTOs.WholeDocuementDTO.DocumentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient("DOCUMENT-SERVICE")
public interface DocumentService {

    @GetMapping("/loans/{loanId}/documents")
    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByLoanId(@PathVariable UUID loanId);

    @GetMapping("/loans/applicants/{applicantId}/documents")
    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByApplicantId(@PathVariable UUID applicantId);

    @GetMapping("/documents/{documentId}")
    ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable UUID documentId);


    @DeleteMapping("/loans/{loanId}/documents")
    ResponseEntity<String> deleteAllDocumentsByLoanId(@PathVariable String loanId);
//    documentService.deleteAllDocumentsByLoanId(UUID.fromString(loanId));


    @DeleteMapping("/loans/applicants/{applicantId}/documents")
    ResponseEntity<String> deleteAllDocumentsByApplicantId(@PathVariable String applicantId);
//    documentService.deleteAllDocumentsByApplicantId(UUID.fromString(ApplicantId));

    @PutMapping("/documents/{documentId}/status")
    ResponseEntity<DocumentResponseDTO> updateDocumentStatus(@PathVariable String documentId , @RequestBody DocumentStatusRequestDTO documentStatusRequestDTO);
}
