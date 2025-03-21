package com.example.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.bookstore.dto.SaleDTO;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Sale;
import com.example.bookstore.exception.InsufficientStockException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.SaleRepository;
import com.example.bookstore.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SaleServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleServiceImpl saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateSaleSuccess() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setStock(10L);
        SaleDTO dto = new SaleDTO();
        dto.setBook(book);
        dto.setBuyerName("Test Buyer");
        dto.setBuyerEmail("test@example.com");
        dto.setQuantity(3L);


        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Sale sale = new Sale();
        sale.setBook(book);
        sale.setBuyerName(dto.getBuyerName());
        sale.setBuyerEmail(dto.getBuyerEmail());
        sale.setQuantity(dto.getQuantity());
        sale.setCreatedAt(LocalDateTime.now());
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);

        // Act
        SaleDTO createdSale = saleService.createSale(dto);

        // Assert
        assertNotNull(createdSale);
        assertEquals(dto.getQuantity(), createdSale.getQuantity());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(saleRepository, times(1)).save(any(Sale.class));
        assertEquals(7, book.getStock());
    }

    @Test
    void testCreateSaleInsufficientStock() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setStock(10L);
        SaleDTO dto = new SaleDTO();
        dto.setBook(book);
        dto.setBuyerName("Test Buyer");
        dto.setBuyerEmail("test@example.com");
        dto.setQuantity(15L);

        when(bookRepository.findById(dto.getBook().getId())).thenReturn(Optional.of(book));

        // Act & Assert
        Exception exception = assertThrows(InsufficientStockException.class, () -> {
            saleService.createSale(dto);
        });
        assertTrue(exception.getMessage().contains("Not enough stock available"));
    }


    @Test
    void testGetSalesByBuyerEmail() {
        // Arrange
        String email = "test@example.com";
        Sale sale1 = new Sale();
        sale1.setBuyerEmail(email);
        Sale sale2 = new Sale();
        sale2.setBuyerEmail(email);
        List<Sale> salesList = Arrays.asList(sale1, sale2);
        when(saleRepository.findSalesByBuyerEmail(email)).thenReturn(salesList);

        // Act
        List<SaleDTO> result = saleService.getAllSalesByBuyerEmail(email);

        // Assert
        assertEquals(2, result.size());
        for (SaleDTO s : result) {
            assertEquals(email, s.getBuyerEmail());
        }
    }
}
