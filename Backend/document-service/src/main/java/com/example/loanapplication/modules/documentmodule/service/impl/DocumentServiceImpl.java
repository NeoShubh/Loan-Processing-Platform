package com.example.loanapplication.modules.documentmodule.service.impl;

import com.example.loanapplication.exception.document.DocumentFormatNotAllowedException;
import com.example.loanapplication.exception.document.DocumentNotFoundException;
import com.example.loanapplication.exception.document.InvalidDocumentTypeException;
import com.example.loanapplication.modules.documentmodule.dto.DocumentStatusDTO.DocumentStatusRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.WholeDocuementDTO.DocumentRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.WholeDocuementDTO.DocumentResponseDTO;
import com.example.loanapplication.modules.documentmodule.entity.Document;
import com.example.loanapplication.modules.documentmodule.enums.DocumentStatus;
import com.example.loanapplication.modules.documentmodule.enums.DocumentType;
import com.example.loanapplication.modules.documentmodule.repository.DocumentRepository;
import com.example.loanapplication.modules.documentmodule.service.DocumentService;
import com.example.loanapplication.modules.documentmodule.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

    public DocumentServiceImpl(DocumentRepository documentRepository, FileStorageService fileStorageService) {
        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public DocumentResponseDTO createDocument(MultipartFile file, UUID loanApplicationId, UUID applicantId, String documentType, UUID uploadedBy) {
        if (!file.getContentType().equals("application/pdf")) {
            throw new DocumentFormatNotAllowedException("Only PDF format is allowed");
        }

        Document document = new Document();

        String filePath = fileStorageService.saveFile(file);

        document.setFileUrl(filePath); //File URL
        document.setDocumentStatus(DocumentStatus.UPLOADED); //document status
        document.setLoanId(loanApplicationId); //LoanApplication
        document.setApplicantId(applicantId); // Applicant
        document.setUploadedBy(uploadedBy); //UploadedBy
        try {
            document.setDocumentType(DocumentType.valueOf(documentType));//document Type
        } catch (IllegalArgumentException ex) {
            throw new InvalidDocumentTypeException("Invalid document type");
        }
        documentRepository.save(document);

        return DocumentResponseDTO.builder()
                .documentId(document.getDocumentId())
                .loanApplication(document.getLoanId())
                .applicant(document.getApplicantId())
                .documentType(document.getDocumentType())
                .documentStatus(document.getDocumentStatus())
                .fileUrl(document.getFileUrl())
                .uploadedBy(document.getUploadedBy())
                .uploadedAt(document.getUploadedAt())
                .build();
    }

    @Override
    public DocumentResponseDTO getDocumentById(UUID documentId) {

        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));
        return DocumentResponseDTO.builder()
                .documentId(document.getDocumentId())
                .loanApplication(document.getLoanId())
                .applicant(document.getApplicantId())
                .documentStatus(document.getDocumentStatus())
                .documentType(document.getDocumentType())
                .fileUrl(document.getFileUrl())
                .uploadedBy(document.getUploadedBy())
                .uploadedAt(document.getUploadedAt())
                .verifiedBy(document.getVerifiedBy() != null ? document.getVerifiedBy() : null).verifiedAt(document.getVerifiedAt())
                .updatedAt(document.getUpdatedAt())
                .remarks(document.getRemarks() != null ? document.getRemarks() : null)
                .build();
    }

    @Override
    public List<DocumentResponseDTO> getAllDocumentsByLoanId(UUID loanId) {

        List<Document> documents = documentRepository.findAllByLoanId(loanId);
        List<DocumentResponseDTO> documentResponseList = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            DocumentResponseDTO singleDocumentResponseDTO = DocumentResponseDTO.builder()
                    .documentId(documents.get(i).getDocumentId())
                    .loanApplication(documents.get(i).getLoanId())
                    .applicant(documents.get(i).getApplicantId() != null ?
                            documents.get(i).getApplicantId() : null)
                    .documentStatus(documents.get(i).getDocumentStatus())
                    .documentType(documents.get(i).getDocumentType())
                    .fileUrl(documents.get(i).getFileUrl())
                    .uploadedBy(documents.get(i).getUploadedBy())
                    .verifiedBy(documents.get(i).getVerifiedBy() != null ? documents.get(i).getVerifiedBy() : null)
                    .verifiedAt(documents.get(i).getVerifiedAt())
                    .uploadedAt(documents.get(i).getUploadedAt())
                    .updatedAt(documents.get(i).getUpdatedAt())
                    .remarks(documents.get(i).getRemarks() != null ? documents.get(i).getRemarks() : null).build();

            documentResponseList.add(singleDocumentResponseDTO);
        }
        return documentResponseList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Document> getDocumentsByLoanId(UUID loanId) {

        List<Document> documents = documentRepository.findAllByLoanId(loanId);
        return documents;
    }


    @Override
    public List<DocumentResponseDTO> getAllDocumentsByApplicantId(UUID applicantId) {

        List<Document> documents = documentRepository.findAllByapplicantId(applicantId);
        List<DocumentResponseDTO> documentResponseList = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            DocumentResponseDTO singleDocumentResponseDTO = DocumentResponseDTO.builder().
                    documentId(documents.get(i).getDocumentId())
                    .loanApplication(documents.get(i).getLoanId())
                    .applicant(documents.get(i).getApplicantId())
                    .documentStatus(documents.get(i).getDocumentStatus())
                    .documentType(documents.get(i).getDocumentType())
                    .fileUrl(documents.get(i).getFileUrl())
                    .uploadedBy(documents.get(i).getUploadedBy())
                    .uploadedAt(documents.get(i).getUploadedAt())
                    .verifiedBy(documents.get(i).getVerifiedBy() != null ? documents.get(i).getVerifiedBy() : null)
                    .verifiedAt(documents.get(i).getVerifiedAt())
                    .updatedAt(documents.get(i).getUpdatedAt())
                    .remarks(documents.get(i).getRemarks() != null ? documents.get(i).getRemarks() : null).build();

            documentResponseList.add(singleDocumentResponseDTO);
        }
        return documentResponseList;
    }

    @Override
    public DocumentResponseDTO updateDocument(UUID documentId, DocumentRequestDTO documentRequestDTO) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));

        if (documentRequestDTO.getApplicant() != null) {
            UUID applicantID = UUID.fromString(documentRequestDTO.getApplicant());
            document.setApplicantId(applicantID);
        }

        if (documentRequestDTO.getDocumentType() != null) {
            try {
                document.setDocumentType(DocumentType.valueOf(documentRequestDTO.getDocumentType().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new InvalidDocumentTypeException("The document Type is Invalid");
            }
        }

        documentRepository.save(document);

        return DocumentResponseDTO.builder()
                .documentId(document.getDocumentId())
                .loanApplication(document.getLoanId())
                .applicant(document.getApplicantId())
                .documentStatus(document.getDocumentStatus())
                .documentType(document.getDocumentType())
                .fileUrl(document.getFileUrl())
                .uploadedBy(document.getUploadedBy())
                .uploadedAt(document.getUploadedAt())
                .verifiedBy(document.getVerifiedBy() != null ? document.getVerifiedBy() : null)
                .verifiedAt(document.getVerifiedAt())
                .updatedAt(document.getUpdatedAt())
                .remarks(document.getRemarks() != null ? document.getRemarks() : null).build();
    }

    @Override
    public DocumentResponseDTO updateDocumentFile(UUID documentId, MultipartFile file) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));

        if (!file.getContentType().equals("application/pdf")) {
            throw new DocumentFormatNotAllowedException("Only PDF format is allowed");
        }

