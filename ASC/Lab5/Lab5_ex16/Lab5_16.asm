bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 'a', 'b', 'c', 'b', 'e', 'f'
    l_s1 equ $-s1
    s2 db '1', '2', '3', '4', '5'
    l_s2 equ $-s2
    len equ (l_s2+1)/2 + l_s1/2
    d times len db 0

; Se dau doua siruri de caractere S1 si S2. Sa se construiasca sirul D prin concatenarea elementelor de pe pozitiile impare din S2 cu elementele de pe pozitiile pare din S1.
segment code use32 class=code
    start:
        mov edi, 0
        
        ; s2
        mov ecx, l_s2
        jecxz next
        
        dec ecx
        
        mov esi, 0
        repeta:
            mov al, [s2 + esi]
            mov [d + edi], al
            inc edi
            
            add esi, 2
            cmp esi, ecx
            jle repeta
        
        next:
        ; s1
        mov ecx, l_s1
        jecxz next
        
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
