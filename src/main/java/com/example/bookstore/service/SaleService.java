package com.example.bookstore.service;

import com.example.bookstore.dto.SaleDTO;

import java.util.List;

public interface SaleService {
    SaleDTO createSale(SaleDTO saleDTO);

    SaleDTO cancelSale(Long id);

    List<SaleDTO> getAllSales();

    List<SaleDTO> getAllSalesByBuyerEmail(String buyerEmail);

    double totalEarnings();
}
