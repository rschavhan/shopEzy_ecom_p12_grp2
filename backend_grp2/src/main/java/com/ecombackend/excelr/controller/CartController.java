package com.ecombackend.excelr.controller;

import com.ecombackend.excelr.dto.CartDTO;
import com.ecombackend.excelr.model.Cart;
import com.ecombackend.excelr.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public List<CartDTO> getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartItemsForUser(userId);
    }

    @PostMapping("/add")
    public CartDTO addProductToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.addProductToCart(userId, productId, quantity);
    }

    @PutMapping("/{id}")
    public Cart updateCartItemQuantity(@PathVariable Long id, @RequestParam int quantity) {
        return cartService.updateCartItemQuantity(id, quantity);
    }
    
    @DeleteMapping("/{id}")
    public void removeCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
    }
    
    @DeleteMapping("/user/{userId}")
    public void clearCartForUser(@PathVariable Long userId) {
        cartService.clearCartForUser(userId);
    }
}
