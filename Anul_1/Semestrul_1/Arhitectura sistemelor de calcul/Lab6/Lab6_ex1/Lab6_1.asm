bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


segment data use32 class=data
    s dd 127F5678h, 0ABCDABCDh
    len equ ($-s)/4
    d times len dd 0

;  Se da un sir de dublucuvinte continand date impachetate (4 octeti scrisi ca un singur dublucuvant). Sa se obtina un nou sir de dublucuvinte, in care fiecare dublucuvant se va obtine dupa regula: suma octetilor de ordin impar va forma cuvantul de ordin impar, iar suma octetilor de ordin par va forma cuvantul de ordin par. Octetii se considera numere cu semn, astfel ca extensiile pe cuvant se vor realiza corespunzator aritmeticii cu semn.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, d
        
        repeta:
            mov bx, 0 ; suma para
            mov dx, 0 ; suma impara
            
            push ecx
            
            mov ecx, 2
            bucla:
                lodsb
                cbw
                adc bx, ax
                lodsb
                cbw
                add dx, ax
            loop bucla
            
            ; lodsb
            ; cbw
            ; adc bx, ax
            ; lodsb
            ; cbw
            ; add dx, ax
            ; lodsb
            ; cbw
            ; adc bx, ax
            ; lodsb
            ; cbw
            ; add dx, ax
           
            mov ax, bx
            stosw
            mov ax, dx
            stosw
            
            pop ecx
        
        loop repeta
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
