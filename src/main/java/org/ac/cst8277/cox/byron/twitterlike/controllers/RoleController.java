package org.ac.cst8277.cox.byron.twitterlike.controllers;

import org.ac.cst8277.cox.byron.twitterlike.beans.Role;
import org.ac.cst8277.cox.byron.twitterlike.services.AuthService;
import org.ac.cst8277.cox.byron.twitterlike.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;
    private final AuthService authService;

    @Autowired
    public RoleController(RoleService roleService, AuthService authService) {
        this.roleService = roleService;
        this.authService = authService;
    }

    // Get all Roles
    @GetMapping(value = "/role")
    public ResponseEntity<List<Role>> getRoles(@RequestParam String token) {
        if(authService.isAuthenticated(token)){
            return roleService.getRoles(); // return all roles from role table.
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
