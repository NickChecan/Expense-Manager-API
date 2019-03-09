package com.expense.controller;

import com.expense.model.Role;
import com.expense.service.RoleService;
import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> get() {
        return new ResponseEntity<>(this.roleService.get(), HttpStatus.OK);
    }

    @JsonView({View.All.class})
    @GetMapping(value = "/{id}")
    public ResponseEntity<Role> get(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.roleService.get(id), HttpStatus.OK);
    }

    @PostMapping
    @JsonView({View.All.class})
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(this.roleService.create(role), HttpStatus.CREATED);
    }

    @JsonView({View.All.class})
    @PutMapping(value = "/{id}")
    public ResponseEntity<Role> update(@PathVariable("id") String id, @RequestBody Role role) {
        return new ResponseEntity<>(this.roleService.update(id, role), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        this.roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
