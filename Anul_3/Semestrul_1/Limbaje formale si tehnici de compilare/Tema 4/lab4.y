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
	%token INCLUDE USING NAMESPACE STD IOSTREAM 
	%token INT MAIN VOID FLOAT STRUCT TYPEDEF
	%token LPR RPR LBR RBR SEMICOLON DOT COMMA
	%token CIN COUT 
	%token IF WHILE
	
	%left ELSE LS RS
	%left ASSIGN DIFF
	%left LT GT LTE GTE EQ
	%left PLUS MINUS MUL DIV MODULO
%%
	program : bloc_importuri bloc_struct bloc_instructiuni;

	bloc_importuri : INCLUDE IOSTREAM USING NAMESPACE STD SEMICOLON;
	
	bloc_struct : TYPEDEF STRUCT ID LBR instr_declarativa SEMICOLON RBR SEMICOLON; 
	
	instr_declarativa : tip lista_id;
	tip : INT | FLOAT;
	lista_id : ID | ID COMMA lista_id;

	bloc_instructiuni : INT MAIN LBR lista_instructiuni RBR;
	lista_instructiuni : instructiune | instructiune lista_instructiuni;
	instructiune : instr_declarativa SEMICOLON
			| instr_citire SEMICOLON
			| instr_scriere SEMICOLON
			| instr_atribuire SEMICOLON
			| instr_if SEMICOLON
			| instr_while SEMICOLON;

	instr_citire : CIN RS ID;
	instr_scriere : COUT LS ID;

	instr_atribuire : ID ASSIGN expresie;
	expresie : variabila | variabila op_aritmetica expresie;
	variabila : ID | CONST | DIGIT;
	op_aritmetica : MUL | MINUS | PLUS | DIV | MODULO;

	instr_if : IF LPR conditie RPR LBR lista_instructiuni RBR;

	instr_while : WHILE LPR conditie RPR LBR lista_instructiuni RBR;

	conditie : expresie op_conditie expresie;
	op_conditie : DIFF | EQ | GT | LT | LTE | GTE;

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