package com.example.bookstore.controller;

import com.example.bookstore.dto.SaleDTO;
import com.example.bookstore.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sales")
public class SaleController {
    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        SaleDTO saleBook = saleService.createSale(saleDTO);
        return new ResponseEntity<>(saleBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<SaleDTO> cancelSale(@PathVariable Long id) {
        SaleDTO cancelledBook = saleService.cancelSale(id);
        return new ResponseEntity<>(cancelledBook, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SaleDTO>> findAllSales() {
        List<SaleDTO> sales = saleService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SaleDTO>> findSalesByBuyerEmail(@RequestParam  String buyerMail) {
        List<SaleDTO> salesForBuyer = saleService.getAllSalesByBuyerEmail(buyerMail);
        return ResponseEntity.ok(salesForBuyer);
    }

    @GetMapping("/totalEarnings")
    public double findSalesByTotalEarnings() {
        return saleService.totalEarnings();
    }
}
