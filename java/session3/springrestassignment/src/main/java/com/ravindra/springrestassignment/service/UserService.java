package com.ravindra.springrestassignment.service;

import com.ravindra.springrestassignment.model.User;
import com.ravindra.springrestassignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> searchUsers(String name, Integer age, String role) {

        return repository.getAllUsers().stream()
                .filter(user ->
                        (name == null || user.getName().equalsIgnoreCase(name)) &&
                        (age == null || user.getAge() == age) &&
                        (role == null || user.getRole().equalsIgnoreCase(role))
                )
                .collect(Collectors.toList());
    }

    public void deleteUser(int id) {
        repository.deleteUser(id);
    }
}