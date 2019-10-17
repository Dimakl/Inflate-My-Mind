package com.inflatemymind.controllers;

import com.inflatemymind.models.Expression;
import com.inflatemymind.repositories.ExpressionRepository;
import com.inflatemymind.services.ExpressionService;
import com.inflatemymind.services.UserService;
import com.inflatemymind.utility.LogicalExpression;
import javassist.expr.Expr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/expressions")
public class ExpressionController {

    @Autowired
    ExpressionService expressionService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity getExpressionById(Long expressionId) {
        Optional<Expression> expression = expressionService.getExpressionById(expressionId);

        if (expression.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(expression);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No expression with such id");
        }
    }

    @PostMapping
    public ResponseEntity createExpression(@RequestBody Expression expression) {
        if (expression.getExpression() == null || expression.getContributorId() == null ||
                expression.getAnswer() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Request contains null values");
        } else if (!userService.getUserById(expression.getContributorId()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid user id");
        } else if (!LogicalExpression.expressionIsValid(expression.getExpression())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid expression");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(expressionService.createExpression(expression));
        }
    }
}
