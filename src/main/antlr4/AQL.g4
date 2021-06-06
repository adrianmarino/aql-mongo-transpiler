grammar AQL; // ADRI Query Language Dad! ;)!
//
//
//
// ----------------------------------------------------------------------------
// Grammar rules
// ----------------------------------------------------------------------------
query: expression;

expression
    : '(' expression ')'                                  # parExpression
    | expression ('and' | 'AND' | '&&' | '&') expression  # andExpression
    | expression ('or' | 'OR' | '||' | '|')   expression  # orExpression

    // String expressions:
    | PROPERTY '='  STRING # strEqExpression
    | PROPERTY '!=' STRING # strNotEqExpression

    // Numeric expressions:
    | PROPERTY '='  NUMBER # numEqExpression
    | PROPERTY '!=' NUMBER # numNotEqExpression
    | PROPERTY '>'  NUMBER # numGTExpression
    | PROPERTY '<'  NUMBER # numLTExpression
    | PROPERTY '>=' NUMBER # numGTEqExpression
    | PROPERTY '<=' NUMBER # numLTEqExpression

    // Datetime expression:
    | PROPERTY 'from' DATE_TIME 'to' DATE_TIME # datetimeBetweenExpression
    ;
//
//
//
// ----------------------------------------------------------------------------
// Lexical rules
// ----------------------------------------------------------------------------
fragment DIGIT      : [0-9];
fragment LETTER     : [a-zA-Z] | '_';
fragment IDENTIFIER : LETTER (LETTER | DIGIT)*;

WS: (' ' | '\t' | '\n' | '\r')+ -> skip;

PROPERTY: IDENTIFIER+('.'IDENTIFIER)*;

NUMBER: DIGIT+('.'DIGIT)* | DIGIT+;

STRING
    : '\'' .*? '\''
    | '"' .*? '"'
    ;

/*
  Datetime
*/
fragment DATE:  DIGIT DIGIT DIGIT DIGIT '-' DIGIT DIGIT '-' DIGIT DIGIT;
fragment TIME: DIGIT DIGIT ':' DIGIT DIGIT ':' DIGIT DIGIT;
DATE_TIME
    :  '´' DATE ' ' TIME '´'
    |  '´' DATE '´';
