package com.ecombackend.excelr.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecombackend.excelr.dto.AddressDTO;
import com.ecombackend.excelr.model.Address;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.service.AddressService;
import com.ecombackend.excelr.service.UserService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;
    
    @Autowired
    private UserService userService;

    // Convert Address to AddressDTO
    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.getUser() != null ? address.getUser().getId() : null
        );
    }

    // Convert AddressDTO to Address
    private Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setAddressLine1(addressDTO.getAddressLine1());
        address.setAddressLine2(addressDTO.getAddressLine2());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCountry(addressDTO.getCountry());
        return address;
    }

    // Get all addresses for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDTO>> getAddressesByUser(@PathVariable Long userId) {
        List<Address> addresses = addressService.getAddressesByUser(userId);
        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(addressDTOs, HttpStatus.OK);
    }

    // Add a new address
    @PostMapping
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        if (addressDTO.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Return bad request if userId is missing
        }

        Address address = convertToEntity(addressDTO);

        // Fetch user by ID and handle Optional<User>
        Optional<User> userOptional = userService.findById(addressDTO.getUserId());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get(); // Get the user
            address.setUser(user); // Set the user in the address
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Handle user not found
        }
        
        Address savedAddress = addressService.addAddress(address);
        AddressDTO savedAddressDTO = convertToDTO(savedAddress);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }


    // Update an existing address
    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        address.setId(id);
        Address updatedAddress = addressService.updateAddress(id, address);
        if (updatedAddress != null) {
            AddressDTO updatedAddressDTO = convertToDTO(updatedAddress);
            return new ResponseEntity<>(updatedAddressDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        boolean isDeleted = addressService.deleteAddress(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
