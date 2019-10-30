// Generated from /home/dimakl/IdeaProjects/inflate-my-mind/src/main/java/com/inflatemymind/antlr/LogicalExpression.g4 by ANTLR 4.7.2
package com.inflatemymind.antlr.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LogicalExpressionParser}.
 */
public interface LogicalExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LogicalExpressionParser#eval}.
	 * @param ctx the parse tree
	 */
	void enterEval(LogicalExpressionParser.EvalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogicalExpressionParser#eval}.
	 * @param ctx the parse tree
	 */
	void exitEval(LogicalExpressionParser.EvalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogicalExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(LogicalExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogicalExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(LogicalExpressionParser.ExpressionContext ctx);
}