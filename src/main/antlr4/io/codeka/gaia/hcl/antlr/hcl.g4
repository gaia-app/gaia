grammar hcl;

file
  : directive+
  ;

directive
  : variableDirective
  ;

variableDirective
  : 'variable' identifier variableBlock
  ;

variableBlock
  : '{' type? description? r_default? '}'
  ;

type
  : 'type' '=' STRING
  ;

description
  : 'description' '=' STRING
  ;

r_default
  : 'default' '=' STRING
  ;

identifier
  : STRING
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

WS
  : [ \t\r\n]+ -> skip
  ; // skip spaces, tabs, newlines
