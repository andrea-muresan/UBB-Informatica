bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dw -22, 145, -48, 127
    len equ ($-s)/2
    d times len db 0
    
    unu db 1
    

; Se da un sir de cuvinte s. Sa se construiasca sirul de octeti d, astfel incat d sa contina pentru fiecare pozitie din s:
;   - numarul de biti de 0, daca numarul este negativ
;   - numarul de biti de 1, daca numarul este pozitiv
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, d
        
        repeta:
            push ecx
            
            lodsw
            mov ebx, 0 ; aici conorizam
            mov ecx, 16
            
            cmp ax, 0
            jl negativ
            
            
            pozitiv:
                test ax, 0001h
                jz .gata
                
                inc ebx
                
                .gata:
                    shr ax, 1
                
                loop pozitiv
            jmp next
            negativ:
                test ax, 0001h
                jnz .gata
                
                inc ebx
                
                .gata:
                    shr ax, 1
                
                loop negativ
            
            next:
            mov al, bl
            stosb
            pop ecx
        loop repeta
        
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
