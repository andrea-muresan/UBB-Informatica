bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s db 5, 25, 55, 127
    len equ $-s
    d times len db 0

;4. Se da un sir de octeti s. Sa se construiasca sirul de octeti d, care contine pe fiecare pozitie numarul de biti 1 ai octetului de pe pozitia corespunzatoare din s.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, d
        
        repeta:
            mov bl, 0 ; contorizam biti de 1
            mov ax, 0
            lodsb
            bucla:
                test ax, 01h
                jz nu_e_unu
                inc bl

                nu_e_unu:
                
                mov dl, 2
                div dl
                mov ah, 0
                
                cmp ax, 0
                jne bucla
                
           mov al, bl
           stosb
            
        loop repeta
        
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
