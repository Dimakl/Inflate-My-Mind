package com.inflatemymind.controllers;

import com.inflatemymind.models.Submission;
import com.inflatemymind.services.ExpressionService;
import com.inflatemymind.services.SubmissionService;
import com.inflatemymind.services.UserService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @Autowired
    ExpressionService expressionService;

    @Autowired
    UserService userService;

    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionService.getSubmissions();
    }

    @GetMapping(params = {"submissionId"})
    public ResponseEntity getSubmissionById(Long submissionId) {
        Optional<Submission> submission = submissionService.getSubmissionById(submissionId);
        if (submission.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(submission);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No submission with such id");
        }
    }

    @GetMapping(params = {"expressionId"})
    public List<Submission> getSubmissionsByExpressionId(Long expressionId) {
        return submissionService.getSubmissionsByExpressionId(expressionId);
    }

    @PostMapping
    public ResponseEntity createSubmission(@RequestBody Submission submission) {
        if (submission.getExpressionId() == null || submission.getIsCorrect() == null
                || submission.getUserId() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Request contains null values");
        } else if (!userService.getUserById(submission.getUserId()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No user with such id");
        } else if (!expressionService.getExpressionById(submission.getExpressionId()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No expression with such id");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(submissionService.createSubmission(submission));
        }
    }

}
