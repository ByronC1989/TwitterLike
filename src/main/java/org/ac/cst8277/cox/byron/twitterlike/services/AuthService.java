package org.ac.cst8277.cox.byron.twitterlike.services;

import org.ac.cst8277.cox.byron.twitterlike.repo.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.ac.cst8277.cox.byron.twitterlike.beans.User;

import java.time.LocalDateTime;
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

    public ResponseEntity<User> getGitHubToken(OAuth2User principle){
        try {
            String name;
            String email;

            name = principle.getAttribute("login");
            email = principle.getAttribute("email");

            generateAuthToken(); // generate UUID for github user
            User userGitHub = new User();

            if(authRepo.gitUser(name) != null) {
                // update user if they already exist with a new code
                userGitHub = authRepo.gitUser(name);
                // set an expiry of 15 minutes on token being valid.
                userGitHub.setExpiration(LocalDateTime.now().plusMinutes(15));
                userGitHub.setAuthToken(String.valueOf(authToken));
                authRepo.save(userGitHub);
                System.out.println("Updated User...");
            } else {
                // save user if it's there first time logging in
                userGitHub.setName(name);
                userGitHub.setEmail(email);
                userGitHub.setAuthToken(String.valueOf(authToken));
                userGitHub.setExpiration(LocalDateTime.now().plusMinutes(15));
                authRepo.save(userGitHub);
                System.out.println("Saved New User...");
            }

            return new ResponseEntity<>(userGitHub, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // determine if token is valid
    public boolean isAuthenticated(String token) {
        User validUser;
        LocalDateTime current = LocalDateTime.now();

        if(authRepo.checkToken(token) != null) {
            validUser = authRepo.checkToken(token);
            // if the current time is after the expiration time the token is expired.
            return validUser.getExpiration().isAfter(current);
        }
        return false;
    }

}
