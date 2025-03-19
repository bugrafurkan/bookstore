package com.example.bookstore.service.impl;

import com.example.bookstore.dto.SaleDTO;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Sale;
import com.example.bookstore.exception.InsufficientStockException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.SaleRepository;
import com.example.bookstore.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final BookRepository bookRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, BookRepository bookRepository) {
        this.saleRepository = saleRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public SaleDTO createSale(SaleDTO saleDTO) {
        Book book = bookRepository.findById(saleDTO.getBook().getId()).orElse(null);
        if(book == null){
            throw new ResourceNotFoundException("Book not found");
        }
        if(saleDTO.getQuantity() > book.getStock()){
            throw new InsufficientStockException("Not enough stock available. Available stock: " + book.getStock());
        }

        Long newStock = book.getStock() - saleDTO.getQuantity();
        book.setStock(newStock);
        bookRepository.save(book);
        saleDTO.setBook(book);

        Sale sale = Sale.fromDTO(saleDTO);
        Sale savedSale = saleRepository.save(sale);
        return savedSale.toDTO();
    }

    @Override
    public SaleDTO cancelSale(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found: " + saleId));

        if (sale.isCanceled()) {
            throw new IllegalStateException("Sale is already cancelled");
        }

        Book book = bookRepository.findById(sale.getBook().getId()).orElse(null);
        if(book != null){
            book.setStock(book.getStock() + sale.getQuantity());
            bookRepository.save(book);
        }
        sale.setBook(book);
        sale.setCanceled(true);
        saleRepository.save(sale);

        return sale.toDTO();
    }

    @Override
    public List<SaleDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(Sale::toDTO).
                collect(Collectors.toList());
    }

    @Override
    public List<SaleDTO> getAllSalesByBuyerEmail(String buyerEmail) {
        List<Sale> sales = saleRepository.findSalesByBuyerEmail(buyerEmail);
        return sales.stream().map(Sale::toDTO).collect(Collectors.toList());
    }

    @Override
    public double totalEarnings(){
        double total = 0;
        List<Sale> sales = saleRepository.findAll();
        for (Sale sale : sales) {
            if (!sale.isCanceled()) {
                total += sale.getQuantity() * sale.getBook().getPrice();
            }
        }
        return total;
    }
}
