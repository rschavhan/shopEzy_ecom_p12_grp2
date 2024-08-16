package com.ecombackend.excelr.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecombackend.excelr.dto.ProductDTO;
import com.ecombackend.excelr.dto.ProductRequest;
import com.ecombackend.excelr.dto.ReviewDTO;
import com.ecombackend.excelr.mapper.ProductMapper;
import com.ecombackend.excelr.model.Product;
import com.ecombackend.excelr.model.Review;
import com.ecombackend.excelr.repository.ProductRepository;
import com.ecombackend.excelr.repository.ReviewRepository;
import com.ecombackend.excelr.service.ProductService;
import com.ecombackend.excelr.service.ReviewService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ProductRepository productRepository;
	
    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductMapper productMapper;


    @PostMapping("/{productId}/reviews")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long productId, @RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewDTO createdReview = reviewService.addReview(productId, reviewDTO);
            return ResponseEntity.ok(createdReview);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        List<ReviewDTO> reviewDTOs = reviews.stream()
            .map(review -> new ReviewDTO(review.getId(), review.getReviewerName(), review.getComment(), review.getRating(), null)) // Set userId to null
            .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTOs);
    }


//    @GetMapping
//    public ResponseEntity<List<ProductDTO>> getAllProducts() {
//        // Retrieve the list of Product entities from the service
//        List<Product> products = productService.getAllProducts();
//
//        // Convert the list of Product entities to ProductDTOs
//        List<ProductDTO> productDTOs = products.stream()
//            .map(productMapper::toDTO)
//            .collect(Collectors.toList());
//
//        // Return the list of ProductDTOs in the response
//        return ResponseEntity.ok(productDTOs);
//    }


@GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                       .map(productMapper::toDTO)
                       .toList(); // Using Stream.toList()
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductDTO productDTO = productMapper.toDTO(product);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductRequest productRequest) {
        try {
            Product product = productService.addProduct(productRequest);
            ProductDTO productDTO = productMapper.toDTO(product);
            return ResponseEntity.ok(productDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            Product product = productMapper.toEntity(productDTO);
            product.setId(id);
            Product updatedProduct = productService.saveProduct(product);
            ProductDTO updatedProductDTO = productMapper.toDTO(updatedProduct);
            return ResponseEntity.ok(updatedProductDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
