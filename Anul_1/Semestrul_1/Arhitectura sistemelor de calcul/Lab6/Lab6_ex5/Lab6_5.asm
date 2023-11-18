bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 7, 33, 55, 19, 46
    l_s1 equ $-s1
    s2 db 33, 21, 7, 13, 27, 19, 55, 1, 46 
    len equ $-s2
    d times len db 0

;5. Se dau doua siruri de octeti s1 si s2. Sa se construiasca sirul de octeti d, care contine pentru fiecare octet din s2 pozitia sa in s1, sau 0 in caz contrar.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s2
        mov edi, d
        
        repeta:
            push ecx
            lodsb
            
            mov ecx, l_s1  
            mov ebx, 0
            cauta:
                cmp [s1 + ebx], al
                je gasit
                inc ebx
            loop cauta
            
            negasit:
                mov al, 0
                stosb
                jmp gata
            gasit:
                mov al, bl
                inc al
                stosb
            gata:
                pop ecx

        loop repeta
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
