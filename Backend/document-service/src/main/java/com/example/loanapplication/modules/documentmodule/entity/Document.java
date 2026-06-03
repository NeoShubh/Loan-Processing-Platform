package com.example.loanapplication.modules.documentmodule.entity;

import com.example.loanapplication.modules.documentmodule.enums.DocumentStatus;
import com.example.loanapplication.modules.documentmodule.enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID documentId;

    private UUID loanId;

    private UUID applicantId;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus = DocumentStatus.UPLOADED;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String fileUrl;

    private UUID uploadedBy;

    private LocalDateTime uploadedAt;

    private UUID verifiedBy;

    private LocalDateTime verifiedAt;

    private String remarks;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}