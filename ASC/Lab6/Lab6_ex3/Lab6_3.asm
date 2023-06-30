bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dd 0702090Ah, 0B0C0304h, 05060108h
    len equ ($-s)/2
    d times len db 0
    
    patru equ 4

; 3. Se da un sir de 3 dublucuvinte, fiecare dublucuvant continand 2 valori pe cuvant (despachetate, deci fiecare cifra hexa e precedata de un 0). Sa se creeze un sir de octeti care sa contina acele valori (impachetate deci pe un singur octet), ordonate crescator in memorie, acestea fiind considerate numere cu semn.

segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, d
        
        repeta:
            lodsb
            mov bl, al
            lodsb
            shl al, patru
            add al, bl
            stosb
            
        loop repeta
        
        ; sortare
        mov ecx, len - 1
        do:
            mov ebx, ecx
            mov esi, 0
            do2:
                mov al, [d + esi]
                mov dl, [d + esi + 1]
                
                cmp al, dl
                jl no_swap
                
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
