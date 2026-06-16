package com.example.loanapplication.rcu_service.external.services;

import com.example.loanapplication.rcu_service.external.FeinClient.interceptor.FeignInterceptorConfig;
import com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.documentDTOs.DocumentStatusDTO.DocumentStatusRequestDTO;
import com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.documentDTOs.WholeDocuementDTO.DocumentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name= "DOCUMENT-SERVICE", configuration = FeignInterceptorConfig.class)
public interface DocumentService {

    @GetMapping("/api/documents/loans/{loanId}")
    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByLoanId(@PathVariable UUID loanId);

    @GetMapping("/api/documents/loans/applicants/{applicantId}")
    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByApplicantId(@PathVariable UUID applicantId);

    @GetMapping("/api/documents/{documentId}")
    ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable UUID documentId);


    @DeleteMapping("/api/loans/{loanId}/documents")
    ResponseEntity<String> deleteAllDocumentsByLoanId(@PathVariable String loanId);



    @DeleteMapping("/api/loans/applicants/{applicantId}/documents")
    ResponseEntity<String> deleteAllDocumentsByApplicantId(@PathVariable String applicantId);


    @PutMapping("/api/documents/{documentId}/status")
    ResponseEntity<DocumentResponseDTO> updateDocumentStatus(@PathVariable String documentId , @RequestBody DocumentStatusRequestDTO documentStatusRequestDTO);
}
