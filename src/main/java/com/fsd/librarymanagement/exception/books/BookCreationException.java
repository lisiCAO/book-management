package com.fsd.librarymanagement.exception.books;

public class BookCreationException extends RuntimeException {
    public BookCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
