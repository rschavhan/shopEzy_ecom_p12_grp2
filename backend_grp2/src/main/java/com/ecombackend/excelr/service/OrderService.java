package com.ecombackend.excelr.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecombackend.excelr.dto.AddressDTO;
import com.ecombackend.excelr.dto.OrderDetailsDTO;
import com.ecombackend.excelr.dto.ProductDTO;
import com.ecombackend.excelr.model.Address;
import com.ecombackend.excelr.model.Order;
import com.ecombackend.excelr.model.Product;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.repository.AddressRepository;
import com.ecombackend.excelr.repository.OrderRepository;
import com.ecombackend.excelr.repository.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
    private AddressRepository addressRepository;


    // Admin methods
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        try {
            Order order = orderRepository.findById(orderId).get();
            
//            Hibernate.initialize(order.getAddress());//remove if not work
            order.setStatus(status);
            return orderRepository.save(order);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Order not found", e);
        }
    }

    public void cancelOrder(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId).get();
            orderRepository.delete(order);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Order not found", e);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

 // User-specific methods
    public Order saveOrderForUser(Long userId, Order order) {
        // Find the user by ID
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Find or save the address
        Address address = order.getAddress();
        if (address.getId() == null) {
            address.setUser(user);
            address = addressRepository.save(address);
        }

        // Set the user and address to the order
        order.setUser(user);
        order.setAddress(address);

        // Save and return the order
        return orderRepository.save(order);
    }
    

    public Order updateOrderStatusForUser(Long userId, Long orderId, String status) {
        try {
            Order order = orderRepository.findByIdAndUserId(orderId, userId).get();
            order.setStatus(status);
            return orderRepository.save(order);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Order not found for this user", e);
        }
    }

    public void cancelOrderForUser(Long userId, Long orderId) {
        try {
            Order order = orderRepository.findByIdAndUserId(orderId, userId).get();
            orderRepository.delete(order);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Order not found for this user", e);
        }
    }
    
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    // Convert Order to OrderDetailsDTO using builder pattern
    public OrderDetailsDTO convertToDTO(Order order) {
        return OrderDetailsDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .addressDTO(convertToAddressDTO(order.getAddress()))
                .productsDTO(convertToProductDTOList(order.getProducts()))
                .build();
    }
    
    // Convert Address to AddressDTO using builder pattern (if AddressDTO uses builder)
    private AddressDTO convertToAddressDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }

    // Convert List of Products to List of ProductDTO using builder pattern
    private List<ProductDTO> convertToProductDTOList(List<Product> products) {
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    // Convert Product to ProductDTO using builder pattern
    private ProductDTO convertToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imgSrc(product.getImgSrc())
                .category(product.getCategory())
                .storage(product.getStorage())
                .color(product.getColor())
                .brand(product.getBrand())
                .size(product.getSize())
                .reviews(null) // Handle reviews separately if needed
                .build();
    }

    // Fetch OrderDetailsDTO list for a user by userId using builder pattern
    public List<OrderDetailsDTO> getOrderDetailsByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .map(order -> OrderDetailsDTO.builder()
                        .id(order.getId())
                        .totalAmount(order.getTotalAmount())
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .addressDTO(convertToAddressDTO(order.getAddress()))
                        .productsDTO(convertToProductDTOList(order.getProducts()))
                        .build())
                .collect(Collectors.toList());
    }
    
//    public void deleteOrdersByProductId(Long productId) {
//        List<Order> orders = orderRepository.findByProductId(productId);
//        for (Order order : orders) {
//            // Remove the reference to the address
//            order.setAddress(null);
//            orderRepository.delete(order);
//        }
//    }
}
