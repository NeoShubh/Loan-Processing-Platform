package com.example.loanapplication.exception.document;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(String msg){
        super(msg);
    }
}
