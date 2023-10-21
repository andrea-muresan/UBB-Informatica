; 4. Se da un sir de octeti s. Sa se construiasca sirul de octeti d, care contine pe fiecare pozitie numarul de biti 1 ai octetului de pe pozitia corespunzatoare din s.


bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


segment data use32 class=data
    s db 5, 25, 55, 127
    len equ $-s
    d times len db 0
    doi db 2


segment code use32 class=code
    start:
        mov ecx, len
        jcxz final
        
        mov esi, s
        mov edi, d
        
        
        repeta:
            cld
            lodsb ; al = ds:esi, inc esi
            
            mov bl, 0 ; contorizam cati de 1 apar in scrierea binara a numarului
            bucla:
                mov ah, 0 ;ax <- cuvant
                div byte [doi]
                
                cmp ah, 0
                je nada
                
                inc bl
                
                nada:
                
                cmp al, 0
                jne bucla
            
            mov al, bl
            stosb
                     
        loop repeta
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
