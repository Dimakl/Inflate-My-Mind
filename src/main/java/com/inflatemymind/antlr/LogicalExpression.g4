grammar LogicalExpression;

eval
    : expression EOF;

expression
    : expression AND expression
    | expression OR expression
    | (NOT)* LPAREN expression RPAREN
    | (NOT)* VARIABLE
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