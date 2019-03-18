package com.expense.service;

import com.expense.exception.ResourceNotFoundException;
import com.expense.model.Role;
import com.expense.model.User;
import com.expense.repository.RoleRepository;
import com.expense.repository.UserRepository;
import com.expense.security.UserSecurityDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository,
                               RoleRepository roleRepository,
                               @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmailIgnoreCase(username).map(UserSecurityDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
    }

    public User getMe() {
        UserSecurityDetails currentUser = (UserSecurityDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findById(currentUser.getId())
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void signUp(User user) {
        user.setId(null); // Clear the id to avoid unnecessary update operations
        user.setPassword(this.passwordEncoder.encode(user.getPassword())); // Encode password for security reasons
        // Get and set a list of common roles for standard users
        List<Role> commonRoles = new ArrayList<>();
        commonRoles.add(this.roleRepository.findByNameIgnoreCase("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("User common roles not found!")));
        user.setRoles(commonRoles);
        this.userRepository.save(user);
    }

}
