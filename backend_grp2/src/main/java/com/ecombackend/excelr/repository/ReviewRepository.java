package com.ecombackend.excelr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecombackend.excelr.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
}
