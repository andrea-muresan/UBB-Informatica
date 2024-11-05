# Tema 1: Analizor lexical

## Enunt general:
Scrierea unui ANALIZOR LEXICAL pentru un minilimbaj de programare (MLP), ales ca subset al unui limbaj existent.

### 1. Specificarea minilimbajului de programare (MLP).
Limbajul trebuie sa contina cel putin anumite instructiuni si tipuri de date:
- 2 tipuri de date simple si un tip de date definit de utilizator
- instructiuni:
  - o instructiune de atribuire
  - o instructiune de intrare/iesire
  - o instructiune de selectie (conditionala)
  - o instructiune de ciclare

Pe langa acestea, vor exista unele restrictii suplimentare referitoare la identificatori si constante (vezi sectiunea 3.1).  
Se cere ca specificarea sa respecte structurile sintactice ale limbajului ales, intr-o forma (foarte) simplificata. De asemenea, ea va fi suficient de generala astfel incat sa descrie constructiile (limbajului) folosite pentru scrierea programelor de la pct.1

```
<program> ::= <bloc_importuri> <bloc_struct> <bloc_instructiuni>

<bloc_importuri> ::= “#include” “<iostream>” “using” “namespace” “std”;

<bloc_struct> ::= “typedef” “struct” <nume_struct> “{“ <instr_declarativa>”;”  “}””;”}
<nume_struct> ::= ID

<instr_declarativa> ::= <tip> <lista_id>
<tip> ::= “int” | “float” | <nume_struct>
<lista_id> ::= ID | ID “,” <lista_id>

<bloc_instructiuni> ::=  “int” “main””(“”)” “{“ <lista_instructiuni> “}”
<lista_instructiuni> ::= <instructiune> | <instructiune> <lista_instructiuni>
<instructiune> ::= <instr_declarativa>“;” | <instr_citire>”;” | <instr_scriere>”;” | <instr_atribuire>”;” | <instr_if>”;” | <instr_while>”;”

<instr_citire> ::= “cin” “>>” ID
<instr_scriere> ::= “cout” “<<” ID

<instr_atribuire> ::= ID “=” <expresie>
<expresie> ::= <variabila> | <variabila> <op_aritmetic> <expresie>
<variabila> ::= ID | CONST
<op_aritmetic> ::= “*” | “-” | “+” | “/” | “%”

<instr_if> ::= “if” “(“ <conditie> “)” “{“ <lista_instructiuni> “}”

<instr_while> ::= “while” “(“ <conditie> “)” “{” <lista_instructiuni> “}”

<conditie> ::= <expresie> <op_conditie> <expresie>
<op_conditie> ::= “!=” | “==” | “>” | “<” | “<=” | “>=”

ID ::= <litera> | <litera> <lista_ch>
<litera> ::= “a” | “b” | ... | “z” | “A” | “B” | ... | “Z”
<lista_ch> ::= <caracter> | <caracter> <lista_ch>
<caracter> ::= <litera> | <cifra>
<cifra> ::= “0” | “1” | ... | “9”

CONST ::= <cifra> | <cifra_nenula> <lista_cifre> | “3.14”
<cifra_nenula> ::= “1” | ... | “9”
<lista_cifre> ::= <cifra> | <cifra> <lista_cifre>
```

### 2. Se cer textele sursa a 3 mini-programe
care respecta specificatiile MLP date si care rezolva urmatoarele probleme:
- calculeaza perimetrul si aria cercului de o raza data data
  ```
  #include <iostream>
  using namespace std;

  int main () {
    float r;
    cin >> r;		
    float perim, arie;
    perim = 2 * 3.14 * r;
    arie = 3.14 * r * r;
    cout << perim;
    cout << arie;
  }
  ```
- determina cmmdc a 2 nr naturale
  ```
    #include <iostream>
    using namespace std;
    
    int main () {
      int a, b, r; 
      while (b != 0) {
        r = a % b;
        a = b;
        b = r;
      }
      cout << a;
    }
  ```
- calculeaza suma a n numere citite de la tastatura
  ```
  #include <iostream>
  using namespace std;

  int main () {
    int n, nr, i, suma;
    i = 0;
    while (i < n) {
      cin >> nr;
      suma = suma + nr;
      i = i + 1;
    }
    cout << suma;
  }
  ```
Se cere ca mini-programele sa fie compilate si executate in limbajul original ales.

### 3. Se cer textele sursa a doua programe care contin erori conform MLP-ului definit:
- Unul dintre programe contine doua erori care sunt in acelasi timp erori in limbajul original (pentru care MLP defineste un subset)
  ```
  #include <iostream>
  using namespace std;

  int main () {
    int i;
    i = 5;
    if (i === 1) { // eroare
      cout <<< i;  // eroare
    }
    i = i +1;
  }
  ```
- Al doilea program contine doua erori conform MLP, dar care nu sunt erori in limbajul original. Se cere ca acesta sa fie compilat si executat in limbajul original ales.
  ```
  #include <iostream>
  using namespace std;
  
  int main() {
    int i = 5; // eroare
    i = i ++; // eroare
  } 
  ```

### 4. Implementarea analizorului lexical
Analizorul lexical accepta la intrare un fisier text reprezentand un program sursa si intocmeste ca date de iesire tabelele:  
&nbsp; **FIP** - forma interna a programului sursa,  
&nbsp; **TS** - tabelui de simboluri.  
In plus, programul va trebui sa semnaleze erorile lexicale si locul in care apar.  

Niste restrictii suplimentare vor fi impuse analizoarelor lexicale.  
Acestea se vor diferentia dupa urmatoarele criterii:
1. tabela de simboluri:  
&nbsp;a. unica pentru identificatori si constante  
&nbsp;b. separat pentru identificatori si constante
2. organizarea tabelelor de simboluri:  
&nbsp;a. tabel ordonat lexicografic  
&nbsp;b. tabel arbore binar de cautare (ordine lexicografica)  
&nbsp;c. tabel de dispersie (hash)  

Se cer, de asemenea:
- implementarea structurilor de date cerute pentru tabela de simboluri;  
- *organizarea FIP si TS trebuie aleasa astfel incat accesul la informatiile  
pentru un atom lexical in TS sa se faca in Theta(1) pe baza informatiilor din FIP.*  
- salvarea FIP si TS in fisiere. Pozitia in TS (din fisierul corespunzator FIP) va fi numarul liniei din fisierul TS in care este stocata informatia referitoare la acel atom lexical.
