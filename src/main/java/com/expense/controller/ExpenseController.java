package com.expense.controller;

import com.expense.model.Expense;
import com.expense.service.ExpenseService;
import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    @JsonView({View.Main.class})
    public ResponseEntity<List<Expense>> get(
            @RequestParam(value = "groupId", required = false) String groupId,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate
    ) {
        return new ResponseEntity<>(this.expenseService.get(groupId, fromDate, toDate), HttpStatus.OK);
    }

    @PostMapping
    @JsonView({View.Main.class})
    public ResponseEntity<Expense> create(@RequestBody Expense expense) {
        return new ResponseEntity<>(this.expenseService.create(expense), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @JsonView({View.Main.class})
    public ResponseEntity<Expense> update(@PathVariable("id") String id, @RequestBody Expense expense) {
        return new ResponseEntity<>(this.expenseService.update(id, expense), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        this.expenseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

