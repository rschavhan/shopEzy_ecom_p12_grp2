package com.ecombackend.excelr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
	
    private Long id;
    private String reviewerName;
    private String comment;
    private int rating;
    private Long userId;

    // Constructors, getters, and setters
}
