package com.arty.springVueConference.controllers;

import com.arty.springVueConference.domains.Role;
import com.arty.springVueConference.repos.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
public class RoleController
{
    @Autowired
    public IRoleRepository roleRepository;

    @GetMapping("/getAllRoles")
    @PreAuthorize("hasAuthority('role:read')")
    public ResponseEntity<List<Role>> getAllRoles() {
        try {
            List<Role> roles = roleRepository.findAll();

            if(roles.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }


            return new ResponseEntity<>(roles, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
