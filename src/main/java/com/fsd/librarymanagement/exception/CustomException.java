package com.fsd.librarymanagement.exception;

public class CustomException extends RuntimeException{

    // Constructor that takes a message and passes it to the RuntimeException constructor
    public CustomException(String message) {
        super(message);
    }
}
