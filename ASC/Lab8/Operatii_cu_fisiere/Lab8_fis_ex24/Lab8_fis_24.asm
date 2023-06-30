bits 32 

global start        

extern exit, fopen, fclose, printf, fprintf
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import printf msvcrt.dll    
import fprintf msvcrt.dll    

segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    text db "Ana are 12 mere. Maria are 3 pere. 12 + 3 = 15 fructe", 0
    len equ $ - text - 1
    c db 'C', 0
    
    format_afisare db "%s", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 24. Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se inlocuiasca toate CIFRELE din textul dat cu caracterul 'C'. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut prin inlocuire in fisier.
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
        
        mov ecx, len
        repeta:
            cmp [text + ecx], byte '0'
            jl next
            
            cmp [text + ecx], byte '9'
            jg next
            
            mov bl, [c]
            mov [text + ecx], bl
            
            next:
        loop repeta
        
        ; fprintf(descriptor, format_afisare, val)
        push dword text
        push dword format_afisare
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
