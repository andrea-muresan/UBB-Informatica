bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
 
segment data use32 class=data
    n dd 0
    
    mesajn db "Numar: ", 0
    format_afis db "Maximul este %d", 0
    format db "%d", 0

; 28. Se citesc de la tastatura numere (in baza 10) pana cand se introduce cifra 0. Determinaţi şi afişaţi cel mai mare număr dintre cele citite.
segment code use32 class=code
    start:

        mov ebx, 0FFFF_FFFFh ; maximul
        repeta:
            ; citire
            push dword mesajn
            call [printf]
            add esp, 4*1
            
            push dword n
            push dword format
            call [scanf]
            add esp, 4*2
            
            ; verificam daca e 0
            mov eax, [n]
            cmp eax, 0
            je afara
            
            ; comparam
            cmp ebx, eax
            jg gata
            
            mov ebx, eax
            
            gata:
            jmp repeta
        
        afara:
        
        ; afisare
        push dword ebx
        push dword format_afis
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
