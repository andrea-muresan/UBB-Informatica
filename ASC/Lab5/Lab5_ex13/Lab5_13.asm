bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

segment data use32 class=data
    s db 1, 5, 3, 8, 2, 9
    l_s equ $-s
    l_d1 equ ($-s+1)/2
    l_d2 equ ($-s)/2
    d1 times l_d1 db 0
    d2 times l_d2 db 0

; Se da un sir de octeti S. Sa se construiasca sirul D1 ce contine elementele de pe pozitiile pare din S si sirul D2 ce contine elementele de pe pozitiile impare din S.
segment code use32 class=code
    start:
        mov ecx, l_d1
        jecxz next
        
        dec ecx
        mov esi, 0
        mov edi, 0
        
        repeta:
            mov al, [s + esi]
            mov [d1 + edi], al
            inc edi
            
            add esi, 2
            cmp esi, l_s
            jle repeta
        
        next:
        mov ecx, l_d2
        jecxz final
        
        dec ecx
        mov esi, 1
        mov edi, 0
        
        repeta2:
            mov al, [s + esi]
            mov [d2 + edi], al
            inc edi
            
            add esi, 2
            cmp esi, l_s
            jle repeta2
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
