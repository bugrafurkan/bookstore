package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Book;

import java.util.List;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO);

    BookDTO getBook(Long id);

    List<BookDTO> listBooks();

    BookDTO updateBook(Long id, BookDTO dto);

    void deleteBook(Long id);
}
