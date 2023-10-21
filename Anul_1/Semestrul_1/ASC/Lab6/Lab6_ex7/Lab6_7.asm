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
    d times len db 0
    
    trei db 3

; 7. Se da un sir de dublucuvinte. Sa se obtina sirul format din octetii superiori ai 
; cuvitelor superioare din elementele sirului de dublucuvinte care sunt divizibili cu 3.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, d

        repeta:
            mov ax, 0
            times 4 lodsb
            
            mov bl, al
            bits 32 ; assembling for the 32 bits architecture

            div byte [trei]
            cmp ah, 0
            jne gata
            
            mov al, bl
            stosb
            
            gata:
            
        loop repeta
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
