package com.expense.controller;

import com.expense.model.User;
import com.expense.service.UserSecurityService;
import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessController {

    private final UserSecurityService userSecurityService;

    @Autowired
    public AccessController(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @JsonView({View.Main.class})
    @PostMapping(value = "/signup")
    public ResponseEntity signUp(@RequestBody User user) {
        this.userSecurityService.signUp(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/me")
    @JsonView({View.Main.class})
    public ResponseEntity<User> getMe() {
        return new ResponseEntity<>(this.userSecurityService.getMe(), HttpStatus.OK);
    }

}
