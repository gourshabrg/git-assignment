package com.ravindra.springrestassignment.controller;

import com.ravindra.springrestassignment.model.User;
import com.ravindra.springrestassignment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String role) {

        return ResponseEntity.ok(service.searchUsers(name, age, role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable int id,
            @RequestParam(required = false) Boolean confirm) {

        if (confirm == null || !confirm) {
            return ResponseEntity.badRequest().body("Confirmation required");
        }

        service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitUser(@RequestBody User user) {

        if (user.getName() == null || user.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        return ResponseEntity.status(201).body("User submitted successfully");
    }
}