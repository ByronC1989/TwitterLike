package org.ac.cst8277.cox.byron.twitterlike.services;

import org.ac.cst8277.cox.byron.twitterlike.beans.User;
import org.ac.cst8277.cox.byron.twitterlike.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {


    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    // Get all Users
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK); // return all users from user table.
    }

    // Add a User
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) {
        try{
            User registeredUser = userRepo.save(user); // insert user into database.
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            // return error if user not added to database.
        }
    }
}
