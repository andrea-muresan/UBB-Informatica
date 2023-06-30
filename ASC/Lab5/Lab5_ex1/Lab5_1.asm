bits 32

global start        


extern exit               
import exit msvcrt.dll    


segment data use32 class=data
    s db 1, 2, 3, 4
    len equ $-s
    d times len - 1 dw 0
    

; Se da un sir de octeti S de lungime l. Sa se construiasca sirul D de lungime l-1 astfel incat elementele din D sa reprezinte produsul dintre fiecare 2 elemente consecutive S(i) si S(i+1) din S.
segment code use32 class=code
    start:
        
        mov ecx, len
        dec ecx
        jecxz final
        
        
        mov esi, 0
        
        repeta:
            mov al, [s + esi]
            imul byte [s + esi + 1]
            mov [d + esi], ax
            
            inc esi
            
        loop repeta
            
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
