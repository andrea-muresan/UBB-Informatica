bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, printf, fread
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fread msvcrt.dll

segment data use32 class=data
    nume_fisier db "input.txt", 0
    mod_acces db "r", 0
    descriptor dd -1
    
    len equ 100
    string times len db 0
    format_afis db "%c", 0
    lungime dd 0
    
    eroare db "Deshiderea fisierului a esuat", 0

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
        
        ; fread(string, 1, len, descriptor)
        push dword [descriptor]
        push dword len
        push dword 1
        push dword string
        call [fread]
        add esp, 4*4
        
        cmp eax, 0
        je final
        
        mov [lungime], eax
        
        mov ecx, [lungime]
        repeta:
            push ecx
            
            mov eax, 0
            mov al, byte [string + ecx - 1]
            
            ; printf(format_afis, eax)
            push dword eax
            push dword format_afis
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
