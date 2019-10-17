package com.inflatemymind.services;

import com.inflatemymind.models.Expression;
import com.inflatemymind.repositories.ExpressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpressionService {

    @Autowired
    ExpressionRepository expressionRepository;

    public Optional<Expression> getExpressionById(Long expressionId) {
        return expressionRepository.findById(expressionId);
    }

    public Expression createExpression(Expression expression) {
        return expressionRepository.save(expression);
    }

}
