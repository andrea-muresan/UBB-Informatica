%{
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#define SIZE 101  

int posConst = 0;
int posId = 0;
int pos = 0;
int crtPosFip = 0;
int code = 2;
int lineNumber = 1;

struct Entry {
    int code; // 0 - id, 1 - const, 2-N - other
    char* data;
    int tsPos; 
};

int size = SIZE;
struct Entry* fip[SIZE] = { NULL };
char* tsId[SIZE] = { NULL };
char* tsConst[SIZE] = { NULL };

// Hash function
int hashFunction(char* str) {
    int hash = 0;
    while (*str) {
        hash = (hash * 31 + *str) % SIZE; 
        str++;
    }
    return hash;
}

// Forward declaration of the findIdPos function
int findIdPos(char* key);
int findConstPos(char* key);

// Adding an identifier to the hash table
void addId(char* key) {
    if (findIdPos(key) != -1) return;

    int index = hashFunction(key);
    
    while (tsId[index] != NULL) {
        index = (index + 1) % size;
    }
    
    tsId[index] = strdup(key);
}

// Adding a constant to the hash table
void addConst(char* key) {
    if (findConstPos(key) != -1) return;

    int index = hashFunction(key);
    
    while (tsConst[index] != NULL) {
        index = (index + 1) % size;
    }
    
    tsConst[index] = strdup(key);
}

// Finding the position of an identifier
int findIdPos(char* key) {
    int index = hashFunction(key);
    while (tsId[index] != NULL) {
        if (strcmp(tsId[index], key) == 0) {
            return index; 
        }
        index = (index + 1) % size; 
    }
    return -1; 
}

// Finding the position of a constant
int findConstPos(char* key) {
    int index = hashFunction(key);
    while (tsConst[index] != NULL) {
        if (strcmp(tsConst[index], key) == 0) {
            return index; 
        }
        index = (index + 1) % size; 
    }
    return -1; 
}

// Adding to FIP
void addToFip(struct Entry* item) {
    fip[crtPosFip++] = item;
}

// Finding in FIP
int findFipPos(char* key) {
    for (int index = 0; index < crtPosFip; index++) {
        if (strcmp(fip[index]->data, key) == 0) {
            return index; 
        }
        index = (index + 1) % size; 
    }
    return -1;
}

// Helper function to create FIP entry
struct Entry* createEntry(int code, char* data, int position) {
    struct Entry* entry = (struct Entry*)malloc(sizeof(struct Entry)); 
    entry->code = code;   
    entry->data = data;
    entry->tsPos = position;
    return entry; 
}


char* string_copy(char *string) {
    int size = (int)strlen(string);
    char* new_string = malloc((size+1) * sizeof (char));
    for (int i = 0; i <= size; ++i)
        new_string[i] = string[i];
    return new_string;
}

// Print results
void printResults() {
    printf("\n\nTS ID\n");
    char* crt = NULL;
    for (int i = 0; i < SIZE; i++) {
        crt = tsId[i];
        if (crt != NULL) {
            printf("%d\t%s\n", i, tsId[i]);
        }
    }
    printf("\n\n");

    printf("\n\nTS CONST\n");
    for (int i = 0; i < SIZE; i++) {
        crt = tsConst[i];
        if (crt != NULL) {
            printf("%d\t%s\n", i, tsConst[i]);
        }
    }
    printf("\n\n");

    

    printf("FIP\t\tpos\t\tts\n");
    for (int index = 0; index < crtPosFip; index++) {
        if (fip[index]->code == 0) {
            printf("%s\t\t%d\t\t%d\n", fip[index]->data, fip[index]->code, fip[index]->tsPos);
        } else if (fip[index]->code == 1) {
            printf("%s\t\t%d\t\t%d\n", fip[index]->data, fip[index]->code, fip[index]->tsPos);
        } else {
            printf("%s\t\t%d\n", fip[index]->data, fip[index]->code);
        }
    }
}

%}

%option noyywrap
%option caseless

digit               [0-9]
octal_digit         [0-7]
ndigit              [1-9]
hex_digit           [0-9a-fA-F]
hex_prefix          0[xX]   

