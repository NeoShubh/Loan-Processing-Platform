package com.example.loanapplication.modules.documentmodule.controller;

import com.example.loanapplication.modules.documentmodule.dto.DocumentStatusDTO.DocumentStatusRequestDTO;

import com.example.loanapplication.modules.documentmodule.dto.WholeDocuementDTO.DocumentRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.WholeDocuementDTO.DocumentResponseDTO;
import com.example.loanapplication.modules.documentmodule.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;


    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
    @PreAuthorize("hasRole('RM')")
    @PostMapping("")
    ResponseEntity<DocumentResponseDTO> createDocument(@RequestParam MultipartFile file, @RequestParam UUID loanApplicationId, @RequestParam UUID applicantId, @RequestParam String documentType, @RequestParam UUID UploadedBy){
        DocumentResponseDTO documentResponseDTO = documentService.createDocument(file, loanApplicationId, applicantId, documentType, UploadedBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentResponseDTO);
    }
    @PreAuthorize("hasAnyRole('RM','CM','RCU')")
    @GetMapping("/{documentId}")
    ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable UUID documentId) {
        DocumentResponseDTO documentResponseDTO = documentService.getDocumentById(documentId);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponseDTO);
    }
    @PreAuthorize("hasRole('RM')")
    @PutMapping("/{documentId}")
    ResponseEntity<DocumentResponseDTO> updateDocument(@PathVariable UUID documentId, @RequestBody DocumentRequestDTO documentRequestDTO) {
        DocumentResponseDTO documentResponseDTO = documentService.updateDocument(documentId, documentRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponseDTO);
    }
    @PreAuthorize("hasRole('RM')")
    @DeleteMapping("/loans/{loanId}")
    ResponseEntity<String> deleteAllDocumentsByLoanId(@PathVariable String loanId) {
        documentService.deleteAllDocumentsByLoanId(UUID.fromString(loanId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Content Deleted");
    }
    @PreAuthorize("hasRole('RM')")
    @DeleteMapping("/loans/applicants/{applicantId}")
    ResponseEntity<String> deleteAllDocumentsByApplicantId(@PathVariable String applicantId) {
        documentService.deleteAllDocumentsByApplicantId(UUID.fromString(applicantId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Content Deleted");
    }
    @PreAuthorize("hasRole('RM')")
    @DeleteMapping("/{documentId}")
    ResponseEntity<String> deleteDocumentsById(@PathVariable UUID documentId) {
        documentService.deleteDocumentsById(documentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Content Deleted");
    }
    @PreAuthorize("hasAnyRole('RM','CM','RCU')")
    @GetMapping("/loans/{loanId}")
    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByLoanId(@PathVariable UUID loanId){
        List <DocumentResponseDTO> responesList = documentService.getAllDocumentsByLoanId(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(responesList);
    }
    @PreAuthorize("hasAnyRole('RM','CM','RCU')")
    @GetMapping("/loans/applicants/{applicantId}")
    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByApplicantId(@PathVariable UUID applicantId){
        List <DocumentResponseDTO> responselist = documentService.getAllDocumentsByApplicantId(applicantId);
        return ResponseEntity.status(HttpStatus.OK).body(responselist);
    }
    @PreAuthorize("hasRole('RM')")
    @PutMapping("/{documentId}/file")
    ResponseEntity<DocumentResponseDTO> updateDocumentFile(@PathVariable UUID documentId,@RequestParam MultipartFile file){
        DocumentResponseDTO documentResponseDTO = documentService.updateDocumentFile(documentId,file);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponseDTO);
    }
    @PreAuthorize("hasAnyRole('RM','CM','RCU')")
    @PutMapping("/{documentId}/status")
    ResponseEntity<DocumentResponseDTO> updateDocumentStatus(@PathVariable String documentId ,@RequestBody DocumentStatusRequestDTO documentStatusRequestDTO){
        DocumentResponseDTO documentResponseDTO = documentService.updateDocumentStatus(documentId,documentStatusRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponseDTO);
    }
}
