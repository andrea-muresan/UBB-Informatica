%{
	#include "lab4.tab.h"
	
	int line = 1;
%}

%option noyywrap

ID 	  [a-zA-Z][a-zA-Z0-9]*
DIGIT 0|([1-9][0-9]*)
NZD   [1-9]*
CONST ([-]?[0-9]("."[0-9]+)?[f]?)|([-]?[1-9][0-9]*("."[0-9]+)?[f]?)

%%	
"#include"   			{ return INCLUDE; }
"cin" 		 			{ return CIN; }
"cout" 		 			{ return COUT; }
"main()" 	 			{ return MAIN; }
"namespace"  			{ return NAMESPACE; }
"std" 		 			{ return STD; }
"int" 		 			{ return INT; }
"void" 		 			{ return VOID; }
"float" 	 			{ return FLOAT; }
"<iostream>" 			{ return IOSTREAM; }
"using" 	 			{ return USING; }
"if" 		 			{ return IF; }
"else" 	 	 			{ return ELSE; }
"while" 	 			{ return WHILE; }
"struct" 	 			{ return STRUCT; }
"typedef"          		{ return TYPEDEF; }
	
{DIGIT} 	 			{ return DIGIT; }
{ID}  					{ return ID; }
{CONST}  				{ return CONST; }
	
"{" 		 			{ return LBR; }
"}" 		 			{ return RBR; }
"(" 		 			{ return LPR; }
")" 		 			{ return RPR; }
">>" 		 			{ return RS; }
"<<" 		 			{ return LS; }
"<" 		 			{ return LT; }
">" 		 			{ return GT; }
">=" 		 			{ return GTE; }
"<=" 		 			{ return LTE; }
"+" 		 			{ return PLUS; }
"-" 		 			{ return MINUS; }
"*" 		 			{ return MUL; }
"/" 		 			{ return DIV; }
";" 		 			{ return SEMICOLON; }
"=="					{ return EQ; }
"=" 		 			{ return ASSIGN; }
"!=" 		 			{ return DIFF; }
"." 		 			{ return DOT; }
","						{ return COMMA; }
"%"						{ return MODULO; }
	
[\n] 		 			{ line++; }
%%