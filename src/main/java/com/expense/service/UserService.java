package com.expense.service;

import com.expense.exception.ResourceNotFoundException;
import com.expense.model.User;
import com.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> get() {
        return this.userRepository.findAll();
    }

    public User get(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public User create(User user) {
        user.setId(null); // Clear the id to avoid unnecessary update operations
        user.setPassword(this.passwordEncoder.encode(user.getPassword())); // Encode password for security reasons
        return this.userRepository.save(user);
    }

    public User update(String id, User user) {
        return this.userRepository.findById(id).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
            existingUser.setRoles(user.getRoles());
            return this.userRepository.save(existingUser);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    public void delete(String id) {
        this.userRepository.deleteById(id);
    }

}
