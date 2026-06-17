package com.example.loanapplication.loan_service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("DOCUMENT-SERVICE")
public interface DocumentService {

    @DeleteMapping("/api/documents/loans/{loanId}")
    ResponseEntity<String> deleteAllDocumentsByLoanId(@PathVariable String loanId);
//    documentService.deleteAllDocumentsByLoanId(UUID.fromString(loanId));


    @DeleteMapping("/api/documents/loans/applicants/{applicantId}")
    ResponseEntity<String> deleteAllDocumentsByApplicantId(@PathVariable String applicantId);
//    documentService.deleteAllDocumentsByApplicantId(UUID.fromString(ApplicantId));
}
