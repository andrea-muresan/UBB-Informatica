bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, printf, fscanf, scanf, getchar
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
import fscanf msvcrt.dll
import getchar msvcrt.dll

segment data use32 class=data
    nume_fisier times 15 db 0
    mod_acces db "r", 0
    descriptor dd -1
    
    cuvant times 20 db 0
    numar dd 0
    
    format_string db "%s", 0
    format_numar db "%d", 0
    format_afis db "%s ", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 21.	Sa se citeasca de la tastatura un nume de fisier si un numar. 
; Sa se citeasca din fisierul dat cuvintele separate prin spatii si sa se afiseze in consola cuvintele care sunt pe pozitiile multipli de numarul citit de la tastatura.

segment code use32 class=code
    start:
        ; scanf(format_string, nume_fisier)
        push dword nume_fisier
        push dword format_string
        call [scanf]
        add esp, 4*2
        
        ; call [getchar]
        
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        ; scanf(format_string, nume_fisier)
        push dword numar
        push dword format_numar
        call [scanf]
        add esp, 4*2
        
        mov esi, 1
        repeta:
            ; fscanf(descriptor, format_string, cuvant)
            push dword cuvant
            push dword format_string
            push dword [descriptor]
            call [fscanf]
            add esp, 4*3
            
            cmp eax, 1
            jne afara
            
            mov eax, esi
            div byte [numar]
            
            cmp ah, 0
            jne next
            
            ; printf(format_afis, cuvant)
            push dword cuvant
            push dword format_afis
            call [printf]
            add esp, 4*2
            
            next:
                inc esi
                jmp repeta
        afara:
        
        
            
        
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
