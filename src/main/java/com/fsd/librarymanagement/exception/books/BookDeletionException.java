package com.fsd.librarymanagement.exception.books;

public class BookDeletionException extends RuntimeException {
    public BookDeletionException(String message, Exception e) {
        super(message);
    }
}
