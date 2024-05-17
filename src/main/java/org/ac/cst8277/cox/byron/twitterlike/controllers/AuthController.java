package org.ac.cst8277.cox.byron.twitterlike.controllers;

import org.ac.cst8277.cox.byron.twitterlike.beans.User;
import org.ac.cst8277.cox.byron.twitterlike.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestParam String name,
                                                 @RequestParam String password) {
        if(name.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return authService.authorizeUser(name, password); // placeholder
        }
    }
    @GetMapping("/")
    public ResponseEntity<User> authorize(@AuthenticationPrincipal OAuth2User principal) {
        // placeholder return details from GitHub
        if(principal != null) {
            return authService.getGitHubToken(principal);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

