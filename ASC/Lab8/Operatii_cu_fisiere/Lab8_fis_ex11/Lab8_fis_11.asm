bits 32 

global start        

extern exit, fopen, fclose, fprintf, printf, scanf
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
    
    dolar times 50 db 0
    n times 50 db 0
    
    
    format_cit db "%s", 0
    format_afis db "%s ", 0
    
    
    eroare db "Deschiderea fisierului a esuat", 0

; 11. Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura cuvinte si sa se scrie in fisier cuvintele citite pana cand se citeste de la tastatura caracterul '$'.
segment code use32 class=code
    start:
        mov al, '$'
        mov [dolar], al
        
        ; fopen
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        repeta:
            ; scanf
            push dword n
            push dword format_cit
            call [scanf]
            add esp, 4*2
            
            cld
            mov esi, n 
            mov edi, dolar
            mov ecx, 50
            repe cmpsb
            jecxz afara
            
            ; fprintf
            push dword n
            push dword format_afis
            push dword [descriptor]
            call [fprintf]
            add esp, 4*3
            
            ; clear
            ; mov ecx, 50
            ; mov esi, n
            ; mov edi, n
            ; bucla:
                ; lodsb
                ; xor al, al
                ; stosb
            ; loop bucla
            
            jmp repeta
        
        afara:
        
        ; fclose
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
