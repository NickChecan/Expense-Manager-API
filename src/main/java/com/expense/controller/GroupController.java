package com.expense.controller;

import com.expense.model.Group;
import com.expense.service.GroupService;
import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    @JsonView({View.Main.class})
    public ResponseEntity<List<Group>> get() {
        return new ResponseEntity<>(this.groupService.get(), HttpStatus.OK);
    }

    @PostMapping
    @JsonView({View.Main.class})
    public ResponseEntity<Group> create(@RequestBody Group group) {
        System.out.println("Justice for all");
        return new ResponseEntity<>(this.groupService.create(group), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @JsonView({View.Main.class})
    public ResponseEntity<Group> update(@PathVariable("id") String id, @RequestBody Group group) {
        return new ResponseEntity<>(this.groupService.update(id, group), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        this.groupService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
