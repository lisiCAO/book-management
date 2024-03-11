package com.fsd.librarymanagement.service;

import com.fsd.librarymanagement.entity.Book;


import java.util.List;


public interface BookService {
    void addBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Long id);
    void updateBook(Long id, Book book);
    void deleteBook(Long id);
}
