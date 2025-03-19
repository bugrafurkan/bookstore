package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDTO {
    private Long bookId;
    private String buyerName;
    private String buyerEmail;
    private String quantity;
}
