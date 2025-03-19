package com.example.bookstore.dto;

import com.example.bookstore.entity.Book;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long id;
    private Book book;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean canceled = false;
}