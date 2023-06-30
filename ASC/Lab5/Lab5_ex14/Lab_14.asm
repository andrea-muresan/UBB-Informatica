bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s db 1, 3, -2, -5, 3, -8, 5, 0
    len equ $-s
    d1 times len db 0
    d2 times len db 0

; Se da un sir de octeti S. Sa se construiasca un sir D1 care sa contina toate numerele pozitive si un sir D2 care sa contina toate numerele negative din S.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        mov esi, 0 ; contor s
        mov ebx, 0 ; contor d1
        mov edx, 0 ; contor d2
        
        repeta:
            mov al, [s + esi]
            cmp al, 0
            jl negativ
            
            pozitiv:
                mov [d1 + ebx], al
                inc ebx
                jmp gata
            negativ:
                mov [d2 + edx], al
                inc edx
            gata:
                inc esi
        loop repeta
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
