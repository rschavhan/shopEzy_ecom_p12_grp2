package com.ecombackend.excelr.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecombackend.excelr.dto.WishlistDTO;
import com.ecombackend.excelr.mapper.WishlistMapper;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.model.Wishlist;
import com.ecombackend.excelr.repository.ProductRepository;
import com.ecombackend.excelr.repository.UserRepository;
import com.ecombackend.excelr.repository.WishlistRepository;

@Service
public class WishlistService {

	private final WishlistRepository wishlistRepository;
	private final WishlistMapper wishlistMapper;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public WishlistService(WishlistRepository wishlistRepository, WishlistMapper wishlistMapper,
			UserRepository userRepository, ProductRepository productRepository) {
		this.wishlistRepository = wishlistRepository;
		this.wishlistMapper = wishlistMapper;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	public List<WishlistDTO> getWishlistByUser(User user) {
		List<Wishlist> wishlist = wishlistRepository.findByUser(user);
		return wishlist.stream().map(wishlistMapper::toDTO).collect(Collectors.toList());
	}

	public void addProductToWishlist(WishlistDTO wishlistDTO) {
		Wishlist wishlist = wishlistMapper.toEntity(wishlistDTO);
		wishlistRepository.save(wishlist);
	}

	@Transactional
	public void removeProductFromWishlist(Long userId, Long productId) {
		wishlistRepository.deleteByUserIdAndProductId(userId, productId);
	}
}
