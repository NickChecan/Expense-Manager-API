package com.expense.service;

import com.expense.exception.NoAuthorizationException;
import com.expense.exception.ResourceNotFoundException;
import com.expense.model.Group;
import com.expense.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserSecurityService userSecurityService;

    @Autowired
    public GroupService(GroupRepository groupRepository,
                        UserSecurityService userSecurityService) {
        this.groupRepository = groupRepository;
        this.userSecurityService = userSecurityService;
    }

    public List<Group> get() {
        return this.groupRepository.findAllByUser(this.userSecurityService.getMe());
    }

    public Group create(Group group) {
        group.setId(null); // Clear the id to avoid unnecessary update operations
        group.setUser(this.userSecurityService.getMe());
        return this.groupRepository.save(group);
    }

    public Group update(String id, Group group) {
        return this.groupRepository.findById(id).map(existingGroup -> {
            if (!existingGroup.getUser().equals(this.userSecurityService.getMe()))
                throw new NoAuthorizationException("Classification does not belong to the current user.");
            existingGroup.setName(group.getName());
            existingGroup.setDescription(group.getDescription());
            return this.groupRepository.save(existingGroup);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    public void delete(String id) {
        Group group = this.groupRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (!group.getUser().equals(this.userSecurityService.getMe()))
            throw new NoAuthorizationException("Classification does not belong to the current user.");
        this.groupRepository.deleteById(group.getId());
    }

}
