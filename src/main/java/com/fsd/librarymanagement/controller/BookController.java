package com.fsd.librarymanagement.controller;

import com.fsd.librarymanagement.entity.Book;
import com.fsd.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService; // Service layer dependency for book operations
    private static final Logger logger = LoggerFactory.getLogger(BookController.class); // Logger for logging

    // Constructor to inject the book service
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /* Views */
    @GetMapping("/showFormForAdd")
    public String showFromForAdd(Model theModel) {
        logger.info("Showing form to add a new book");
        Book theBook = new Book();
        theModel.addAttribute("book", theBook);
        return "books/book-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("bookId") Long theId, Model theModel) {
        logger.info("Showing form to update book with ID {}", theId);
        Book theBook = bookService.getBookById(theId);
        theModel.addAttribute("book", theBook);
        return "books/book-form";
    }

    /* CRUD */
    @GetMapping("/list")
    public String listBooks(Model theModel) {
        logger.info("Listing books");
        List<Book> theBooks = bookService.getAllBooks();
        theModel.addAttribute("books", theBooks);
        logger.info("Books listed successfully");
        return "books/list-books";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book theBook, BindingResult theBindingResult) {
       if(theBindingResult.hasErrors()) {
           logger.warn("Form validation errors while saving book");
           return "/books/book-form";
       }
        if (theBook.getId() == null) {
            logger.info("Adding a new book: {}", theBook);
            bookService.addBook(theBook);
        } else {
            logger.info("Updating book with ID {}: {}", theBook.getId(), theBook);
            bookService.updateBook(theBook.getId(), theBook);
        }
        return "redirect:/books/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bookId") Long theId) {
        logger.info("Deleting book with ID {}", theId);
        bookService.deleteBook(theId);
        return "redirect:/books/list";
    }
}
