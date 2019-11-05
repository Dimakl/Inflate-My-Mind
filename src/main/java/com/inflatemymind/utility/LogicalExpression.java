package com.inflatemymind.utility;

import com.inflatemymind.antlr.generated.LogicalExpressionLexer;
import com.inflatemymind.antlr.generated.LogicalExpressionParser;
import org.antlr.v4.runtime.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LogicalExpression {

    public static Boolean expressionIsValid(String expression) {
        return checkSyntaxErrors(expression) && checkInvalidTokens(expression);
    }

    private static boolean checkSyntaxErrors(String expression) {
        LogicalExpressionLexer lexer = new LogicalExpressionLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LogicalExpressionParser parser = new LogicalExpressionParser(tokens);
        parser.eval();
        return parser.getNumberOfSyntaxErrors() == 0;
    }

    private static boolean checkInvalidTokens(String expression) {
        // on change of grammar should change it too
        String allowedTokens = "()!&|^ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Set<Character> tokenSet = new HashSet<>();
        for (Character c : allowedTokens.toCharArray()) {
            tokenSet.add(c);
        }
        for (Character c : expression.toCharArray()) {
            if (!tokenSet.contains(c)) {
                return false;
            }
        }
        return true;
    }

}
