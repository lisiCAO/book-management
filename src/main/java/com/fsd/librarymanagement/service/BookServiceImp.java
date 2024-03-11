package com.fsd.librarymanagement.service;

import com.fsd.librarymanagement.DAO.BookRepository;
import com.fsd.librarymanagement.entity.Book;
import com.fsd.librarymanagement.exception.books.BookCreationException;
import com.fsd.librarymanagement.exception.books.BookDeletionException;
import com.fsd.librarymanagement.exception.books.BookNotFoundException;
import com.fsd.librarymanagement.exception.books.BookUpdateException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import java.util.List;

@Service
public class BookServiceImp implements BookService{

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImp.class);

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(Book book)
    {
        logger.info("Adding new book with title: {}", book.getTitle());
        try{
            bookRepository.save(book);
        }catch (Exception e) {
            String errorMessage = "Failed to add book with title: " + book.getTitle();
            logger.warn(errorMessage, e);
            throw new BookCreationException(errorMessage, e);
        }
    }

    @Override
    public List<Book> getAllBooks()
    {
        logger.info("Fetching all books from the database");
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            String errorMessage = "Failed to fetch all books";
            logger.warn(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public Book getBookById(Long id)
    {
        logger.info("fetch a book with ID:{}", id);
        return bookRepository.findById(id).orElseThrow(() -> {
            logger.warn("Book not found with ID: {}", id);
            return new BookNotFoundException("Book not found with ID" + id);
        });
    }

    @Override
    public void updateBook(Long id, Book bookDetails)
    {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setStatus(bookDetails.getStatus());
            try {
                bookRepository.save(book);
            } catch (Exception e) {
                String errorMessage = "Failed to update book with ID: " + id;
                logger.warn(errorMessage, e);
                throw new BookUpdateException(errorMessage, e);
            }
        } else {
            logger.warn("Attempted to update a non-existing book with ID: {}", id);
            throw new BookNotFoundException("Cannot update, book not found with ID " + id);
        }
    }

    @Override
    public void deleteBook(Long id)
    {
        logger.info("Deleting book with ID: {}", id);
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            String errorMessage ="Failed to delete book with ID: " + id;
            logger.warn(errorMessage, e);
            throw new BookDeletionException(errorMessage, e);
        }
    }
}
