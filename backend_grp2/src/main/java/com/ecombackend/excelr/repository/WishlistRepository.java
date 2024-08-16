package com.ecombackend.excelr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecombackend.excelr.model.Product;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
	Optional<Wishlist> findByUserAndProduct(User user, Product product);
    List<Wishlist> findByUser(User user);
    
    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.user.id = :userId AND w.product.id = :productId")
    void deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

}
