bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, scanf, printf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
import fprintf msvcrt.dll

segment data use32 class=data
    ;nume_fisier db "string.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    string times 100 db 0
    len dd 0
    
    format_str db "%s", 0
    format_car db "%c", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 8. Citesc string tastatura, creem fisier string.txt, adaugam in fisier prima data majusculele apoi minusculele invers de cum apar in sir.
segment code use32 class=code
    start:
        ; scanf(format_str, string)
        push dword string
        push dword format_str
        call [scanf]
        add esp, 4*2
        
        mov esi, 0
        bucla:
            cmp byte [string + esi], 0
            je adauga
            
            inc esi
            jmp bucla
            
        
        
        adauga:
            mov eax, ".txt"
            mov [string + esi], eax
            
        mov [len], esi
                   
    
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword string
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        cld
        mov ecx, [len]
        mov esi, string
        repeta:
            push ecx
            
                mov eax, 0
                mov al, byte [string + ecx - 1]
                cmp al, 'A'
                jl .next
                
                cmp al, 'Z'
                jg .next
                
                ; printf(descriptor, format_car, eax)
                push dword eax
                push dword format_car
                push dword [descriptor]
                call [fprintf]
                add esp, 4*3
                
                .next:
            
            pop ecx
        loop repeta
        
        mov ecx, [len]
        mov esi, string
        repeta2:
            push ecx
            
                mov eax, 0
                mov al, byte [string + ecx - 1]
                cmp al, 'a'
                jl .next
                
                cmp al, 'z'
                jg .next
                
                ; printf(descriptor, format_car, eax)
                push dword eax
                push dword format_car
                push dword [descriptor]
                call [fprintf]
                add esp, 4*3
                
                .next:
            
            pop ecx
        loop repeta2
        
        ; fclose(descriptor)
        push dword [descriptor]
        call [fclose]
        add esp, 4
        
        jmp final
        eroare_deschidere:
            push dword eroare
            call [printf]
            add esp, 4
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
