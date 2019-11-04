package com.inflatemymind.utility;

import com.inflatemymind.antlr.generated.LogicalExpressionLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class LogicalExpressionParser {

    private String expression;

    public LogicalExpressionParser(String expression) {
        this.expression = expression;
        getParseTreeFromExpression();
    }

    private void getParseTreeFromExpression() {
        LogicalExpressionLexer lexer = new LogicalExpressionLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        com.inflatemymind.antlr.generated.LogicalExpressionParser parser = new com.inflatemymind.antlr.generated.LogicalExpressionParser(tokens);
    }


}
