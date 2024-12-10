
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     DIGIT = 258,
     CONST = 259,
     ID = 260,
     NZD = 261,
     INCLUDE = 262,
     USING = 263,
     NAMESPACE = 264,
     STD = 265,
     IOSTREAM = 266,
     INT = 267,
     MAIN = 268,
     VOID = 269,
     FLOAT = 270,
     STRUCT = 271,
     TYPEDEF = 272,
     LPR = 273,
     RPR = 274,
     LBR = 275,
     RBR = 276,
     SEMICOLON = 277,
     DOT = 278,
     COMMA = 279,
     CIN = 280,
     COUT = 281,
     IF = 282,
     WHILE = 283,
     RS = 284,
     LS = 285,
     ELSE = 286,
     DIFF = 287,
     ASSIGN = 288,
     EQ = 289,
     GTE = 290,
     LTE = 291,
     GT = 292,
     LT = 293,
     MODULO = 294,
     DIV = 295,
     MUL = 296,
     MINUS = 297,
     PLUS = 298
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


