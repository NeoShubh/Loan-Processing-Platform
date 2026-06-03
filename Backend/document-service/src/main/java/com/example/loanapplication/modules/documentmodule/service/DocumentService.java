package com.example.loanapplication.modules.documentmodule.service;



import com.example.loanapplication.modules.documentmodule.dto.DocumentStatusDTO.DocumentStatusRequestDTO;
import com.example.loanapplication.modules.documentmodule.entity.Document;
import org.springframework.web.multipart.MultipartFile;
import com.example.loanapplication.modules.documentmodule.dto.WholeDocuementDTO.DocumentResponseDTO;
import com.example.loanapplication.modules.documentmodule.dto.WholeDocuementDTO.DocumentRequestDTO;
import java.util.List;
import java.util.UUID;

public interface DocumentService {

     DocumentResponseDTO createDocument(MultipartFile file, UUID loanApplicationId, UUID applicantId, String documentType, UUID UploadedBy);

     DocumentResponseDTO getDocumentById(UUID documentId);

     List<DocumentResponseDTO> getAllDocumentsByLoanId(UUID loanId);
     List<Document> getDocumentsByLoanId(UUID loanId);

     List<DocumentResponseDTO> getAllDocumentsByApplicantId(UUID applicantId);

     DocumentResponseDTO updateDocument(UUID documentId, DocumentRequestDTO documentRequestDTO);

     DocumentResponseDTO updateDocumentFile(UUID documentId, MultipartFile file);

     DocumentResponseDTO updateDocumentStatus(String documentId, DocumentStatusRequestDTO documentStatusRequestDTO);

     void deleteAllDocumentsByLoanId(UUID loanId);

     void deleteAllDocumentsByApplicantId(UUID applicantId);

     void deleteDocumentsById(UUID documentId);


}
