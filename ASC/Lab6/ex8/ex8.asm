; Se da un sir de dublucuvinte. Sa se obtina sirul format din octetii inferiori ai
; cuvintelor superioare din elementele sirului de dublucuvinte care sunt palindrom in scrierea in baza 10.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


segment data use32 class=data
    s DD 12345678h, 1A2C3C4Dh, 98FCDC76h
    len equ ($ - s)/4
    d times len db 0

segment code use32 class=code
    start:
        
        mov ecx, len
        jcxz final
        
        dec ecx
        
        mov esi, s
        mov edi, d
        
        repeta:
            lodsw
            lodsb ; al = octet inferior din cuvantul superior
            
            
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
