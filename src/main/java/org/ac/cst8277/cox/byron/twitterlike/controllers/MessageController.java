package org.ac.cst8277.cox.byron.twitterlike.controllers;

import org.ac.cst8277.cox.byron.twitterlike.beans.Message;
import org.ac.cst8277.cox.byron.twitterlike.services.AuthService;
import org.ac.cst8277.cox.byron.twitterlike.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    private final MessageService messageService;
    private final AuthService authService;

    @Autowired
    public MessageController(MessageService messageService, AuthService authService) {
        this.messageService = messageService;
        this.authService = authService;
    }

    // Get all Messages
    @GetMapping(value = "/message")
    public ResponseEntity<List<Message>> getMessages(@RequestParam String token) {
        if(authService.isAuthenticated(token)){
            return messageService.getMessages(); // return all users from user table.
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // Get messages produced by a specific producer
    @GetMapping(value = "/message/published/{id}")
    public ResponseEntity<List<Message>> findByProducerId(@PathVariable int id,
                                                          @RequestParam String token) {
        if(authService.isAuthenticated(token)){
            return messageService.findByProducerId(id); // return all users from user table.
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // Get messages from a specific subscriber
    @GetMapping(value = "/message/subscribed/{id}")
    public ResponseEntity<List<Message>> findBySubscriberId(@PathVariable int id,
                                                            @RequestParam String token) {
        if(authService.isAuthenticated(token)){
            return messageService.findBySubscriberId(id); // return all users from user table.
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
