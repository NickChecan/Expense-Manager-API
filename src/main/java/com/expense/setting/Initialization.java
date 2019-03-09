package com.expense.setting;

import com.expense.exception.ResourceNotFoundException;
import com.expense.model.Role;
import com.expense.model.User;
import com.expense.repository.RoleRepository;
import com.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Initialization implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Initialization(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        /* Set application administrator role */
        if(!this.roleRepository.findByNameIgnoreCase("ROLE_ADMIN").isPresent())
            roleRepository.save(new Role("ROLE_ADMIN"));

        /* Set application common user role */
        if(!this.roleRepository.findByNameIgnoreCase("ROLE_USER").isPresent())
            roleRepository.save(new Role("ROLE_USER"));

        /* Application administrator user creation */
        if(!this.userRepository.findByEmailIgnoreCase("nicholas.checan@gmail.com").isPresent()) {
            User admin = new User();
            List<Role> adminRoles = new ArrayList<>();
            admin.setEmail("nicholas.checan@gmail.com");
            admin.setPassword(this.passwordEncoder.encode("abc123"));
            adminRoles.add(this.roleRepository.findByNameIgnoreCase("ROLE_ADMIN")
                    .orElseThrow(() -> new ResourceNotFoundException("Administrator role not found!")));
            admin.setRoles(adminRoles);
            this.userRepository.save(admin);
        }

    }

}
