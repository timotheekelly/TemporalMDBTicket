package org.mongodb.services;

import org.mongodb.models.User;
import org.mongodb.repositories.UserRepository;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Integer createUser(String firstName, String lastName, String title) {
        int nextUserId = repository.getNextUserId();
        User newUser = new User();
        newUser.setUserId(nextUserId);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setTitle(title);

        repository.createUser(newUser);
        return nextUserId;
    }

    public Integer getUserId(String firstName, String lastName) {
        return repository.getUserIdByName(firstName, lastName);
    }
}