//        if(document.getFileUrl().equals(file.) )

        //deleting the previous one
        fileStorageService.deleteFile(document.getFileUrl());
        //uploading the new one
        String filePath = fileStorageService.saveFile(file);
        document.setFileUrl(filePath);
        document.setUploadedAt(LocalDateTime.now());
        documentRepository.save(document);
        return DocumentResponseDTO.builder().
                documentId(document.getDocumentId()).
                loanApplication(document.getLoanId())
                .applicant(document.getApplicantId())
                .documentStatus(document.getDocumentStatus()).
                documentType(document.getDocumentType())
                .fileUrl(document.getFileUrl())
                .uploadedBy(document.getUploadedBy())

                .uploadedAt(document.getUploadedAt())
                .verifiedBy(document.getVerifiedBy() != null ? document.getVerifiedBy() : null)
                .verifiedAt(document.getVerifiedAt())
                .updatedAt(document.getUpdatedAt())
                .remarks(document.getRemarks() != null ? document.getRemarks() : null).build();
    }

    //mainly used by RCU user
    @Override
    public DocumentResponseDTO updateDocumentStatus(String documentId, DocumentStatusRequestDTO documentStatusRequestDTO) {

        Document document = documentRepository.findById(UUID.fromString(documentId)).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));

        if (documentStatusRequestDTO.getVerifiedBy() != null) {
            UUID rcuUser = UUID.fromString(documentStatusRequestDTO.getVerifiedBy());
            document.setVerifiedBy(rcuUser);
        }
        String status = documentStatusRequestDTO.getDocumentStatus();

        if (status == null) {
            throw new IllegalArgumentException("Document status cannot be null");
        }

        document.setDocumentStatus(DocumentStatus.valueOf(status.toUpperCase()));
        if (documentStatusRequestDTO.getRemarks() != null)
            document.setRemarks(documentStatusRequestDTO.getRemarks());

        document.setVerifiedAt(LocalDateTime.now());

        documentRepository.save(document);
        return DocumentResponseDTO.builder()
                .documentId(document.getDocumentId())
                .loanApplication(document.getLoanId())
                .applicant(document.getApplicantId() != null ?
                        document.getApplicantId() : null)
                .documentStatus(document.getDocumentStatus())
                .documentType(document.getDocumentType())
                .fileUrl(document.getFileUrl())
                .uploadedBy(document.getUploadedBy())
                .uploadedAt(document.getUploadedAt())
                .verifiedBy(document.getVerifiedBy() != null ? document.getVerifiedBy() : null)
                .verifiedAt(document.getVerifiedAt())
                .updatedAt(document.getUpdatedAt())
                .remarks(document.getRemarks() != null ? document.getRemarks() : null).build();
    }


    @Transactional
    @Override
    public void deleteAllDocumentsByLoanId(UUID loanId) {
        long count = documentRepository.deleteAllByLoanId(loanId);

        if (count == 0) {
            throw new DocumentNotFoundException("No Documents Found for this loan application");
        }
    }

    @Transactional
    @Override
    public void deleteAllDocumentsByApplicantId(UUID applicantId) {
        long count = documentRepository.deleteAllByApplicantId(applicantId);

        if (count == 0) {
            throw new DocumentNotFoundException("No Document Found for this applicant ");
        }
    }

    @Transactional
    @Override
    public void deleteDocumentsById(UUID documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));
        documentRepository.delete(document);
    }
}
