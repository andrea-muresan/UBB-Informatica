bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

segment data use32 class=data
    s db 1, 2, 4, 6, 10, 20, 25
    len equ $-s
    d times len-1 db 0

; Se da un sir de octeti S de lungime l. Sa se construiasca sirul D de lungime l-1 astfel incat elementele din D sa reprezinte diferenta dintre fiecare 2 elemente consecutive din S.
segment code use32 class=code
    start:
        mov ecx, len
        dec ecx
        jecxz final
        
        mov esi, 0
        repeta:
            mov al, [s + esi + 1]
            sub al, [s + esi]
            mov [d + esi], al
            
            inc esi
        
        loop repeta
  
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
