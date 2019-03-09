package com.expense.service;

import com.expense.exception.NoAuthorizationException;
import com.expense.exception.ResourceNotFoundException;
import com.expense.model.Expense;
import com.expense.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final UserSecurityService userSecurityService;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository,
                        UserSecurityService userSecurityService) {
        this.expenseRepository = expenseRepository;
        this.userSecurityService = userSecurityService;
    }

    public List<Expense> get(String groupId, Date fromDate, Date toDate) {
        List<Expense> expenses = this.expenseRepository
                .findAllByUserAndDateBetween(this.userSecurityService.getMe(), fromDate, toDate);
        if (groupId != null)
            return expenses.stream().filter(expense ->
                    expense.getGroup().getId().equals(groupId)).collect(Collectors.toList());
        return expenses;
    }

    public Expense create(Expense expense) {
        expense.setId(null); // Clear the id to avoid unnecessary update operations
        expense.setUser(this.userSecurityService.getMe());
        return this.expenseRepository.save(expense);
    }

    public Expense update(String id, Expense expense) {
        return this.expenseRepository.findById(id).map(existingExpense -> {
            if (!existingExpense.getUser().equals(this.userSecurityService.getMe()))
                throw new NoAuthorizationException("Expense does not belong to the current user.");
            existingExpense.setTitle(expense.getTitle());
            existingExpense.setDescription(expense.getDescription());
            existingExpense.setDate(expense.getDate());
            existingExpense.setValue(expense.getValue());
            existingExpense.setGroup(expense.getGroup());
            return this.expenseRepository.save(existingExpense);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    public void delete(String id) {
        Expense expense = this.expenseRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (!expense.getUser().equals(this.userSecurityService.getMe()))
            throw new NoAuthorizationException("Expense does not belong to the current user.");
        this.expenseRepository.deleteById(expense.getId());
    }

}
