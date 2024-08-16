package com.ecombackend.excelr.service;

import java.util.List;
import java.util.Optional;

import com.ecombackend.excelr.dto.UserDTO;
import com.ecombackend.excelr.model.User;

public interface UserService {

	boolean existsByEmail(String email);

	void saveUser(User user);

	Optional<User> findByUsername(String username); 

	List<User> getAllUsers();

	void deleteUser(Long id);

	Optional<User> findById(Long id);

	void updateRoleForUser(Long userId, Long newRoleId);


	 Optional<User> findByEmail(String email);

	 UserDTO getUserProfile(Long userId); 
	 UserDTO updateUserProfile(Long userId, UserDTO userDTO);


}
