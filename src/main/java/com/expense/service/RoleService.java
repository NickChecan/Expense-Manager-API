package com.expense.service;

import com.expense.exception.ResourceNotFoundException;
import com.expense.model.Role;
import com.expense.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> get() {
        return this.roleRepository.findAll();
    }

    public Role get(String id) {
        return this.roleRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Role create(Role role) {
        role.setId(null); // Clear the id to avoid unnecessary update operations
        return this.roleRepository.save(role);
    }

    public Role update(String id, Role role) {
        return this.roleRepository.findById(id).map(existingRole -> {
            existingRole.setName(role.getName());
            return this.roleRepository.save(existingRole);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    public void delete(String id) {
        this.roleRepository.deleteById(id);
    }

}
