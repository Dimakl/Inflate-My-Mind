package com.inflatemymind.utility;


import com.inflatemymind.antlr.generated.LogicalExpressionLexer;
import com.inflatemymind.antlr.generated.LogicalExpressionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Tree;

import javax.xml.soap.Node;
import java.util.*;

public class ImageFromLogicalExpression {

    private String expression;

    private List<List<TreeNode>> treeNodes = new ArrayList<>();

    public static void main(String[] args) {
        ImageFromLogicalExpression imageFromLogicalExpression = new ImageFromLogicalExpression("(A&B)|(D&Cd)");
    }

    public ImageFromLogicalExpression(String expression) {
        this.expression = expression;
        getTreeNodesFromExpression();
        drawImageFromTreeNodes();
    }


    private void getTreeNodesFromExpression() {
        LogicalExpressionLexer lexer = new LogicalExpressionLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        com.inflatemymind.antlr.generated.LogicalExpressionParser parser = new LogicalExpressionParser(tokens);
        // тут дфс
        Queue<AbstractMap.SimpleEntry<TreeNode, ParseTree>> queue = new LinkedList<>();
        Queue<AbstractMap.SimpleEntry<TreeNode, ParseTree>> nextQueue = new LinkedList<>();
        int currentLevel = 0;
        treeNodes.add(new ArrayList<>());
        queue.add(new AbstractMap.SimpleEntry<>(null, parser.eval().getChild(0)));
        ParseTree node;
        while (!queue.isEmpty()) {
            AbstractMap.SimpleEntry<TreeNode, ParseTree> element = queue.poll();
            TreeNode fromNode = element.getKey();
            TreeNode appendNode;
            node = element.getValue();
            switch (node.getChildCount()) {
                case 4:
                    appendNode = new TreeNode(node, true, NodeType.PARENS, fromNode);
                    treeNodes.get(currentLevel).add(appendNode);
                    nextQueue.add(new AbstractMap.SimpleEntry<>(appendNode, node.getChild(2))); // not ( EXPR )
                    break;
                case 3:
                    if (node.getChild(1).getText().equals("&")
                            || node.getChild(1).getText().equals("|")) { // expr [&|] expr
                        NodeType nodeType = node.getChild(1).getText().equals("&") ? NodeType.AND : NodeType.OR;
                        appendNode = new TreeNode(node, false, nodeType, fromNode);
                        treeNodes.get(currentLevel).add(appendNode);
                        nextQueue.add(new AbstractMap.SimpleEntry<>(appendNode, node.getChild(0))); // EXPR [&\] expr
                        nextQueue.add(new AbstractMap.SimpleEntry<>(appendNode, node.getChild(2))); // expr [&\] EXPR
                    } else { // ( expr )
                        appendNode = new TreeNode(node, false, NodeType.PARENS, fromNode);
                        treeNodes.get(currentLevel).add(appendNode);
                        nextQueue.add(new AbstractMap.SimpleEntry<>(appendNode, node.getChild(1))); // ( EXPR )
                    }
                    break;
                case 2: // ! VAR
                    appendNode = new TreeNode(node, true, NodeType.VARIABLE, fromNode);
                    treeNodes.get(currentLevel).add(appendNode);
                case 1: // VAR
                    appendNode = new TreeNode(node, false, NodeType.VARIABLE, fromNode);
                    treeNodes.get(currentLevel).add(appendNode);
            }
            if (queue.isEmpty() && !nextQueue.isEmpty()) {
                queue = nextQueue;
                currentLevel++;
                treeNodes.add(new ArrayList<>());
                nextQueue = new LinkedList<>();
            }
        }
    }


    private void drawImageFromTreeNodes() {
    }

    enum NodeType {
        VARIABLE,
        OR,
        AND,
        PARENS
    }

    class TreeNode {
        NodeType type;
        String NodeToken;
        Boolean isNegated;
        TreeNode next;

        // конструктор поменять
        public TreeNode(ParseTree node, Boolean isNegated, NodeType nodeType, TreeNode next) {
            this.type = nodeType;
            this.next = next;
            this.NodeToken = node.getText();
            this.isNegated = isNegated;
        }
    }


}
