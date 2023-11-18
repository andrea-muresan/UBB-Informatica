bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dd 12345678h, 1A2B3C4Dh, 0FE98DC76h
    len equ ($-s)/4
    b1 times len db 0
    b2 times len db 0

; Se da un sir A de dublucuvinte. Construiti doua siruri de octeti  
; - B1: contine ca elemente partea superioara a cuvintelor superioare din A
; - B2: contine ca elemente partea inferioara a cuvintelor inferioare din A
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, b1
        mov ebx, 0 ; entru b2
        
        repeta:
            lodsb ; partea inferioara a cuvintelor inferioare
            mov [b2 + ebx], al
            inc ebx
            
            times 2 lodsb ; partea superioara a cuvintelor superioare
            movsb
            
        loop repeta
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
