package com.expense.repository;

import com.expense.model.Expense;
import com.expense.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findAllByUserAndDateBetween(User user, Date fromDate, Date toDate);

}
