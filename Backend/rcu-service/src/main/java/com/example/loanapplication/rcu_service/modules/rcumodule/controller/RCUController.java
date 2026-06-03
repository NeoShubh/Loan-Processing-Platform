package com.example.loanapplication.rcu_service.modules.rcumodule.controller;



import com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.RCUCaseResponseDTO;
import com.example.loanapplication.rcu_service.modules.rcumodule.enums.RCUStatus;
import com.example.loanapplication.rcu_service.modules.rcumodule.service.RCUService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rcu")
public class RCUController {

    private final RCUService rcuService;

    public RCUController(RCUService rcuService) {
        this.rcuService = rcuService;
    }

    //RCU CASE METHODs
    //Done
    @PreAuthorize("hasAnyRole('RCU')")
    @PostMapping("/cases/{loanId}")
    ResponseEntity<RCUCaseResponseDTO> CreateRCUCase(@PathVariable String loanId){
       RCUCaseResponseDTO rcuCaseResponseDTO = rcuService.CreateRCUCase(UUID.fromString(loanId));
        return ResponseEntity.status(HttpStatus.CREATED).body(rcuCaseResponseDTO);
    }
    @PreAuthorize("hasAnyRole('RCU')")
    @GetMapping("/cases/{rcuCaseId}/getCase")
    ResponseEntity<RCUCaseResponseDTO>  getRCUCase(@PathVariable String rcuCaseId){
        RCUCaseResponseDTO rcuCaseResponseDTO = rcuService.getRCUCase(UUID.fromString(rcuCaseId));
        return ResponseEntity.status(HttpStatus.FOUND).body(rcuCaseResponseDTO);
    }
    @PreAuthorize("hasAnyRole('RM', 'RCU')")
    @GetMapping("/loans/{loanID}")
    ResponseEntity<RCUCaseResponseDTO> getRCUCaseByLoanID(@PathVariable UUID loanID){
        RCUCaseResponseDTO rcuCaseResponseDTO = rcuService.getRCUCaseByLoanID(loanID);
        return ResponseEntity.status(HttpStatus.FOUND).body(rcuCaseResponseDTO);
    }
    //Done
    @PreAuthorize("hasAnyRole('RCU')")
    @DeleteMapping("/cases/{rcuCaseId}")
    ResponseEntity<String> DeleteRCUCasebyId(@PathVariable String rcuCaseId){
       rcuService.DeleteRCUCaseById(UUID.fromString(rcuCaseId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted Successfully");
    }
    //Pending
    @PreAuthorize("hasAnyRole('RCU')")
    @PutMapping("/cases/{rcuCaseId}/status")
    ResponseEntity<RCUCaseResponseDTO> updateRCUCaseStatus(@PathVariable String rcuCaseId,@RequestParam String rcuStatus){
       RCUCaseResponseDTO rcuCaseResponseDTO = rcuService.updateRCUCaseStatus(UUID.fromString(rcuCaseId), RCUStatus.valueOf(rcuStatus));
        return ResponseEntity.status(HttpStatus.OK).body(rcuCaseResponseDTO);
    }

    //Done
    @PreAuthorize("hasAnyRole('RCU')")
    @PutMapping("/cases/{rcuCaseId}/assign/{assignedUserId}")
    ResponseEntity<RCUCaseResponseDTO> AssignedRCUCase(@PathVariable String rcuCaseId, @PathVariable String assignedUserId){
    RCUCaseResponseDTO rcuCaseResponseDTO = rcuService.AssignedRCUCase(UUID.fromString(rcuCaseId),UUID.fromString(assignedUserId));
    return ResponseEntity.status(HttpStatus.OK).body(rcuCaseResponseDTO);
    }
    //Done
//    @PostMapping("/cases/{rcuCaseId}/decision")
//    ResponseEntity<String> RCUCaseDecisionMaking(@PathVariable String rcuCaseId){
//        rcuService.RCUCaseDecisionMaking(UUID.fromString(rcuCaseId));
//        return  ResponseEntity.status(HttpStatus.OK).body("Decision Making Done !");
//    }

    //METHODS WHICH ARE MORE RELATED TO DOCUMENTs PART
    //Done
//    @GetMapping("/documents/{documentId}")
//    ResponseEntity<DocumentResponseDTO> getDocument(@PathVariable String documentId){
//        DocumentResponseDTO documentResponseDTO = rcuService.getDocument(documentId);
//        return ResponseEntity.status(HttpStatus.FOUND).body(documentResponseDTO);
//    }
//
//    //Done
//    @PutMapping("/documents/{documentId}/status")
//    ResponseEntity<DocumentResponseDTO> updateDocumentStatusAndRemarks(@PathVariable String documentId,@RequestBody DocumentStatusRequestDTO documentStatusRequestDTO){
//       DocumentResponseDTO documentResponseDTO = rcuService.updateDocumentStatusAndRemarks(documentId,documentStatusRequestDTO);
//        return ResponseEntity.status(HttpStatus.FOUND).body(documentResponseDTO);
//    }
//    //Done
//    @GetMapping("/documents/applicant/{applicantId}")
//    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentByApplicant(@PathVariable String applicantId){
//        List<DocumentResponseDTO> documentResponseDTOList = rcuService.getAllDocumentByApplicant(applicantId);
//        return ResponseEntity.status(HttpStatus.FOUND).body(documentResponseDTOList);
//    }
//    //Done
//    @GetMapping("/loans/{loanId}/documents")
//    ResponseEntity<List<DocumentResponseDTO>> getAllDOcumentByLoanId(@PathVariable String loanId){
//        List<DocumentResponseDTO> documentResponseDTOList = rcuService.getAllDOcumentByLoanId(loanId);
//        return ResponseEntity.status(HttpStatus.FOUND).body(documentResponseDTOList);
//    }

   }