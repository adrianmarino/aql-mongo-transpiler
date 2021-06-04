grammar AQL;
// ----------------------------------------------------------------------------
// Grammar rules
// ----------------------------------------------------------------------------
query: expression (bool_op expression)*;

bool_op: 'and' | 'or';

expression
    : str_eq_expression
    | str_nq_expression
    | num_eq_expression
    | num_nq_expression
    | num_gt_expression
    | num_lt_expression
    | num_gte_expression
    | num_lte_expression
    | datetime_between_expression;

str_eq_expression: VARIABLE '=' STRING;
str_nq_expression: VARIABLE '!=' STRING;

num_eq_expression: VARIABLE '=' NUMBER;
num_nq_expression: VARIABLE '!=' NUMBER;

num_gt_expression: VARIABLE '>' NUMBER;
num_gte_expression: VARIABLE '>=' NUMBER;
num_lt_expression: VARIABLE '<' NUMBER;
num_lte_expression: VARIABLE '<=' NUMBER;

datetime_between_expression: VARIABLE 'between' from 'and' to;

from: DATE_TIME;
to: DATE_TIME;

//
//
//
// ----------------------------------------------------------------------------
// Lexical rules
// ----------------------------------------------------------------------------
fragment DIGIT  : [0-9];
fragment TWO_DIGIT : DIGIT DIGIT;
fragment LETTER : [a-zA-Z] | '_';
fragment IDENTIFIER: LETTER (LETTER | DIGIT)*;
fragment DATE : TWO_DIGIT TWO_DIGIT '-' TWO_DIGIT '-' TWO_DIGIT;
fragment TIME : TWO_DIGIT ':' TWO_DIGIT ':' TWO_DIGIT;

WS: (' ' | '\t')+ -> skip;

NUMBER: DIGIT+('.'DIGIT)* | DIGIT+;

STRING
    : '\'' .*? '\''
    | '"' .*? '"'
    ;

fragment RESERVED: 'between' | 'and' | 'or';

VARIABLE: IDENTIFIER+('.'IDENTIFIER)*;

DATE_TIME: '\'' DATE '_' TIME '\'';
