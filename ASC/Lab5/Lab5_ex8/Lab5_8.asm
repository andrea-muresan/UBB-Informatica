bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s db 'a', 'A', 'b', 'B', '2', '%', 'x', 'M'
    len equ $-s
    d times len db 0

; Se da un sir de caractere S. Sa se construiasca sirul D care sa contina toate literele mari din sirul S.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        mov esi, 0 ; contorul sirului pe care il parcurgem
        mov edi, 0 ; contorul sirului pe care il cream
        repeta:
            mov al, [s + esi]
            cmp al, 'A'
            jl gata
            
            cmp al, 'Z'
            jg gata
            
            mov [d + edi], al
            inc edi
            
            gata:
                inc esi
        loop repeta
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
