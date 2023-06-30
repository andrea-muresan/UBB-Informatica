bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 2, 1, 3, -3, -4, 2, -6
    l_a equ $-a
    b db 4, 5, -5, 7, -6, -2, 1
    l_b equ $-b
    len equ $-a
    d times len db 0

; Se dau 2 siruri de octeti A si B. Sa se construiasca sirul R care sa contina doar elementele pare si negative din cele 2 siruri.
segment code use32 class=code
    start:
        mov edi, 0
        
        ; a
        mov ecx, l_a
        jecxz next
        
        mov esi, 0
        repeta:
            mov al, [a + esi]
            cmp al, 0
            jge .gata
            
            test al, 01h
            jnz .gata
            
            mov [d + edi], al
            inc edi
            
            .gata:
                inc esi
            loop repeta
        
        next:
        ; b
        mov ecx, l_b
        jecxz final
        
        mov esi, 0
        repeta2:
            mov al, [b + esi]
            cmp al, 0
            jge .gata
            
            test al, 01h
            jnz .gata
            
            mov [d + edi], al
            inc edi
            
            .gata:
                inc esi
            loop repeta2
            
            
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
