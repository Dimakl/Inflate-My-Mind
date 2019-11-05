grammar LogicalExpression;

eval
    : expression EOF;

expression
    : expression AND expression       #AND
    | expression OR expression        #OR
    | (NOT)* LPAREN expression RPAREN #PARENS
    | (NOT)* VARIABLE                 #VARIABLE
    ;

LPAREN
    : '('
    ;

RPAREN
    : ')'
    ;

NOT
    : '!'
    ;

AND
    : '&'
    ;

OR
    : '|'
    ;

VARIABLE
    : [A-Z]
    ;