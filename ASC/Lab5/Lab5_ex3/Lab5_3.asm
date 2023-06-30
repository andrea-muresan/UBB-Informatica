bits 32 

global start        

extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

segment data use32 class=data
    s1 db 1, 2, 3, 4
    ls1 equ $-s1
    s2 db 5, 6, 7
    ls2 equ $-s2
    
    len equ $-s1
    d times len db 0
    
; Se dau doua siruri de octeti S1 si S2. Sa se construiasca sirul D prin concatenarea elementelor din sirul S1 1uate de la stanga spre dreapta si a elementelor din sirul S2 luate de la dreapta spre stanga.
segment code use32 class=code
    start:
        
        mov esi, 0
        
        mov edi, 0
        mov ecx, ls1
        repeta:
            mov al, [s1+edi]
            mov [d+esi], al
            inc esi
            inc edi
        loop repeta
            
        mov edi, 0
        mov ecx, ls2
        bucla:
            mov al, [s2+edi]
            mov [d+esi], al
            inc esi
            inc edi
        loop bucla
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
