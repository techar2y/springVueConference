package com.arty.springVueConference.controllers;

import com.arty.springVueConference.domains.Role;
import com.arty.springVueConference.domains.User;
import com.arty.springVueConference.repos.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(required = false) String searchStr,
                                                           @RequestParam(defaultValue="0") int page,
                                                           @RequestParam(defaultValue="3") int pageSize){
        try {
            Pageable paging = PageRequest.of(page, pageSize);

            Page<User> users = null;
            if (searchStr == null || searchStr.length() == 0)
                users = userRepository.findAll(paging);
            else
                users = userRepository.findUserByUsernameContaining(searchStr, paging);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("users", users.getContent());
            response.put("currentPage", users.getTotalPages());
            response.put("totalItems", users.getTotalElements());
            response.put("totalPages", users.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAllUsers")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> deleteAllUsers(){
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            User _user = userRepository.save(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        try {
            User user = userRepository.getUserById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUserById/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long id){
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){
        try {
            User newUser = userRepository.getUserById(id);
            if(newUser == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


            if (user != null) {
                newUser.setFullName(user.getFullName());
                newUser.setEmail(user.getEmail());
                newUser.setUsername(user.getUsername());
                newUser.setRoles(user.getRoles());
                newUser = userRepository.save(newUser);
            }

            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/isUsernameUnique")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<Integer> isUsernameUnique(@RequestParam String username, @RequestParam Long id) {
        try {
            List<User> users = userRepository.findUsersByUsernameAndIdNot(username, id);

            return new ResponseEntity<>(users.size(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/isEmailUnique")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<Integer> isEmailUnique(@RequestParam String email, @RequestParam Long id) {
        try {
            List<User> users = userRepository.findUsersByEmailAndIdNot(email, id);

            return new ResponseEntity<>(users.size(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getUsersByUserRole")
    @PreAuthorize("hasAuthority('user:read') or hasAuthority('role:read')")
    public ResponseEntity<List<User>> getUsersByUserRole(@RequestBody Role role) {
        try {

            List<User> users = userRepository.findUsersByRolesName(role.getName());

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
