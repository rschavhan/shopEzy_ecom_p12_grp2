package com.ecombackend.excelr.model;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
//    @ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne
    private User user;
//    @ManyToOne(cascade = CascadeType.REMOVE) // This will delete cart items when the product is deleted
    @ManyToOne
    @JoinColumn(name = "product_id") 
    private Product product;
    private int quantity;
}
