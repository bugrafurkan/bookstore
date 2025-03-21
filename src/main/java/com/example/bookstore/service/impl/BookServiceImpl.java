package com.example.bookstore.service.impl;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = Book.fromDTO(bookDTO);
        Book savedBook = bookRepository.save(book);
        return savedBook.toDTO();
    }

    @Override
    public BookDTO getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));

        return book.toDTO();
    }

    @Override
    public List<BookDTO> listBooks() {
        return bookRepository.findAll().stream()
                .map(Book::toDTO).
                collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book updatedBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
        updatedBook.setTitle(dto.getTitle());
        updatedBook.setAuthor(dto.getAuthor());
        updatedBook.setPrice(dto.getPrice());
        //eksikleri de ekle
        Book savedBook = bookRepository.save(updatedBook);

        return savedBook.toDTO();
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
        bookRepository.delete(book);
    }
}
