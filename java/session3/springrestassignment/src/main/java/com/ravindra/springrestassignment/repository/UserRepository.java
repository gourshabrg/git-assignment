package com.ravindra.springrestassignment.repository;

import com.ravindra.springrestassignment.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepository() {
        users.add(new User(1, "Priya", 25, "USER"));
        users.add(new User(2, "Amit", 30, "ADMIN"));
        users.add(new User(3, "Rahul", 30, "USER"));
        users.add(new User(4, "Sneha", 28, "USER"));
        users.add(new User(5, "Karan", 35, "ADMIN"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void deleteUser(int id) {
        users.removeIf(u -> u.getId() == id);
    }
}