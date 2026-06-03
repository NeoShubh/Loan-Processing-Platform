package com.example.loanapplication.modules.documentmodule.repository;

import com.example.loanapplication.modules.documentmodule.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findAllByLoanId(UUID loanId);
    List<Document> findAllByapplicantId(UUID applicantId);
    Long deleteAllByLoanId(UUID loanId);
    Long deleteAllByApplicantId(UUID applicantId);
}
