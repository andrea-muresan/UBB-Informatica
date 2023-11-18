bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


segment data use32 class=data
    s1 db 1, 3, 5, 7
    len_s equ $-s1
    s2 db 2, 6, 9, 4
    
    len equ $-s2
    d times len db 0

; Se dau doua siruri de octeti S1 si S2 de aceeasi lungime. Sa se obtina sirul D prin intercalarea elementelor celor doua siruri.
segment code use32 class=code
    start:
        
        mov ecx, len_s
        jecxz final
        
        mov esi, 0 ; contor pentru sirurile date
        mov edi, 0 ; contorul pentru sirul pe care il cream
        
        repeta:
            ; punem elementul din primul sir
            mov al, [s1 + esi]
            mov [d + edi], al
            inc edi
            
            ; punem elementul din al doilea sir
            mov al, [s2 + esi]
            mov [d + edi], al
            inc edi
        
            inc esi
            
        loop repeta
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
