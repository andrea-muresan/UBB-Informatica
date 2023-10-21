bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    sir DD 12345678h, 1256ABCDh, 12AB4344h
    len equ ($-sir)/4
    d times len dd 0

; 17. Se da un sir de dublucuvinte. Sa se ordoneze descrescator sirul cuvintelor inferioare ale acestor dublucuvinte. Cuvintele superioare raman neschimbate.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        ; punem cuvintele inferioare in sirul d
        cld
        mov esi, sir
        mov edi, d
        repeta:
            movsw
            lodsw
        loop repeta
        
        ; sortare d
        mov ecx, len - 1
        do:
            mov ebx, ecx
            mov esi, 0
            do2:
                mov ax, [d + esi]
                mov dx, [d + esi + 2]
                
                cmp ax, dx
                ja no_swap
                
                mov [d + esi], dx
                mov [d + esi + 2], ax
                
                no_swap:
                
                inc esi
                dec ebx
                jnz do2
        loop do
        
        ; actualizam sirul initial
        mov ecx, len
        mov esi, 0 ; contor pentru sirul initial
        mov edi, 0 ; contor pentru sirul d
        bucla:
            mov ax, [d + edi]
            mov [sir + esi], ax
            add esi, 4
            add edi, 2
            
        loop bucla
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
