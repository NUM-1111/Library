package com.atmd.library.exception;

public class DuplicateIsbnException extends RuntimeException{
    public DuplicateIsbnException(String message){
        super(message);
    }
}
