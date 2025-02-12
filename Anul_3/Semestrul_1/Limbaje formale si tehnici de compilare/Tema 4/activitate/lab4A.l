%{
	#include "lab4A.tab.h"
	
	int line = 1;
%}

%option noyywrap

ID 	  [a-zA-Z][a-zA-Z0-9]*
DIGIT 0|([1-9][0-9]*)
NZD   [1-9]*
CONST ([-]?[0-9]("."[0-9]+)?[f]?)|([-]?[1-9][0-9]*("."[0-9]+)?[f]?)

%%	

	
{DIGIT} 	 			{ return DIGIT; }
{ID}  					{ return ID; }
{CONST}  				{ return CONST; }
	
"[" 		 			{ return LSB; }
"]" 		 			{ return RSB; }
"*" 		 			{ return MUL; }

	
[\n] 		 			{ line++; }
%%