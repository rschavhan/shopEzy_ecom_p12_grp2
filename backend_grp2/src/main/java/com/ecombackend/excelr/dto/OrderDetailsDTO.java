package com.ecombackend.excelr.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailsDTO {
    private Long id;
    private Double totalAmount;
    private Date orderDate;
    private String status;
    private AddressDTO addressDTO;
    private List<ProductDTO> productsDTO;
    
	public OrderDetailsDTO() {}
	public OrderDetailsDTO(Long id, Double totalAmount, Date orderDate, String status, AddressDTO addressDTO,
			List<ProductDTO> productsDTO) {
		super();
		this.id = id;
		this.totalAmount = totalAmount;
		this.orderDate = orderDate;
		this.status = status;
		this.addressDTO = addressDTO;
		this.productsDTO = productsDTO;
	}
    
    
	
	
}
 