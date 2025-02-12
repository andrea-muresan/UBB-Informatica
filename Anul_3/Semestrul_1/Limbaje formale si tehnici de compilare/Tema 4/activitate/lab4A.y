%{
	#include <stdio.h>
	#include <stdlib.h>
	#include <string.h>

	extern int yylex();
	extern int yyparse();
	extern FILE* yyin;
	extern int line;
	extern int yyerror(const char* msg)
	{
		printf("Error at line %d: %s\n", line, msg);
		exit(1);
	}
	
	void print_line_info() {
		printf("Line %d: ", line);
	}
%}
	%token DIGIT CONST ID NZD
	%token LSB RSB 
	%left MUL
%%
	program : calcul;
	calcul : elem | elem MUL calcul;
	elem : DIGIT | expr_par;
	expr_par : LSB calcul RSB;

	
%%

int main(int argc, char* argv[]){
	++argv, --argc;
	
	if(argc > 0){
		yyin = fopen(argv[0], "r");
	} else {
		yyin = stdin;
	}

	while(!feof(yyin)){
		yyparse();
	}
	
	printf("Result: OK\n");
	return 0;
}