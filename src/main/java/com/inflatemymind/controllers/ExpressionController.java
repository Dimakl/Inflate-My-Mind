package com.inflatemymind.controllers;

import com.inflatemymind.models.Expression;
import com.inflatemymind.repositories.ExpressionRepository;
import com.inflatemymind.services.ExpressionService;
import com.inflatemymind.services.UserService;
import com.inflatemymind.utility.ImageFromLogicalExpression;
import com.inflatemymind.utility.LogicalExpression;
import javassist.expr.Expr;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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

    @GetMapping(value = "/image")
    public @ResponseBody ResponseEntity getFullExpressionInfoById(Long expressionId) {
        Optional<Expression> expression = expressionService.getExpressionById(expressionId);
        if (expression.isPresent()) {
            String encodedString = null;
            try {
                BufferedImage image = ImageIO.read(new File("src/main/resources/schemes/" + expressionId + ".png"));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write( image, "png", baos );
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
                encodedString = Base64.getEncoder().encodeToString(imageInByte);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            try {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .headers(headers)
                        .body(encodedString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No expression with such id");
        }
        return null;
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
            Expression expr = expressionService.createExpression(expression);
            new ImageFromLogicalExpression(expr);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(expr);
        }
    }
}
