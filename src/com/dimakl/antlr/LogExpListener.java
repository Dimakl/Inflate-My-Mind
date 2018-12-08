package com.dimakl.antlr;// Generated from LogExp.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LogExpParser}.
 */
public interface LogExpListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LogExpParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(LogExpParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogExpParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(LogExpParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link LogExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpression(LogExpParser.BinaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link LogExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpression(LogExpParser.BinaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link LogExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(LogExpParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link LogExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(LogExpParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link LogExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(LogExpParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link LogExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(LogExpParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link LogExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(LogExpParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link LogExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(LogExpParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogExpParser#binary}.
	 * @param ctx the parse tree
	 */
	void enterBinary(LogExpParser.BinaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogExpParser#binary}.
	 * @param ctx the parse tree
	 */
	void exitBinary(LogExpParser.BinaryContext ctx);
}