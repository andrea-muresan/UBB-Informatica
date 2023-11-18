bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf
import exit msvcrt.dll   
import printf msvcrt.dll   
import scanf msvcrt.dll   

segment data use32 class=data
    a dd 0
    b dd 0
    rezultat resd 2
    
    mesaja db "a = ", 0
    mesajb db "b = ", 0
    format db "%d", 0
    

; 1. Sa se citeasca de la tastatura doua numere (in baza 10) si sa se calculeze produsul lor. Rezultatul inmultirii se va salva in memorie in variabila "rezultat" (definita in segmentul de date).
segment code use32 class=code
    start:
        push dword mesaja
        call [printf]
        add esp, 4*1
        
        ; scanf %, a
        push dword a
        push dword format
        call [scanf]
        add esp, 4*2
        
        push dword mesajb
        call [printf]
        add esp, 4*1
        
        ; scanf %, b
        push dword b
        push dword format
        call [scanf]
        add esp, 4*2
        
        mov eax, [a]
        mov ebx, [b]
        
        mul ebx
        mov [rezultat], eax
        mov [rezultat + 4], edx
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
