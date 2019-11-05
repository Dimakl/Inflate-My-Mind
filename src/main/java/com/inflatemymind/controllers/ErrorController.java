package com.inflatemymind.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public RedirectView error() {
        return new RedirectView("/index.html");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
