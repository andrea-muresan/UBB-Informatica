bits 32 

global start        

extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


segment data use32 class=data
    s1 db 1, 2, 3, 4
    len equ $-s1
    s2 db 5, 6, 7, 8
    d times len db 0
    

; Se dau doua siruri de octeti S1 si S2 de aceeasi lungime. Sa se construiasca sirul D astfel: fiecare element de pe pozitiile pare din D este suma elementelor de pe pozitiile corespunzatoare din S1 si S2, iar fiecare element de pe pozitiile impare are ca si valoare diferenta elementelor de pe pozitiile corespunzatoare din S1 si S2.
segment code use32 class=code
    start:
    
        mov ecx, len
        jecxz final
        
        mov esi, 0
        repeta:
            test esi, 0001h;
            jz par
            
            impar:
                mov al, [s1 + esi]
                sub al, [s2 + esi]
            
            jmp gata
                
            par:
                mov al, [s1 + esi]
                add al, [s2 + esi]
            
            gata:
                mov [d + esi], al
                inc esi
                
        
        loop repeta
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
