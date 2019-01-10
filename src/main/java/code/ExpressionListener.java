package code;

import antlr.LogExpBaseListener;
import antlr.LogExpLexer;
import antlr.LogExpParser;
import org.antlr.v4.runtime.Vocabulary;
import java.util.Map;

import java.util.*;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class ExpressionListener extends LogExpBaseListener{

    // type of vars is String, because Strings could be used as vars in future
    private HashSet<String> vars = new HashSet<String>();
    private LogExpLexer lexer;
    // string array in future should be changed to reference array
    private HashMap<String,ArrayList<String>> nodeMap;

    // such a redundancy is made for future development
    public final String PASS_NODE = "pass";

    public ExpressionListener(LogExpLexer lexer){
        this.lexer = lexer;
        nodeMap = new HashMap<String, ArrayList<String>>();
    }

    public List<String> getVars(){
        return new ArrayList<String>(vars);
    }


    @Override
    public void exitBinaryExpression(LogExpParser.BinaryExpressionContext ctx) {
        //TODO: understand what must be happening here
        Vocabulary vocabulary =  lexer.getVocabulary();
        String leftOp = ctx.left.getText(),
            rightOp = ctx.right.getText(),
            op = ctx.op.getText(),
            fullOpName = "("+leftOp+")" + op + "("+rightOp+")";
        if (!nodeMap.containsKey(fullOpName))
            nodeMap.put(fullOpName, new ArrayList<String>());
        else {
            int i;
            for (i = 2; i < 20; i++)
                if (!nodeMap.containsKey(fullOpName+"["+i+"]")) {
                    fullOpName = fullOpName+"["+i+"]";
                    nodeMap.put(fullOpName, new ArrayList<>());
                    break;
                }
        }
    }


    //TODO: make realization of these 2 methods:
    @Override
    public void exitIdentifierExpression(LogExpParser.IdentifierExpressionContext ctx) {
        nodeMap.putIfAbsent(ctx.id.getText(), new ArrayList<String>());
    }

    @Override
    public void exitNotExpression(LogExpParser.NotExpressionContext ctx) { }


    @Override
    public void enterIdentifierExpression(LogExpParser.IdentifierExpressionContext ctx) {
        vars.add(ctx.id.getText());
    }

}
