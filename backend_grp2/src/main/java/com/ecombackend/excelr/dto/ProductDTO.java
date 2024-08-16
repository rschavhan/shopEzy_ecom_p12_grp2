package com.ecombackend.excelr.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String imgSrc;
    private String category;
    private String storage;
    private String color;
    private String brand;
    private String size;
    private List<ReviewDTO> reviews; // Include reviews if necessary
    
    
    
    
}

    // Constructors, getters, and setters

