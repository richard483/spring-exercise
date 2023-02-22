package com.example.userCRUD.controllers;

import com.example.userCRUD.models.User;
import com.example.userCRUD.repositories.UserRepository;
import com.example.userCRUD.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer elements){
        return userService.getAllUsers(page, elements);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUserByName(@RequestParam String name, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer elements){
        return  userService.searchUserByName(name, page, elements);
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping()
    public ResponseEntity<Map<String, Object>> deleteUserById(@RequestParam Integer id){
       return userService.deleteUserById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUserById(@RequestBody User user, @PathVariable Integer id){
        return userService.updateUserById(user, id);
    }
}
