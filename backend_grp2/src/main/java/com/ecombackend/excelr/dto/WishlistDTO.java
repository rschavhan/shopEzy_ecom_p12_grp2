package com.ecombackend.excelr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishlistDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private String name; // Product name
    private String imgSrc; // Product image source
    private Double price; // Product price
    // getters and setters
}
