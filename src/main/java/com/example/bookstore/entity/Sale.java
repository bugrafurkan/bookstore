package com.example.bookstore.entity;

import jakarta.validation.constraints.Min;
import com.example.bookstore.dto.SaleDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotBlank(message = "Buyer name cannot be empty")
    private String buyerName;

    @NotBlank(message = "Buyer email cannot be empty")
    private String buyerEmail;

    @NotBlank(message = "Buyer phone cannot be empty")
    private String buyerPhone;
    @Min(value = 1, message = "Quantity must be at least 1")
    private long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean canceled = false;

    public static Sale fromDTO(SaleDTO dto) {
        Sale sale = new Sale();
        BeanUtils.copyProperties(dto, sale);
        return sale;
    }

    public SaleDTO toDTO() {
        return new SaleDTO(
                this.id,
                this.book,
                this.buyerName,
                this.buyerEmail,
                this.buyerPhone,
                this.quantity,
                this.createdAt,
                this.createdAt,
                this.canceled
                );
    }
}
