package com.example.loanapplication.loan_service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("DOCUMENT-SERVICE")
public interface DocumentService {

    @DeleteMapping("/loans/{loanId}/documents")
    ResponseEntity<String> deleteAllDocumentsByLoanId(@PathVariable String loanId);
//    documentService.deleteAllDocumentsByLoanId(UUID.fromString(loanId));


    @DeleteMapping("/loans/applicants/{applicantId}/documents")
    ResponseEntity<String> deleteAllDocumentsByApplicantId(@PathVariable String applicantId);
//    documentService.deleteAllDocumentsByApplicantId(UUID.fromString(ApplicantId));
}
