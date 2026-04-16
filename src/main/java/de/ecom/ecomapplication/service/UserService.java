package de.ecom.ecomapplication.service;

import de.ecom.ecomapplication.repository.UserRepository;
import de.ecom.ecomapplication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public List<User> fetchAllUsers() {
        return userRepository.findAll();

    }

    public Optional<User> fetchAUser(Long id) {
        return userRepository.findById(id);
    }

    public void addUser(User user) {
        userRepository.save(user);

    }

    public boolean updateUser(Long id, User updatedUser) {
        return  userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }
}
