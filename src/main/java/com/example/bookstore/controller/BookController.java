package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDto) {
        BookDTO createdBook = bookService.createBook(bookDto);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // [GET] /api/v1/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO>  getBook(@PathVariable Long id) {
        BookDTO book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    // [GET] /api/v1/books
    @GetMapping
    public ResponseEntity<List<BookDTO>>  listBooks() {
        List<BookDTO> books = bookService.listBooks();
        return ResponseEntity.ok(books);
    }

    // [PUT] /api/v1/books/{id}
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO>  updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id,bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    // [DELETE] /api/v1/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
