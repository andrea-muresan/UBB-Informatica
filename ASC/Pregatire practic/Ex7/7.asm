bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit
import exit msvcrt.dll

segment data use32 class=data
    numere dd 11h, 2h
    tabela db 'a', 'b', 'c'
    

; our code starts here
segment code use32 class=code
    start:
        mov ebx, tabela
        mov al, 2
        es xlat
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
