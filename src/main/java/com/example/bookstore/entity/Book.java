package com.example.bookstore.entity;

import com.example.bookstore.dto.BookDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private double price;
    private Long stock;
    private LocalDate publishedDate;
    private String description;

public static Book fromDTO(BookDTO dto) {
    Book book = new Book();
    book.setTitle(dto.getTitle());
    book.setAuthor(dto.getAuthor());
    book.setPublisher(dto.getPublisher());
    book.setIsbn(dto.getIsbn());
    book.setPrice(dto.getPrice());
    book.setStock(dto.getStock());
    book.setPublishedDate(dto.getPublishedDate());
    book.setDescription(dto.getDescription());
    return book;
    }

public BookDTO toDTO() {
    return new BookDTO(
            this.id,
            this.title,
            this.author,
            this.publisher,
            this.isbn,
            this.price,
            this.stock,
            this.publishedDate,
            this.description
        );
    }
}
