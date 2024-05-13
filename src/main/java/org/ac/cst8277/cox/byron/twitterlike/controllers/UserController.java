package org.ac.cst8277.cox.byron.twitterlike.controllers;

import org.ac.cst8277.cox.byron.twitterlike.beans.User;
import org.ac.cst8277.cox.byron.twitterlike.services.AuthService;
import org.ac.cst8277.cox.byron.twitterlike.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // Get all Users
    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getUsers(@RequestParam String token) {
        if(authService.isAuthenticated(token)){
            return userService.getUsers(); // return all users from user table.
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // Add a User
    @PostMapping(value = "/user")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user, @RequestParam String token) {
        if(authService.isAuthenticated(token)){
            return userService.createUser(user); // add user to database.
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
