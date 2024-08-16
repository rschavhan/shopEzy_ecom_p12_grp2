package com.ecombackend.excelr.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ecombackend.excelr.dto.WishlistDTO;
import com.ecombackend.excelr.model.Wishlist;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    @Mappings({
        @Mapping(target = "userId", source = "user.id"),
        @Mapping(target = "productId", source = "product.id"),
        @Mapping(target = "name", source = "product.name"),
        @Mapping(target = "imgSrc", source = "product.imgSrc"), // Use the correct property name
        @Mapping(target = "price", source = "product.price")
    })
    WishlistDTO toDTO(Wishlist wishlist);

    @Mappings({
        @Mapping(target = "user.id", source = "userId"),
        @Mapping(target = "product.id", source = "productId")
    })
    Wishlist toEntity(WishlistDTO wishlistDTO);
}
