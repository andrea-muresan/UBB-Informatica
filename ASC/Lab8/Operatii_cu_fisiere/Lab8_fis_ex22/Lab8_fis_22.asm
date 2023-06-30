bits 32 

global start        

extern exit, fopen, fclose, printf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    nume_fisie db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    n dw 2365
    zece dw 10
    
    format_afis db "%d", 10, 13, 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; Se da numele unui fisier si un numar pe cuvant scris in memorie. Se considera numarul in reprezentarea fara semn. Sa se scrie cifrele zecimale ale acestui numar ca text in fisier, fiecare cifra pe o linie separata.
segment code use32 class=code
    start:
        ; fopen (nume_fisie, mod_acces)
        push dword mod_acces
        push dword nume_fisie
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        mov ax, word [n] 
        repeta:
            cmp ax, 0
            je afara
            
            mov edx, 0
            div word [zece]
            
            push ax
            ; fprintf(descriptor, format_afis, edx)
            push dword edx
            push dword format_afis
            push dword [descriptor]
            call [fprintf]
            add esp, 4*3
            
            pop ax
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
