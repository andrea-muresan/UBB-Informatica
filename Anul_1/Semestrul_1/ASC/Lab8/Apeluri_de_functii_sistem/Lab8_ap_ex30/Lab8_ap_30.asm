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
    
    mesaj_int db "Numar: ", 0
    format db "%d", 0
    format_afis db "Minimul este: %d", 0

; 30. Se citesc de la tastatura numere (in baza 10) pana cand se introduce cifra 0. Determinaţi şi afişaţi cel mai mic număr dintre cele citite.
segment code use32 class=code
    start:
        mov ebx, 0FFF_FFFFh ; numar minim
        repeta:
            ; citire
            push dword mesaj_int
            call [printf]
            add esp, 4*1
            
            push dword n
            push dword format
            call [scanf]
            add esp, 4*2
            
            ; e zero?
            mov eax, [n]
            cmp eax, 0
            je afara
            
            ; comparare
            cmp eax, ebx
            jg next
            
            mov ebx, eax
            
            next:
            
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
