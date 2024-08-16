package com.ecombackend.excelr.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecombackend.excelr.dto.ProductDTO;
import com.ecombackend.excelr.dto.ProductRequest;
import com.ecombackend.excelr.mapper.ProductMapper;
import com.ecombackend.excelr.model.Product;
import com.ecombackend.excelr.repository.CartRepository;
import com.ecombackend.excelr.repository.OrderRepository;
import com.ecombackend.excelr.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
//    @Autowired
    OrderRepository orderRepository;


    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                       .map(productMapper::toDTO)
                       .collect(Collectors.toList());
    }

    


//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct1(Long id) {
        productRepository.deleteById(id);
    }
    
    @Transactional
    public void deleteProduct(Long productId) {
        cartRepository.deleteByProductId(productId); // Delete related cart items

//        productRepository.disableForeignKeyChecks();
        productRepository.deleteById(productId);
//        productRepository.enableForeignKeyChecks();
    }

    public Product addProduct(ProductRequest productRequest) {
        if (productRepository.existsByName(productRequest.getName())) {
            throw new RuntimeException("Product with this name already exists.");
        }

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setImgSrc(productRequest.getImgSrc());
        product.setCategory(productRequest.getCategory());
        product.setStorage(productRequest.getStorage());
        product.setColor(productRequest.getColor());
        product.setBrand(productRequest.getBrand());
        product.setSize(productRequest.getSize());

        return productRepository.save(product);
    }
}
