package com.inflatemymind.utility;


import com.inflatemymind.antlr.generated.LogicalExpressionLexer;
import com.inflatemymind.antlr.generated.LogicalExpressionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Tree;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.xml.soap.Node;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ImageFromLogicalExpression {

    private String expression;

    private Integer answer;

    private List<List<TreeNode>> treeNodes = new ArrayList<>();

    private Image and, or, not;

    public static void main(String[] args) {
        ImageFromLogicalExpression imageFromLogicalExpression = new ImageFromLogicalExpression("A&B|C", 1);
    }

    public ImageFromLogicalExpression(String expression, Integer answer) {
        this.expression = expression;
        this.answer = answer;
        getTreeNodesFromExpression();
        try {
            loadImages();
            drawImageFromTreeNodes();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        System.out.println();
    }


    private void drawImageFromTreeNodes() throws IOException {
        BufferedImage bufferedImage = createBufferedImage();
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(3f);
        g.setStroke(stroke);
        Font font = new Font("TimesRoman", Font.BOLD, 20);
        g.setFont(font);
        drawBasicElements(g);
        drawConnections(g);
        ImageIO.write(bufferedImage, "png", new File("coolfile.png"));
    }

    private void drawBasicElements(Graphics2D g) {
        Integer currentX = 0;
        Integer currentY = 0;
        for (int i = treeNodes.size() - 1; i > -1; i--) {
            for (int j = 0; j < treeNodes.get(i).size(); j++) {
                TreeNode node = treeNodes.get(i).get(j);
                node.boxStartX = currentX;
                node.boxStartY = currentY;
                switch (node.type) {
                    case VARIABLE:
                        String var = node.nodeText;
                        if (node.isNegated) {
                            var = "!" + var;
                        }
                        g.drawString(var, currentX + 25, currentY + 35);
                        break;
                    case PARENS:
                        if (node.isNegated) {
                            g.drawImage(not, currentX, currentY, null);
                        } else {
                            g.drawLine(currentX, currentY + 25, currentX + 50, currentY + 25);
                        }
                        break;
                    case AND:
                        g.drawImage(and, currentX, currentY, null);
                        break;
                    case OR:
                        g.drawImage(or, currentX, currentY, null);
                        break;
                }
                g.drawLine(currentX + 75, currentY + 25, currentX + 100, currentY + 25);
                currentY += 75;
            }
            currentY = 0;
            currentX += 125;
        }
    }

    private void drawConnections(Graphics2D g) {

    }

    private BufferedImage createBufferedImage() {
        int canvasLength = (treeNodes.size() + 1) * 125 - 50; // each block besides the last is 125 and last doesnt contain wires
        int max = 0;
        for (List<TreeNode> el : treeNodes) {
            if (el.size() > max) {
                max = el.size();
            }
        }
        int canvasWidth = 75 * max - 25; // each block is 50, 25 - padding
        return new BufferedImage(canvasLength, canvasWidth, BufferedImage.TYPE_INT_ARGB);
    }

    private void loadImages() throws IOException {
        and = ImageIO.read(getClass().getResource("/operator_images/and.png"));
        or = ImageIO.read(getClass().getResource("/operator_images/or.png"));
        not = ImageIO.read(getClass().getResource("/operator_images/not.png"));
    }


    enum NodeType {
        VARIABLE,
        OR,
        AND,
        PARENS
    }

    class TreeNode {
        NodeType type;
        String nodeText;
        Boolean isNegated;
        TreeNode next;
        Integer boxStartX;
        Integer boxStartY;

        // конструктор поменять
        public TreeNode(ParseTree node, Boolean isNegated, NodeType nodeType, TreeNode next) {
            this.type = nodeType;
            this.next = next;
            this.nodeText = node.getText();
            this.isNegated = isNegated;
        }
    }


}
