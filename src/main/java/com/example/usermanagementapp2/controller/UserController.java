package com.example.usermanagementapp2.controller;

import com.example.usermanagementapp2.entity.User;
import com.example.usermanagementapp2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(userService.saveUser(user));
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // Get current user details
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        Optional<User> user = userService.findUserByUsername(principal.getName());
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update current user details
    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@Valid @RequestBody User updatedUser, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Optional<User> user = userService.findUserByUsername(principal.getName());
        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setUsername(updatedUser.getUsername());
            currentUser.setPassword(updatedUser.getPassword());  // Consider encrypting the password here
            userService.updateUser(currentUser);
            return ResponseEntity.ok(currentUser);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
