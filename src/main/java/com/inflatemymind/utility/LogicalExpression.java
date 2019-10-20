package com.inflatemymind.utility;

import com.inflatemymind.antlr.generated.LogicalExpressionLexer;
import com.inflatemymind.antlr.generated.LogicalExpressionParser;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenSource;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.TokenStream;

public class LogicalExpression {

    public static void main(String[] args) {
        expressionIsValid("A&B");
    }

    public static Boolean expressionIsValid(String expression) {
        CharStream in = CharStreams.fromString(expression);
        LogicalExpressionLexer lexer = new LogicalExpressionLexer(in);
        // TODO: not working righnt now
        CommonTokenStream tokens = new CommonTokenStream((TokenSource) lexer);
        LogicalExpressionParser parser = new LogicalExpressionParser((TokenStream) tokens);
        parser.eval();
        return true;
    }

}
