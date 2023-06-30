bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; 14. Se da un sir S de dublucuvinte.
; Sa se obtina sirul D format din octetii dublucuvintelor din sirul D sortati in ordine crescatoare in interpretarea fara semn.
segment data use32 class=data
    s DD 12345607h, 1A2B3C15h, -1
    len equ $-s
    d times len db 0

; our code starts here
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, d 
        
        times len movsb
        
        ; sortare
        
        dec ecx
        do:
            mov ebx, ecx
            mov esi, 0
            do2:
                mov al, [d + esi]
                mov dl, [d + esi + 1]
                
                cmp al, dl
                jb no_swap
                
                mov [d + esi], dl
                mov [d + esi + 1], al
                
                no_swap:
                
                inc esi
                dec ebx
                jnz do2
                
        loop do
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
