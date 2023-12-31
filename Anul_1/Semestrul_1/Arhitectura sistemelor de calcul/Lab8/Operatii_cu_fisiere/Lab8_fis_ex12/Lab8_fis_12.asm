bits 32

global start        

extern exit, fopen, fclose, scanf, fprintf, printf
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
    
    n dd 0
    
    mesaj_citire db "Introduceti un numar: ", 0
    format_citire db "%d", 0
    format_afisare db "%d ", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 12. Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura numere si sa se scrie valorile citite in fisier pana cand se citeste de la tastatura valoarea 0.
segment code use32 class=code
    start:
        ; fopnen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        repeta:
            ; mesaj citire
            push dword mesaj_citire
            call [printf]
            add esp, 4
            
            ; scanf(format, val)
            push dword n
            push dword format_citire
            call [scanf]
            add esp, 4*2
            
            ; comparam cu 0
            mov eax, [n]
            cmp eax, 0
            je afara
            
            ; fprintf(descriptor, format, val)
            push dword [n]
            push dword format_afisare
            push dword [descriptor]
            call [fprintf]
            add esp, 4*3
            
            jmp repeta
            
        afara:
        
        ;fclose(descriptor)
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
