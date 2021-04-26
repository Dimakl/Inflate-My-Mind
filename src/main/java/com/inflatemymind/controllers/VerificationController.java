package com.inflatemymind.controllers;


import com.inflatemymind.models.Email;
import com.inflatemymind.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    @Autowired
    EmailService emailService;

    @CrossOrigin
    @GetMapping
    public String verifyEmail(String email, Long code) {
        if (emailService.verifyEmail(email, code)) {
            return "Email verified";
        } else {
            return "Invalid code for email verification";
        }
    }

}
