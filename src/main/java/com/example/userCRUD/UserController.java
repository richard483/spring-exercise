package com.example.userCRUD;

import com.example.userCRUD.models.User;
import com.example.userCRUD.repositories.UserRepository;
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

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer elements){
        Page<User> users;
        Map<String, Object> responseBody = new HashMap<>();
        try {
            PageRequest pageRequest = PageRequest.of(page, elements);
            users = userRepository.findAll(pageRequest);
            responseBody.put("message","Page " + (page+1) + " of " + users.getTotalPages());
            responseBody.put("data", users);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUserByName(@RequestParam String name, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer elements){
        Page<User> users;
        Map<String, Object> responseBody = new HashMap<>();
        try {
            PageRequest pageRequest = PageRequest.of(page, elements);
            users = userRepository.findUserByNameContains(name, pageRequest);
            responseBody.put("message","Page " + (page+1) + " of " + users.getTotalPages());
            responseBody.put("data", users);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user){
        Map<String, Object> responseBody = new HashMap<>();
        try {
            userRepository.save(user);
            responseBody.put("message", "User " + user.getName() + " created!");
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Map<String, Object>> deleteUserById(@RequestParam Integer id){
        Map<String, Object> responseBody = new HashMap<>();
        try {
            User user = userRepository.findUserById(id);
            if( user == null)
                throw new RuntimeException("There are no user with such id");
            userRepository.deleteById(id);
            responseBody.put("message","User: " + user.getName() + " deleted!");
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (Exception e){
            responseBody.put("data", e.getMessage().toString());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUserById(@RequestBody User user, @PathVariable Integer id){
        Map<String, Object> responseBody = new HashMap<>();

        try{
            User userTemp = userRepository.findUserById(id);
            String userName = userTemp.getName();
            if(userTemp == null)
                throw new RuntimeException("There are no user with such id");
            userTemp.setAddress(user.getAddress());
            userTemp.setEmail(user.getEmail());
            userTemp.setName(user.getName());
            userTemp.setPassword(user.getPassword());
            userRepository.save(userTemp);

            responseBody.put("message","User: "+userName+" updated to " + user.getName() + " !");
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (Exception e){
            responseBody.put("data", e.getMessage().toString());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }
}
