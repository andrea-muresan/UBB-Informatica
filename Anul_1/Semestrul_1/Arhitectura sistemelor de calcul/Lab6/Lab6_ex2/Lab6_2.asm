bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dw 1432h, 8675h, 0ADBCh
    len equ ($-s)/2
    d times len dd 0
    
    zece dw 10h
    aux resb 4

; 2. Se da un sir de cuvinte. Sa se obtina din acesta un sir de dublucuvinte, in care fiecare dublucuvant va contine nibble-urile despachetate pe octet (fiecare cifra hexa va fi precedata de un 0), aranjate crescator in interiorul dublucuvantului.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz final
        
        cld
        mov esi, s
        mov edi, d
        
        repeta:
            push ecx
            
            ; punem fiecare cifra hexa in cate un octet in aux
            mov ecx, 4
            mov ebx, 0
            lodsw
            bucla:
                mov dx, 0
                div word [zece]
                mov [aux + ebx], dx
                inc ebx
            loop bucla
            
            ; sortam aux
            push edi
            mov ecx, 3
            do:
                mov ebx, ecx
                mov edi, 0
                do2:
                    mov al, [aux + edi]
                    mov dl, [aux + edi + 1]
                    
                    cmp al, dl
                    jl no_swap
                    
                    mov [aux + edi], dl
                    mov [aux + edi + 1], al
                    
                    no_swap:
                    
                    inc edi
                    dec ebx
                    jnz do2
                        
            loop do
            pop edi
            mov eax, [aux]
            stosd

            pop ecx
        loop repeta
        
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
