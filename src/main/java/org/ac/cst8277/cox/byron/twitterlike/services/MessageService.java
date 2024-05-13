package org.ac.cst8277.cox.byron.twitterlike.services;

import org.ac.cst8277.cox.byron.twitterlike.beans.Message;
import org.ac.cst8277.cox.byron.twitterlike.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    // Get all Messages
    public ResponseEntity<List<Message>> getMessages() {
        return new ResponseEntity<>(messageRepo.findAll(), HttpStatus.OK); // return all users from user table.
    }

    // Get messages produced by a specific producer
    public ResponseEntity<List<Message>> findByProducerId(@PathVariable int id) {
        return new ResponseEntity<>(messageRepo.findByProducerId(id), HttpStatus.OK); // return all users from user table.
    }

    // Get messages from a specific subscriber
    public ResponseEntity<List<Message>> findBySubscriberId(@PathVariable int id) {
        return new ResponseEntity<>(messageRepo.findBySubscriberId(id), HttpStatus.OK); // return all users from user table.
    }
}
