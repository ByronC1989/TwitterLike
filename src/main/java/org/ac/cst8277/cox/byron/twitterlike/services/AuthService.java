package org.ac.cst8277.cox.byron.twitterlike.services;

import org.ac.cst8277.cox.byron.twitterlike.repo.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ac.cst8277.cox.byron.twitterlike.beans.User;

import java.util.UUID;

@Service
public class AuthService {

    private final AuthRepo authRepo;

    @Autowired
    public AuthService(AuthRepo authRepos) {
        this.authRepo = authRepos;
    }

    private UUID authToken;

    // Generate Token
    public void generateAuthToken() {
        this.authToken = UUID.randomUUID();
    }

    public boolean hasToken(User user){
        return user.getAuthToken() != null;
    }

    public void authenticate(User user){
        if (!hasToken(user)) {
            generateAuthToken(); //
            user.setAuthToken(authToken.toString());
        }
    }

    // Check Token
    public ResponseEntity<User> authorizeUser(String name, String password) {
        try{
            User authenticatedUser;
            if(authRepo.authorizeUser(name, password) == null){
                authenticatedUser = null;
            } else {
                authenticatedUser = authRepo.authorizeUser(name, password);
            }
            assert authenticatedUser != null;
            if(authenticatedUser.getAuthToken() == null) {
                authenticate(authenticatedUser); // generate and supply user with token.
                authRepo.save(authenticatedUser); // update user in database with generated token.
            }
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            // return error if user not added to database.
        }
    }

    // determine if token is valid
    public boolean isAuthenticated(String token) {
        return authRepo.checkToken(token) != null;
    }

}
