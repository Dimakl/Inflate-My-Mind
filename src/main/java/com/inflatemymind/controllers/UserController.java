package com.inflatemymind.controllers;

import com.inflatemymind.exceptions.ResourceNotFoundException;
import com.inflatemymind.models.User;
import com.inflatemymind.repositories.UserRepository;
import com.inflatemymind.services.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @CrossOrigin(origins = "http://127.0.0.1:8000")
    @GetMapping(params={"login","password"})
    public ResponseEntity getUserByLoginAndPassword(String login, String password) {
        // TODO: fix when user is not found
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByLoginAndPassword(login, password));
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity getUserById(@RequestParam Long userId) {
        Optional<User> user = userService.getUserById(userId);

        if (user.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No user with such id");
        }
    }


    @CrossOrigin(origins = "http://127.0.0.1:8000")
    @PostMapping
    public ResponseEntity createUser( User user) throws IllegalAccessException {
        if (user.getLogin() == null || user.getPassword() == null || user.getIsTeacher() == null
                || user.getFirstName() == null || user.getSecondName() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Request contains null values");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.createUser(user));
    }


}
