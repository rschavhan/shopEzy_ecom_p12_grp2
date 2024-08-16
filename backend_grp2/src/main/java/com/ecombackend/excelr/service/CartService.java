package com.ecombackend.excelr.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecombackend.excelr.dto.CartDTO;
import com.ecombackend.excelr.dto.ProductDTO;
import com.ecombackend.excelr.model.Cart;
import com.ecombackend.excelr.model.Product;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.repository.CartRepository;
import com.ecombackend.excelr.repository.ProductRepository;
import com.ecombackend.excelr.repository.UserRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CartDTO> getCartItemsForUser(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CartDTO addProductToCart(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cartItem = cartRepository.findByUserAndProduct(user, product);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity()+1 );
        } else {
            cartItem = new Cart();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUser(user);
        }

        cartItem = cartRepository.save(cartItem);
        return convertToDTO(cartItem);
    }

    @Transactional
    public Cart updateCartItemQuantity(Long id, int quantity) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }	
    
    @Transactional
    public void clearCartForUser(Long userId) {
    	System.out.println("Clearing cart for user ID: " + userId);
        cartRepository.deleteByUserId(userId);
    }


    @Transactional
    public void deleteCartItem(Long id) {
        cartRepository.deleteById(id);
    }

    private CartDTO convertToDTO(Cart cart) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(cart.getProduct().getId())
                .name(cart.getProduct().getName())
                .price(cart.getProduct().getPrice())
                .imgSrc(cart.getProduct().getImgSrc())
                .category(cart.getProduct().getCategory())
                .storage(cart.getProduct().getStorage())
                .color(cart.getProduct().getColor())
                .brand(cart.getProduct().getBrand())
                .size(cart.getProduct().getSize())
                .build();

        return CartDTO.builder()
                .id(cart.getId())
                .product(productDTO)
                .userId(cart.getUser().getId())
                .quantity(cart.getQuantity())
                .build();
    }
}
