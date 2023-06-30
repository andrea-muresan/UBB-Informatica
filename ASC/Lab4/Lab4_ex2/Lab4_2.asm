bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


segment data use32 class=data
    a dw 1010_1111_0010_0001b
    b dw 1111_0101_1100_1100b
    c dd 0

; Se dau cuvintele A si B. Se cere dublucuvantul C:
;   bitii 0-3 ai lui C coincid cu bitii 5-8 ai lui B
;   bitii 4-8 ai lui C coincid cu bitii 0-4 ai lui A
;   bitii 9-15 ai lui C coincid cu bitii 6-12 ai lui A
;   bitii 16-31 ai lui C coincid cu bitii lui B
segment code use32 class=code
    start:
        mov ebx, 0
        
        ; bitii 0-3 ai lui C coincid cu bitii 5-8 ai lui B
        mov ax, [b]
        and ax, 0000_0001_1110_0000b
        mov cl, 5
        shr ax, cl
        or bx, ax ; bx = 0000_0000_0000_1110b
        
        ; bitii 4-8 ai lui C coincid cu bitii 0-4 ai lui A
        mov ax, [a]
        and ax, 0000_0000_0001_1111b
        mov cl, 4
        shl ax, cl
        or bx, ax ; bx = 0000_0000_0001_1110b
        
        ; bitii 9-15 ai lui C coincid cu bitii 6-12 ai lui A
        mov ax, [a]
        and ax, 0001_1111_1100_0000b
        mov cl, 3
        shl ax, cl
        or bx, ax ; bx = 0111_1000_0001_1110b
        
        ; bitii 16-31 ai lui C coincid cu bitii lui B
        mov eax, [b]
        mov cl, 16
        shl eax, cl ; ebx = F5CC_781Eh
        or ebx, eax
        
        mov [c], ebx
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
