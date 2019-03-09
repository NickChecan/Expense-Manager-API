package com.expense.controller;

import com.expense.model.User;
import com.expense.service.UserService;
import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> get() {
        return new ResponseEntity<>(this.userService.get(), HttpStatus.OK);
    }

    @JsonView({View.All.class})
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> get(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.userService.get(id), HttpStatus.OK);
    }

    @PostMapping
    @JsonView({View.All.class})
    public ResponseEntity<User> create(@RequestBody User user) {
        return new ResponseEntity<>(this.userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping
    @JsonView({View.All.class})
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody User user) {
        return new ResponseEntity<>(this.userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        this.userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
