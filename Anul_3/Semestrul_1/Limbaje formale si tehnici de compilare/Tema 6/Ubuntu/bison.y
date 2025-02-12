%{
#include <stdio.h>
#include <string.h>
#include <ctype.h>

int yylex(void);
int yyerror(const char *s);

void addToDataSection(char segment[500]);
void addToCodeSection(char segment[500]);

%}

%union{
	int nr;
	char varname[2000];
}

%error-verbose
%start program
%token INCLUDE
%token IOSTREAM
%token USING
%token NAMESPACE
%token STD
%token INT
%token MAIN
%token CIN
%token COUT
%token ID
%token CONST

%type<varname> CONST ID operand expresie 

%%

program :	import bloc_instructiuni
		;

import	:	INCLUDE IOSTREAM USING NAMESPACE STD ';'
		;
			
bloc_instructiuni	:	INT MAIN '(' ')' '{' lista_instructiuni '}'
					;
					
lista_instructiuni	:	instructiune lista_instructiuni
					|	instructiune
					;
					
instructiune	:	instr_declarativa
				|	instr_citire
				|	instr_scriere
				|	instr_atribuire
				;
				
instr_declarativa	:	INT ID ';'	{
							char tmp[500];
							tmp[0] = 0;
							strcat(tmp, "\t");
							strcat(tmp, $2);
							strcat(tmp, " dd 0\n");
							addToDataSection(tmp);
					}
					;
			
instr_citire	:	CIN ID ';'	{
						char tmp[500];
						tmp[0] = 0;
						strcat(tmp, "\tcall io_readint\n");
						strcat(tmp, "\tmov [");
						strcat(tmp, $2);
						strcat(tmp, "], eax\n\n");
						addToCodeSection(tmp);
				}
				;
				
instr_scriere	:	COUT ID ';'	{
						char tmp[500];
						tmp[0] = 0;
						strcat(tmp, "\tmov eax, [");
						strcat(tmp, $2);
						strcat(tmp, "]\n");
						strcat(tmp, "\t");
						strcat(tmp, "call io_writeint\n\n");
						addToCodeSection(tmp);
				}
				;
				
instr_atribuire	:	ID '=' expresie ';'	{
						char tmp[500];
						tmp[0] = 0;
						strcat(tmp, "\tmov eax, 0\n");
						strcat(tmp, $3);
						strcat(tmp, "\tmov [");
						strcat(tmp, $1);
						strcat(tmp, "], eax\n");
						addToCodeSection(tmp);
				}
				;
				
expresie	:	expresie '+' operand	{
					char tmp[500];
					tmp[0] = 0;
					strcat(tmp, "\tadd eax, ");
					if(isdigit($3[0])){
						// caz constanta
						strcat(tmp, $3);
					}
					else {
						// caz id
						strcat(tmp, "[");
						strcat(tmp, $3);
						strcat(tmp, "]");
					}
					strcat(tmp, "\n\n");
					strcpy($$, $1);
					strcat($$, tmp);
			}
			|	expresie '-' operand	{
					char tmp[500];
					tmp[0] = 0;
					strcat(tmp, "\tsub eax, ");
					if(isdigit($3[0])){
						// caz constanta
						strcat(tmp, $3);
					}
					else {
						// caz id
						strcat(tmp, "[");
						strcat(tmp, $3);
						strcat(tmp, "]");
					}
					strcat(tmp, "\n\n");
					strcpy($$, $1);
					strcat($$, tmp);
			}
			|	expresie '*' operand	{
					char tmp[500];
					tmp[0] = 0;
					strcat(tmp, "\tmov ebx, ");
					if(isdigit($3[0])){
						// caz constanta
						strcat(tmp, $3);
					}
					else {
						// caz id
						strcat(tmp, "[");
						strcat(tmp, $3);
						strcat(tmp, "]");
					}
					strcat(tmp, "\n");
					strcat(tmp, "\timul ebx\n\n");
					strcpy($$, $1);
					strcat($$, tmp);
			}
			|	expresie '/' operand	{
					char tmp[500];
					tmp[0] = 0;
					strcat(tmp, "\tmov edx, 0\n");
					strcat(tmp, "\tmov ebx, ");
					if(isdigit($3[0])){
						// caz constanta
						strcat(tmp, $3);
					}
					else {
						// caz id
						strcat(tmp, "[");
						strcat(tmp, $3);
						strcat(tmp, "]");
					}
					strcat(tmp, "\n");
					strcat(tmp, "\tidiv ebx\n\n");
					strcpy($$, $1);
					strcat($$, tmp);
			}
			|	operand					{
					char tmp[500];
					tmp[0] = 0;
					strcat(tmp, "\tadd eax, ");
					if(isdigit($1[0])){
						// caz constanta
						strcat(tmp, $1);
					}
					else {
						// caz id
						strcat(tmp, "[");
						strcat(tmp, $1);
						strcat(tmp, "]");
					}
					strcat(tmp, "\n");
					strcpy($$, tmp);
			}
			;
			
operand		: ID			{ strcpy($$, $1); }
			| CONST			{ strcpy($$, $1); }
			;
%%