package com.fsd.librarymanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="book")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not be longer than 255 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 255, message = "Author name must not be longer than 255 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(max = 20, message = "ISBN must not be longer than 20 characters")
    private String isbn;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE; // Status of the book, defaults to AVAILABLE

    // Enum to define possible statuses of a book
    public enum BookStatus {
        AVAILABLE,
        CHECKED_OUT,
        UNKNOWN
    }


}
