package com.example.bookstore.repository;

import com.example.bookstore.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findSalesByBuyerEmail(String email);
}
