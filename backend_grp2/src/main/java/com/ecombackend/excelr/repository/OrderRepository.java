package com.ecombackend.excelr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecombackend.excelr.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUserId(Long userId);
//    @Modifying
//    @Query("DELETE FROM Order o WHERE o.product.id = :productId")
//    void deleteReferencesByProductId(@Param("productId") Long productId);



    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
    List<Order> findAll();
    @Modifying
    @Query("DELETE FROM Order o WHERE o.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

//	List<Order> findByProductId(Long productId);

}

