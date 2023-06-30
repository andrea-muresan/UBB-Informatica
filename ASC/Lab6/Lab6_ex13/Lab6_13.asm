bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s DD 12345607h, 1A2B3C15h, 13A33412h
    len equ ($-s)/4
    d times len db 0
    
    sapte db 7

; Se da un sir S de dublucuvinte.
; Sa se obtina sirul D format din octetii inferiori ai cuvintelor inferioare din elementele sirului de dublucuvinte, care sunt multiplii de 7.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, d 
        
        repeta:
            lodsb
            mov bl, al ;copie al
            mov ah, 0
            
            
            div byte [sapte] 
            cmp ah, 0
            jne gata
            
            ; e divizibil cu sapte
            mov al, bl
            stosb
            
            gata:
                add esi, 3
        
        loop repeta
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
