bits 32 

global start        

extern exit, fopen, fclose, fprintf, printf
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import fprintf msvcrt.dll    
import printf msvcrt.dll    

segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    text db "Ana si Maria merG in Andorra.", 0
    len equ $ - text - 1
    
    dif equ 'a' - 'A'
    
    format_afis db "%c", 0
    
    eroare db "Deschiderea fisierului a esuat", 0
    

; 14. Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se transforme toate literele mari din textul dat in litere mici. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.
segment code use32 class=code
    start:
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        cld
        mov esi, text
        mov ecx, len
        repeta:
            push ecx 
            
            mov eax, 0
            lodsb
            cmp al, 'A'
            jl afis
            
            cmp al, 'Z'
            jg afis
            
            add al, dif

            afis:
                ; fprintf(descriptor, format_afis, eax)
                push eax
                push dword format_afis
                push dword [descriptor]
                call [fprintf]
                add esp, 4*3
                
            pop ecx
            loop repeta
                
        
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
