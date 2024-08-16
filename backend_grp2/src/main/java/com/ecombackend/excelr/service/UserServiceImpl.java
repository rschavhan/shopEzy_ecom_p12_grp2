package com.ecombackend.excelr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecombackend.excelr.dto.UserDTO;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	JdbcTemplate jdbcTemplate;
    
	@Autowired
    private UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @Override
//    @Transactional
//    public void deleteUser(Long id) {
//       
//        // Finally, delete the user
//        userRepository.deleteById(id);
//    }

    
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Remove addresses associated with the user
        user.getAddresses().clear();
        
        // Now delete the user
        userRepository.delete(user);
    }
    
    
    
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateRoleForUser(Long userId, Long newRoleId) {
        String sql = "UPDATE user_roles SET role_id = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, newRoleId, userId);
    }


	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		 return userRepository.findByEmail(email);
	}


	public UserDTO getUserProfile(Long userId) {
        // Find the user by ID from the repository
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        // Convert the User entity to UserDTO to return only necessary fields
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());

        return userDTO;
    }
	
	@Override
	@Transactional
	public UserDTO updateUserProfile(Long userId, UserDTO userDTO) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
	    
	    user.setUsername(userDTO.getUsername());
	    user.setFirstName(userDTO.getFirstName());
	    user.setLastName(userDTO.getLastName());
	    user.setEmail(userDTO.getEmail());
	    user.setMobileNumber(userDTO.getMobileNumber());
	    
	    userRepository.save(user);

	    return new UserDTO(user); // Assuming UserDTO has a constructor that takes a User object
	}

}
