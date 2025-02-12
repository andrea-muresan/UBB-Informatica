Comenzi:

* In Ubuntu:  
  * bison -d bison.y  
  * flex flex.lxi  
  * gcc bison.tab.c lex.yy.c  
  * ./a.out in.txt  
  * cat out.asm  

* In Windows:  (se copiaza fisierul out.asm din ubuntu)
  * nasm -f win32 out.asm  
  * nlink out.obj -lio -o out.exe  
  * out.exe  


!!! Mi-e nu imi respecta ordinea operatiilor, aparent trebuia.
