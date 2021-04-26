package com.inflatemymind.controllers;

import com.inflatemymind.exceptions.ResourceNotFoundException;
import com.inflatemymind.models.Email;
import com.inflatemymind.models.User;
import com.inflatemymind.repositories.UserRepository;
import com.inflatemymind.services.EmailService;
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

    @Autowired
    EmailService emailService;

    @CrossOrigin
    @GetMapping(params = {"login", "password"})
    public ResponseEntity getUserByLoginAndPassword(String login, String password) {
        User user = userService.getUserByLoginAndPassword(login, password);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No such user");
        }
        if (!emailService.isEmailVerified(user)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email not verified");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @CrossOrigin
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


    @CrossOrigin
    @PostMapping
    public ResponseEntity createUser(User user) throws IllegalAccessException {
        if (user.getLogin() == null || user.getPassword() == null || user.getIsTeacher() == null
                || user.getFirstName() == null || user.getSecondName() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Request contains null values");
        }
        if (!emailService.registerOrDeclineUserEmail(user, user.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email is used");
        }
        if (!userService.hasUnusedLogin(user)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username is used");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.createUser(user));
    }


}
