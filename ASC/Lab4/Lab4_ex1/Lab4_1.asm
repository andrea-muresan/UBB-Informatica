bits 32 ; assembling for the 32 bits architecture
global start        

extern exit               
import exit msvcrt.dll    

segment data use32 class=data
    a dw 1010_1111_0010_0001b
    b dw 1111_0101_1100_1100b
    c dd 0

; Se dau cuvintele A si B. Sa se obtina dublucuvantul C:
;   bitii 0-4 ai lui C coincid cu bitii 11-15 ai lui A
;   bitii 5-11 ai lui C au valoarea 1
;   bitii 12-15 ai lui C coincid cu bitii 8-11 ai lui B
;   bitii 16-31 ai lui C coincid cu bitii lui A
segment code use32 class=code
    start:
        mov ebx, 0 
        
        ; bitii 0-4 ai lui C coincid cu bitii 11-15 ai lui A
        mov ax, [a]
        and ax, 1111_1000_0000_0000b
        mov cl, 11
        shr ax, cl
        or bx, ax
        
        ; bitii 5-11 ai lui C au valoarea 1
        or bx, 0000_1111_1110_0000b
        
        ; bitii 12-15 ai lui C coincid cu bitii 8-11 ai lui B
        mov ax, [b]
        and ax, 0000_1111_0000_0000b
        mov cl, 4
        shl ax, cl
        or bx, ax
        
        ; bitii 16-31 ai lui C coincid cu bitii lui A
        mov eax, [a]
        mov cl, 16
        shl eax, cl
        or ebx, eax
        
        mov [c], ebx
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
