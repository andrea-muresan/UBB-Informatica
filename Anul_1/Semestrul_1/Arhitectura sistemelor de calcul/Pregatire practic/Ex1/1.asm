bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fscanf, printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fscanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    nume_fisier db "input.txt", 0
    mod_acces db "r", 0
    descriptor dd -1
    
    numar dd 0
    zece dd 10h
    
    format_hexa db "%x", 0
    format_hexa_afis db "%x ", 0
    format_dec db "%d", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 1.	Se dă un șir de 10 numere în baza 16 în fișierul input.txt. 
; Să se determine cifra minimă din fiecare număr.
; Să se afișeze acest șir al cifrelor minime, în baza 10, pe ecran.

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
        
        mov ecx, 10
        repeta:
            push ecx
                
                ; fscanf(descriptor, format_hexa, numar)
                push dword numar
                push dword format_hexa
                push dword [descriptor]
                call [fscanf]
                add esp, 4*3
                
                mov ebx, 0
                mov bl, 10
                mov edx, 0
                mov eax, [numar]
                bucla:
                    mov edx, 0
                    
                    div dword [zece]
                    
                    cmp dl, bl
                    jg next
                    
                    mov bl, dl
                    
                    next:
                        cmp eax, 0
                        je gata
                    jmp bucla
                    
                gata: 
                ; printf(format_hexa, ebx)
                push dword ebx
                push dword format_hexa_afis
                call [printf]
                add esp, 4*2
                
            pop ecx
        loop repeta
        
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
