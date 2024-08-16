package com.ecombackend.excelr.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecombackend.excelr.dto.ProductDTO;
import com.ecombackend.excelr.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "reviews", source = "reviews") // Optional: if you want to include reviews
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
