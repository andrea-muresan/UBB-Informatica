bits 32

global start        

extern exit, fopen, fclose, printf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    text db "Ana are 23 de ani, 10 pere, 5 pere si 100 de lei.", 0
    len equ $ - text - 1
    
    format_afis db "%c", 0
    
    eroare db "Deschiderea fisierului a esuat", 0
    

; 21. Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, cifre si spatii. Sa se inlocuiasca toate cifrele de pe pozitii impare cu caracterul ‘X’. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.
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
        
        cld
        mov esi, 0
        mov ecx, len
        repeta:
            push ecx
            
            mov al, byte [text + esi]
            cmp al, '0'
            jl afisare
            
            cmp al, '9'
            jg afisare
            
            test esi, 0001h
            jz afisare
            
            mov al, 'X'
            
            afisare:
                ; fprintf(descriptor, format_afis, eax)
                push eax
                push dword format_afis
                push dword [descriptor]
                call [fprintf]
                add esp, 4*3
                
            inc esi
            pop ecx
        loop repeta
        
        
        jmp final
        eroare_deschidere:
            push dword eroare
            call [printf]
            add esp, 4
            
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
