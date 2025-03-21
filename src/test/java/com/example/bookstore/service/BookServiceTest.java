package com.example.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateBook() {
        // Arrange
        BookDTO dto = new BookDTO();
        dto.setTitle("Test Book");
        dto.setAuthor("Test Author");
        dto.setPrice(10L);
        dto.setIsbn("12345");
        dto.setPublishedDate(LocalDate.now());
        dto.setDescription("Test Description");
        dto.setStock(10L);

        Book savedBook = Book.fromDTO(dto);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // Act
        BookDTO createdBook = bookService.createBook(dto);

        // Assert
        assertNotNull(createdBook);
        assertEquals("Test Book", createdBook.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetBookNotFound() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBook(bookId);
        });
        assertTrue(exception.getMessage().contains("Book not found: " + bookId));
    }


    @Test
    void testListBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Book 1");
        Book book2 = new Book();
        book2.setTitle("Book 2");
        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<BookDTO> result = bookService.listBooks();

        // Assert
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    // Diğer update ve delete testleri de benzer şekilde yazılabilir.
}
