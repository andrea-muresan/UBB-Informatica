bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

segment data use32 class=data
    s db 1, 5, 3, 8, 2, 9
    len equ $-s
    par times len db 0
    impar times len db 0

; Se da un sir de octeti S. Sa se obtina sirul D1 ce contine toate numerele pare din S si sirul D2 ce contine toate numerele impare din S.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        mov esi, 0 ; contor pentru sirul dat
        mov edx, 0 ; contor pentru sirul par
        mov ebx, 0 ; contor pentru sir impar
        
        repeta:
            mov al, [s + esi]
            test al, 01h
            jz nr_par
            
            nr_impar:
                mov [impar + ebx], al
                inc ebx
                jmp gata
            
            nr_par:
                mov [par + edx], al
                inc edx
            
            gata:
                inc esi
            
        loop repeta
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
