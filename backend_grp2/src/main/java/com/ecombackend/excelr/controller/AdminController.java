package com.ecombackend.excelr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecombackend.excelr.dto.ProductDTO;
import com.ecombackend.excelr.dto.UserRoleUpdateRequest;
import com.ecombackend.excelr.mapper.ProductMapper;
import com.ecombackend.excelr.model.Product;
import com.ecombackend.excelr.model.User; // <-- Import your custom User class
import com.ecombackend.excelr.repository.ProductRepository;
import com.ecombackend.excelr.service.ProductService;
import com.ecombackend.excelr.service.RoleService;
import com.ecombackend.excelr.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;
	
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;

//    @GetMapping("/products")
//    public String getAllProducts() {
//    	List<Product> allProducts = productService.getAllProducts();
//        return "Returning :"+allProducts;
//    }
    

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                       .map(productMapper::toDTO)
                       .toList(); // Using Stream.toList()
    }


    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @PutMapping("/users/update-role")
    public ResponseEntity<String> updateUserRole(@RequestBody UserRoleUpdateRequest request) {
        try {
            userService.updateRoleForUser(request.getUserId(), request.getNewRoleId());
            return ResponseEntity.ok("Role updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating role");
        }
    }

}
