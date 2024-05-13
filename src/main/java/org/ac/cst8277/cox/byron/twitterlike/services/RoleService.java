package org.ac.cst8277.cox.byron.twitterlike.services;

import org.ac.cst8277.cox.byron.twitterlike.beans.Role;
import org.ac.cst8277.cox.byron.twitterlike.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    // Get all Roles
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleRepo.findAll(), HttpStatus.OK);
    }
}
