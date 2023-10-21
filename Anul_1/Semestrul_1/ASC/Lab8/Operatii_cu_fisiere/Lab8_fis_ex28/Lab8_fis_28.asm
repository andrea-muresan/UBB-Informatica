bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, fprintf, fopen, fclose, printf

import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll


segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    n times 50 db 0
    
    
    format db "%s", 0
    format_afis db "%s ", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; our code starts here
segment code use32 class=code
    start:
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        
        repeta:
            ; scanf(format, val)
            push dword n
            push dword format
            call [scanf]
            add esp, 4*2
            
            mov al, '$'
            cmp al, [n]
            je gata
            
            mov esi, n
            mov ecx, 45
            bucla:
                lodsb
                cmp al, 'a'
                jl next
                
                cmp al, 'z'
                jg next
                
                jmp printeaza
                
                next:
                
            loop bucla
            
            jmp clear
            printeaza:
                ; fprintf(descriptor, format, val)
                push dword n
                push dword format_afis
                push dword [descriptor]
                call [fprintf]
                add esp, 4*3
                
            clear:
                mov edi, n
                mov ecx, 45
                .do:
                    mov al, 0
                    stosb
                loop .do
            
            jmp repeta
        
        
        gata:
        
        ; fclose(descriptor)
        push dword [descriptor]
        call [fclose]
        add esp, 4
        
        jmp final
        eroare_deschidere:
            push dword eroare
            call [printf]
            add esp, 4
            
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
