package com.example.bookstore.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private double price;
    private Long stock;
    private LocalDate publishedDate;
    private String description;

}
