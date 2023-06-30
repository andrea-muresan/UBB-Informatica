bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import printf msvcrt.dll


                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


segment data use32 class=data
    s DD 12345607h, 1A2B3C15h
    len equ $-s
    d times len db 0
    
    format db "%d ", 0

; Se da un sir S de dublucuvinte.
; Sa se obtina sirul D format din octetii dublucuvintelor din sirul D sortati in ordine descrescatoare in interpretarea fara semn.
segment code use32 class=code
    start:
        
        mov ecx , len
        jecxz final
        
        mov esi, s
        mov edi, d
        cld
        
        repeta:
            movsb
        loop repeta
        
        mov ecx, len
        dec ecx
        
        do:
            mov ebx, ecx
            mov esi, 0
            
            do2:
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
