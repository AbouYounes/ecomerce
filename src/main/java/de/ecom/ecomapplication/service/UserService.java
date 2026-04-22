package de.ecom.ecomapplication.service;

import de.ecom.ecomapplication.dto.AddressDTO;
import de.ecom.ecomapplication.dto.UserRequest;
import de.ecom.ecomapplication.dto.UserResponse;
import de.ecom.ecomapplication.model.Address;
import de.ecom.ecomapplication.repository.UserRepository;
import de.ecom.ecomapplication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchAUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse);
    }

    public void addUser(UserRequest userRequest) {
        userRepository.save(mapFromRequest(userRequest));
    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest) {
        return  userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(mapFromRequest(updatedUserRequest).getFirstName());
                    existingUser.setLastName(mapFromRequest(updatedUserRequest).getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToResponse(User user){
        UserResponse userResponseList = new UserResponse();
        userResponseList.setId(String.valueOf(user.getId()));
        userResponseList.setFirstName(user.getFirstName());
        userResponseList.setLastName(user.getLastName());
        userResponseList.setEmail(user.getEmail());
        userResponseList.setPhone(user.getPhone());
        userResponseList.setRole(user.getRole());
        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setZip(user.getAddress().getZip());
            addressDTO.setCountry(user.getAddress().getCountry());
            userResponseList.setAddress(addressDTO);
        }
        return userResponseList;
    }

    private User mapFromRequest(UserRequest userRequest){
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZip(userRequest.getAddress().getZip());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
        return  user;
    }
}
