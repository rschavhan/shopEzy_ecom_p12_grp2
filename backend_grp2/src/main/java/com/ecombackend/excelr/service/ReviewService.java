package com.ecombackend.excelr.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecombackend.excelr.dto.ReviewDTO;
import com.ecombackend.excelr.model.Product;
import com.ecombackend.excelr.model.Review;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.repository.ProductRepository;
import com.ecombackend.excelr.repository.ReviewRepository;
import com.ecombackend.excelr.repository.UserRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ReviewDTO addReview(Long productId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setReviewerName(reviewDTO.getReviewerName());
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setProduct(product);
        review.setUser(user);

        Review savedReview = reviewRepository.save(review);
        return new ReviewDTO(savedReview.getId(), savedReview.getReviewerName(), savedReview.getComment(), 
                             savedReview.getRating(), savedReview.getUser().getId());
    }

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
            .map(review -> new ReviewDTO(review.getId(), review.getReviewerName(), review.getComment(), 
                                         review.getRating(), review.getUser().getId()))
            .collect(Collectors.toList());
    }
}
