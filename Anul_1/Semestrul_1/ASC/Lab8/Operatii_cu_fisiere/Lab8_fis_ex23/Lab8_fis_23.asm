bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fprintf, printf 
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    n dw 23
    zeceh dw 10h
    
    format db "%x", 10, 0 
    
    eroare db "Deschiderea fisierului a esuat", 0

; 23. Se da numele unui fisier si un numar pe cuvant scris in memorie. Sa se scrie cifrele hexazecimale ale acestui numar ca text in fisier, fiecare cifra pe o linie separata.
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
        
        mov ax, [n]
        repeta:
            mov dx, 0 
            div word [zeceh]
            
            mov ebx, 0
            mov bx, dx
            push ax
            
            ; fprintf(descriptor, format, val)
            push dword ebx
            push dword format
            push dword [descriptor]
            call [fprintf]
            add esp, 4*3
            
            pop ax
            cmp ax, 0
            jne repeta
        
        
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
