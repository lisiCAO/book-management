package com.fsd.librarymanagement.exception.books;

public class BookUpdateException extends RuntimeException{
    public BookUpdateException(String message, Exception e) {
        super(message);
    }
}
