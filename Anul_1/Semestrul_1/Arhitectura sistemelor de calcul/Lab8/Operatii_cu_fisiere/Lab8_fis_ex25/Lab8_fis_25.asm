bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
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
    
    text db "Mama are 5 mere. Merg la mare.", 0
    len equ $ - text - 1
    
    format_afis db "%c", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 25. Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se inlocuiasca toate spatiile din textul dat cu caracterul 'S'. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut prin inlocuire in fisier.
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
            
            cmp al, ' '
            jne afisare
            
            mov al, 'S'
            
            afisare:
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