digit_sequence      {digit}+
decimal_exponent    [eE][+-]?{digit_sequence}
suffix              [fF]

int_const       ({ndigit}{digit}*)|(0{octal_digit}*)|({hex_prefix}{hex_digit}+)|(0[bB][01]+)
float_const     ({digit}+{decimal_exponent}{suffix}?)|({digit_sequence}\.{decimal_exponent}?{suffix}?)|({digit_sequence}?\.{digit_sequence}{decimal_exponent}?{suffix}?)
id              [a-zA-Z][a-zA-Z0-9]{0,7}
bad_id          [0-9]+[a-zA-Z][a-zA-Z0-9]*
bad_const       0[0-9]+
bad_float        (0{digit}+{decimal_exponent}{suffix}?)|(0{digit_sequence}\.{decimal_exponent}?{suffix}?)|(0{digit_sequence}?\.{digit_sequence}{decimal_exponent}?{suffix}?)

%%


"#include"|"<iostream>"|"using"|"namespace"|"std"|"main"|"int"|"float"|"cin"|"cout"|"if"|"else"|"while"|"cattimp"|"executa"|"sfcattimp" {
    char* data = string_copy(yytext);
    printf("keyword: \t\t %s\n", data);
    
    int position = findFipPos(data);
    if (position == -1) {
        struct Entry* entry = createEntry(code, data, -1);
        code++;
        addToFip(entry);
    } else {
        struct Entry* entry = createEntry(position, data, -1);
        addToFip(entry);
    }
    
}

";"|"("|")"|"{"|"}"|"," {
    char* data = string_copy(yytext);
    printf("separator: \t\t%s\n", data);
    
    int position = findFipPos(data);
    if (position == -1) {
        struct Entry* entry = createEntry(code, data, -1);
        code++;
        addToFip(entry);
    } else {
        struct Entry* entry = createEntry(position, data, -1);
        addToFip(entry);
    }
}

"!="|">>"|"<<"|"&&"|"=="|">"|"<"|"="|"-"|"+"|"*" {
    char* data = string_copy(yytext);
    printf("operator: \t\t%s\n", data);
    
    int position = findFipPos(data);
    if (position == -1) {
        struct Entry* entry = createEntry(code, data, -1);
        code++;
        addToFip(entry);
    } else {
        struct Entry* entry = createEntry(position, data, -1);
        addToFip(entry);
    }
}

{bad_id} { 
    printf("*********** Illegal token %s at line %d ! ***********\n", yytext, lineNumber); 
    printResults();
    exit(1); 
}

{bad_const} { 
    printf("*********** Illegal token %s at line %d ! ***********\n", yytext, lineNumber); 
    printResults();
    exit(1); 
}

{bad_float} { 
    printf("*********** Illegal token %s at line %d ! ***********\n", yytext, lineNumber); 
    printResults();
    exit(1); 
}

{id} { 
    char* data = string_copy(yytext);
    printf("id: \t\t\t%s\n", data);
    
    if(findIdPos(data) == -1) {
        addId(data);
    }
    struct Entry* entry = createEntry(0, data, findIdPos(data));
    addToFip(entry);
}

{int_const} { 
    char* data = string_copy(yytext);
    printf("int const: \t\t%s\n", data);
    
    if(findConstPos(data) == -1) {
        addConst(data);
    }
    struct Entry* entry = createEntry(1, data, findConstPos(data));
    addToFip(entry);
}

{float_const} {
    char* data = string_copy(yytext);
    printf("float const: \t\t%s\n", data);
    
    if(findConstPos(data) == -1) {
        addConst(data);
    }
    struct Entry* entry = createEntry(1, data, findConstPos(data));
    addToFip(entry);
}
\n|\r|\r\n                  { lineNumber++; }
[ \t]+                      { ; }
.                           { 
    printf("********* Illegal token %s at line %d ! *********\n", yytext, lineNumber); 
    printResults();
    exit(1); 
}
%%

int main(int argc, char** argv) {
    addToFip(createEntry(0, "ID", -1));
    addToFip(createEntry(1, "CONST", -1));
    FILE *fp;
    fp = fopen(argv[1], "r");
    
    yyin = fp;
    yylex();
    
    printResults();
}