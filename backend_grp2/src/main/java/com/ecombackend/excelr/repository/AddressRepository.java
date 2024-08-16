package com.ecombackend.excelr.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecombackend.excelr.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
    @Modifying
    @Query("DELETE FROM Address a WHERE a.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
