bits 32 

global start        

extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

segment data use32 class=data
    s db '+', '4', '2', 'a', '@', '3', '$', '*'
    len equ $-s
    d times len db 0
    
    caract_speciale db "!@#$%^&*", 0

; Se da un sir de caractere S. Sa se construiasca sirul D care sa contina toate caracterele speciale (!@#$%^&*) din sirul S.
segment code use32 class=code
    start:
        
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        
        mov ebx, 0
        
        repeta:
            lodsb
            push ecx
            mov edi, caract_speciale
            
            mov ecx, 8
            cauta:
                scasb
                je gasit
            loop cauta
            
            jmp gata
            
            gasit:
                mov [d + ebx], al
                inc ebx
            gata:
                pop ecx
        
        loop repeta       
        
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
