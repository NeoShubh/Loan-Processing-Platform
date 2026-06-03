package com.example.loanapplication.exception.document;

public class DocumentFormatNotAllowedException extends RuntimeException {
    public DocumentFormatNotAllowedException(String message) {
        super(message);
    }
}
