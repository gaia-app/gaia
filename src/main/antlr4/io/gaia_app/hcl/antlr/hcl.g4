grammar hcl;

file
  : directive+
  ;

directive
  : providerDirective
  | terraformDirective
  | resourceDirective
  | variableDirective
  | outputDirective
  ;

providerDirective
  : 'provider' STRING object
  ;

terraformDirective
  : 'terraform' object
  ;

resourceDirective
  : 'resource' STRING STRING object
  ;

variableDirective
  : 'variable' STRING variableBlock
  ;

variableBlock
  : '{' (variableType|variableDescription|variableDefault)+ '}'
  ;

outputDirective
  : 'output' STRING outputBlock
  ;

outputBlock
  : '{' (outputValue|outputDescription|outputSensitive)+ '}'
  ;

outputValue
  : 'value' '=' expression
  ;

outputDescription
  : 'description' '=' STRING
  ;

outputSensitive
  : 'sensitive' '=' BOOLEAN
  ;

variableType
  : 'type' '=' type
  ;

type
  : TYPE
  | 'list'
  | 'list(' type ')'
  | 'map(' type ')'
  | 'object' '(' object ')'
  ;

object
  : '{' '}'
  | '{' field+ '}'
  ;

field
  : IDENTIFIER '=' expression
  | IDENTIFIER object // for dynamic blocks
  ;

variableDescription
  : 'description' '=' STRING
  ;

variableDefault
  : 'default' '=' expression
  ;

expression
  : STRING
  | NUMBER
  | BOOLEAN
  | array
  | object
  | complexExpression
  ;

functionCall
  : IDENTIFIER '(' expression ( ',' expression )* ','? ')'
  ;

complexExpression
  : IDENTIFIER
  | complexExpression '.' complexExpression // attribute access
  | complexExpression '[' index ']' // indexed array access
  | complexExpression '.' index // indexed attribute access
  | functionCall
  ;

array
  : '[' ']'
  | '[' expression ( ',' expression )* ','? ']'
  ;

index
  : NUMBER
  | '*'
  ;

BOOLEAN
  : 'true'
  | '"true"'
  | 'false'
  | '"false"'
  ;

TYPE
  : 'string'
  | '"string"'
  | 'number'
  | '"number"'
  | 'bool'
  | '"bool"'
  | 'any'
  ;

IDENTIFIER:         Letter LetterOrDigit*;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;
fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;

/**
 * STRING Lexer Rule comes from the JSON grammar
 * https://github.com/antlr/grammars-v4/blob/master/json/JSON.g4
 */
STRING
   : '"' (ESC | SAFECODEPOINT)* '"'
   ;

fragment ESC
   : '\\' (["\\/bfnrt] | UNICODE)
   ;
fragment UNICODE
   : 'u' HEX HEX HEX HEX
   ;
fragment HEX
   : [0-9a-fA-F]
   ;
fragment SAFECODEPOINT
   : ~ ["\\\u0000-\u001F]
   ;

NUMBER
   : '0' | [1-9] [0-9]*
   ;

// comments and whitespaces
COMMENT:      '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN);
HAS_COMMENT:  '#' ~ [\r\n]* -> channel(HIDDEN);
WS:           [ \t\r\n]+    -> channel(HIDDEN); // skip spaces, tabs, newlines
