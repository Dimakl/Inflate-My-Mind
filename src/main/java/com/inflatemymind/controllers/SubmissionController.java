package com.inflatemymind.controllers;

import com.inflatemymind.models.Submission;
import com.inflatemymind.models.User;
import com.inflatemymind.services.ExpressionService;
import com.inflatemymind.services.SubmissionService;
import com.inflatemymind.services.UserService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @GetMapping(params = {"userId"})
    public ResponseEntity getSubmissionsByUserId(Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(submissionService.getSubmissionsByUserId(userId));
    }

    @GetMapping(params = {"expressionId"})
    public ResponseEntity getSubmissionsByExpressionId(Long expressionId) {
        List<Submission> submissions = submissionService.getSubmissionsByExpressionId(expressionId);
        List<User> users = submissions.stream()
                .map(submission -> userService.getUserById(submission.getUserId()).get())
                .collect(Collectors.toList());
        List<Map<String, String>> submissionsMap =
                submissions.stream().map(submission -> new HashMap<String, String>() {{
                    put("res",
                            submission.getIsCorrect() ? "Верно" : "Неверно");
                    put("date",
                            submission.getSubmissionTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }}).collect(Collectors.toList());
        List<Map<String, String>> usersMap =
                users.stream().map(user ->
                        new HashMap<String, String>() {{
                            put("name", user.getFirstName());
                            put("surname", user.getSecondName());
                        }}
                ).collect(Collectors.toList());
        List<Map<String, String>> response = new ArrayList<Map<String, String>>(submissions.size()) {{
            for (int i = 0; i < submissions.size(); i++) {
                submissionsMap.get(i).putAll(usersMap.get(i));
                add(i, submissionsMap.get(i));
            }
        }};
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
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
