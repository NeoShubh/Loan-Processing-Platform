package com.example.loanapplication.loan_service.external.services.fallbacks;

import com.example.loanapplication.loan_service.external.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DocumentServiceFallback implements DocumentService {

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
}
