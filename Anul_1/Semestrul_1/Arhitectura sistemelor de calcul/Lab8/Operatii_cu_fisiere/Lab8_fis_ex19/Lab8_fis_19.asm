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
    
    text db "Ana are 5 mere. A mancat 3, dar a mai cumparat 8.", 0
    len equ $ - text - 1
    
    format_afis db "Suma este: %d", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 19. Se dau in segmentul de date un nume de fisier si un text (poate contine orice tip de caracter). Sa se calculeze suma cifrelor din text. Sa se creeze un fisier cu numele dat si sa se scrie suma obtinuta in fisier.
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
        mov ebx, 0 ; retinem suma in ebx
        repeta:
            mov eax, 0
            lodsb 
            cmp al, '0'
            jl next
            
            cmp al, '9'
            jg next
            
            sub al, '0'
            add ebx, eax
            
            next:
            
        loop repeta
        
        ; fprintf(descriptor, format_afis, ebx)
        push ebx
        push dword format_afis
        push dword [descriptor]
        call [fprintf]
        add esp, 4*3
            
        
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
