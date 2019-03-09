package com.expense.repository;

import com.expense.model.Group;
import com.expense.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    List<Group> findAllByUser(User user);

}
