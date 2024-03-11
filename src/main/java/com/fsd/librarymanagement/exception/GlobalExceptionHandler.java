package com.fsd.librarymanagement.exception;

import com.fsd.librarymanagement.exception.books.BookCreationException;
import com.fsd.librarymanagement.exception.books.BookDeletionException;
import com.fsd.librarymanagement.exception.books.BookNotFoundException;
import com.fsd.librarymanagement.exception.books.BookUpdateException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ModelAndView handleBookNotFoundException(BookNotFoundException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/error/error");     // Set view to 'error/error'
        String referer = request.getHeader("Referer");                 // Get the referer URL from the request
        mav.addObject("message", ex.getMessage());            // Add the exception message to the view
        mav.addObject("returnUrl", referer);                  // Add the return URL to the view
        return mav;                                                        // Return the ModelAndView
    }

    @ExceptionHandler({BookCreationException.class, BookUpdateException.class})
    public String handleBookOperationException(RuntimeException ex, Model model){
        model.addAttribute("error", ex.getMessage()); // Add the exception message to the model
        return "books/book-form";
    }

    @ExceptionHandler(BookDeletionException.class)
    public String handleBookDeletionException(BookDeletionException ex, Model model){
        model.addAttribute("error", ex.getMessage());
        return "books/list-books"; // Return the view name 'books/list-books'
    }
}
