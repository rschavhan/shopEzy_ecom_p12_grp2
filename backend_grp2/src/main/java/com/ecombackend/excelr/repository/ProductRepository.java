package com.ecombackend.excelr.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecombackend.excelr.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByName(String name);
	
	@Modifying
	@Transactional
	@Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
	void disableForeignKeyChecks();

	@Modifying
	@Transactional
	@Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
	void enableForeignKeyChecks();

}
