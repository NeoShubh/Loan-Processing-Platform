package com.example.loanapplication.exception.document;

public class InvalidDocumentStatusException extends RuntimeException {
    public InvalidDocumentStatusException(String message) {
        super(message);
    }
}
