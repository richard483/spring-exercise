package com.example.userCRUD.services;

import com.example.userCRUD.constants.ERole;
import com.example.userCRUD.models.User;
import com.example.userCRUD.repositories.UserRepository;
import com.example.userCRUD.requests.CreateUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService {
  @Autowired UserRepository userRepository;


  public ResponseEntity<Map<String, Object>> getAllUsers(Integer page, Integer elements) {
    Page<User> users;
    Map<String, Object> responseBody = new HashMap<>();
    try {
      PageRequest pageRequest = PageRequest.of(page - 1, elements);
      users = userRepository.findAll(pageRequest);

      if (users.getNumberOfElements() == 0)
        throw new Exception("There are no items for this page");

      responseBody.put("message", "Page " + (page + 1) + " of " + users.getTotalPages());
      responseBody.put("data", users);

      return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } catch (Exception e) {
      responseBody.put("message", e.getMessage());
      return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<Map<String, Object>> searchUserByName(String name,
      Integer page,
      Integer elements) {
    Page<User> users;
    Map<String, Object> responseBody = new HashMap<>();
    try {
      PageRequest pageRequest = PageRequest.of(page - 1, elements);
      users = userRepository.findUserByNameContains(name, pageRequest);

      if (users.getNumberOfElements() == 0) {
        if (page == 1)
          throw new Exception("There are no results of your search");

        throw new Exception("There are no items for this page");
      }

      responseBody.put("message", "Page " + (page + 1) + " of " + users.getTotalPages());
      responseBody.put("data", users);
      return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } catch (Exception e) {
      responseBody.put("message", e.getMessage());
      return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<Map<String, Object>> createUser(CreateUserRequest userRequest) {
    Map<String, Object> responseBody = new HashMap<>();
    try {
      User requestedUser;
      try {
        requestedUser = User.builder()
            .name(userRequest.getName())
            .email(userRequest.getEmail())
            .address(userRequest.getAddress())
            .password(userRequest.getPassword())
            .role(ERole.valueOf(userRequest.getRole()))
            .build();
      } catch (Exception e) {
        throw new Exception("There are no given value of user!");
      }


      userRepository.save(requestedUser);
      responseBody.put("message", "User " + requestedUser.getName() + " created!");
      return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } catch (Exception e) {
      responseBody.put("message", e.getMessage());
      return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<Map<String, Object>> deleteUserById(Integer id) {
    Map<String, Object> responseBody = new HashMap<>();
    try {
      User user = userRepository.findUserById(id);
      if (user == null)
        throw new RuntimeException("There are no user with such id");
      userRepository.deleteById(id);
      responseBody.put("message", "User: " + user.getName() + " deleted!");
      return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } catch (Exception e) {
      responseBody.put("data", e.getMessage());
      return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<Map<String, Object>> updateUserById(User user, Integer id) {
    Map<String, Object> responseBody = new HashMap<>();

    try {
      User userTemp = userRepository.findUserById(id);
      String userName = userTemp.getName();
      if (userTemp == null)
        throw new RuntimeException("There are no user with such id");
      userTemp.setAddress(user.getAddress());
      userTemp.setEmail(user.getEmail());
      userTemp.setName(user.getName());
      userTemp.setPassword(user.getPassword());
      userRepository.save(userTemp);

      responseBody.put("message", "User: " + userName + " updated to " + user.getName() + " !");
      return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } catch (Exception e) {
      responseBody.put("data", e.getMessage());
      return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
  }


}
