bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

segment data use32 class=data
    s1 db '+', '2', '2', 'b', '8', '6', 'X', '8'
    l_s1 equ $ - s1
    s2 db 'a', '4', '5'
    l_s2 equ $ - s2
    
    len equ (l_s1 + 1)/2 + l_s2
    d times len db 0

; Se dau doua siruri de caractere S1 si S2. Sa se construiasca sirul D prin concatenarea elementelor sirului S2 in ordine inversa cu elementele de pe pozitiile pare din sirul S1.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        mov edi, 0 ; contor pentru sirul destinatie
        
        ;sirul 2
        mov ecx, l_s2
        jecxz next
        
        repeta:
            mov al, [s2 + ecx - 1]
            mov [d + edi], al
            inc edi
        loop repeta
        
        next: ; sirul 1
        mov ecx, l_s1
        cmp ecx, 2
        jl final
        
        dec ecx
        
        mov esi, 1
        repeta2:
            mov al, [s1 + esi]
            mov [d + edi], al
            inc edi
            
            add esi, 2
            cmp esi, ecx
            jle repeta2
            
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
