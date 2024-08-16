package com.ecombackend.excelr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecombackend.excelr.model.Address;
import com.ecombackend.excelr.model.Order;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.repository.AddressRepository;
import com.ecombackend.excelr.repository.OrderRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    public List<Address> getAddressesByUser(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address updatedAddress) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            address.setAddressLine1(updatedAddress.getAddressLine1());
            address.setAddressLine2(updatedAddress.getAddressLine2());
            address.setCity(updatedAddress.getCity());
            address.setState(updatedAddress.getState());
            address.setPostalCode(updatedAddress.getPostalCode());
            address.setCountry(updatedAddress.getCountry());
            return addressRepository.save(address);
        } else {
            return null; // Could also throw a custom exception here
        }
    }

    public boolean deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    
    public Address getAddressByOrderId(Long orderId) {
        // Assuming your Order entity has an Address reference or can be used to find the Address
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        User user = order.getUser(); // Get the user from the order

        // Assuming the address is associated with the user and you need to find the correct one
        List<Address> addresses = addressRepository.findByUserId(user.getId());
        
        // Return the first address found for simplicity (you might need a more complex logic here)
        if (addresses.isEmpty()) {
            throw new RuntimeException("No address found for the user associated with the order");
        }

        return addresses.get(0); // Or implement logic to select the correct address
    }

    
}
