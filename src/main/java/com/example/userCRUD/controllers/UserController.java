package com.example.userCRUD.controllers;

import com.example.userCRUD.models.User;
import com.example.userCRUD.repositories.UserRepository;
import com.example.userCRUD.requests.CreateUserRequest;
import com.example.userCRUD.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired UserRepository userRepository;

  @Autowired UserService userService;

  @GetMapping("/all")
  public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "5") Integer elements) {
    return userService.getAllUsers(page, elements);
  }

  @GetMapping("/search")
  public ResponseEntity<Map<String, Object>> searchUserByName(@RequestParam String name,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "5") Integer elements) {
    return userService.searchUserByName(name, page, elements);
  }

  @PostMapping()
  public ResponseEntity<Map<String, Object>> createUser(@RequestBody CreateUserRequest createUserRequest) {
    return userService.createUser(createUserRequest);
  }

  @DeleteMapping()
  public ResponseEntity<Map<String, Object>> deleteUserById(@RequestParam Integer id) {
    return userService.deleteUserById(id);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Map<String, Object>> updateUserById(@RequestBody User user,
      @PathVariable Integer id) {
    return userService.updateUserById(user, id);
  }
}
