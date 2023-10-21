bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, fread, fopen, fclose
import exit msvcrt.dll    
import printf msvcrt.dll    
import fread msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "r", 0
    descriptor dd -1
    
    buffer db 0
    
    nr_vocale_citite db 0
    vocale db "AEIOUaeiou", 0
    
    format_afis db "%d vocale", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 1. Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de vocale si sa se afiseze aceasta valoare. Numele fisierului text este definit in segmentul de date.
segment code use32 class=code
    start:
        mov ebx, 0 ; stocam numarul vocalelor citite
        
        ; EAX = fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        ; verificam daca fisierul a fost deschis cu succes
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        repeta:
            ; fread(buffer, int size, len, descriptor)
            push dword [descriptor]
            push dword 1
            push dword 1
            push dword buffer
            call [fread]
            add esp, 4*4
            
            cmp eax, 0
            je afisare
            
            cld
            mov al, [buffer]
            mov edi, vocale
            mov ecx, 10
            
            cauta:
                scasb
                je gasit
            loop cauta
            
            jmp gata
            
            gasit:
                inc ebx
            gata:
            
            jmp repeta
        
        
        afisare:
            push dword ebx
            push dword format_afis
            call [printf]
            add esp, 4*2
        
        
        ; fclose(descriptor)
        push dword [descriptor]
        call [fclose]
        add esp, 4*1
        
        
        jmp final
        eroare_deschidere:
            push dword eroare
            call [printf]
            add esp, 4*1
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
