bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


segment data use32 class=data
    s db 1, 2, 3, 4, 5, 6, 7, 8
    len equ $-s 
    d times len db 0

; Se da un sir de octeti S. Sa se construiasca sirul D astfel: sa se puna mai intai elementele de pe pozitiile pare din S iar apoi elementele de pe pozitiile impare din S.
segment code use32 class=code
    start:
        
        mov ecx, len
        cmp ecx, 3
        jl final
        
        dec ecx
        
        mov edi, 0 ; contorul sirului pe care il cream
        
        mov esi, 0 ; contorul cu care parcurgem sirul dat - poz pare
        repeta:
            mov al, [s + esi]
            mov [d + edi], al
            inc edi
            
            add esi, 2
            cmp esi, ecx
            jle repeta
            
        mov esi, 1 ; contorul cu care parcurgem sirul dat - poz impare
        bucla:
            mov al, [s + esi]
            mov [d + edi], al
            inc edi
            
            add esi, 2
            cmp esi, ecx
            jle bucla
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
