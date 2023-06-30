; cuvintele care contin '$' in interiorul lor sunt luate in considerare (ex, '$Ana', 'me$re', 'pere$')

bits 32 

global start        

extern exit, fopen, fclose, scanf, fprintf, printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    dolar times 50 db 0
    n times 50 db 0
    
    format_cit db "%s", 0
    format_afis db "%s ", 0
    
    eroare db "DEschiderea fisierului a esuat", 0
    

; Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura cuvinte pana cand se citeste de la tastatura caracterul '$'. Sa se scrie in fisier doar cuvintele care contin cel putin o litera mare (uppercase).
segment code use32 class=code
    start:
        mov al, '$'
        mov [dolar], al
        
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        repeta:
            ; scanf(format_cit, n)
            push dword n
            push dword format_cit
            call [scanf]
            add esp, 4*2
            
            ; verificam daca caracterul citit e '$'
            cld
            mov esi, n
            mov edi, dolar
            mov ecx, 50
            repe cmpsb
            jecxz afara
        
            ; verificare litera mare
            mov esi, n
            mov ecx , 50
            bucla:
                lodsb
                cmp al, 'A'
                jl not_uppercase
                
                cmp al, 'Z'
                jg not_uppercase
                
                ; fprintf(descriptor, format_afis, n)
                push dword n
                push dword format_afis
                push dword [descriptor]
                call [fprintf]
                add esp, 4*3
                jmp next
                
                not_uppercase:
                
                loop bucla
                
            next:
                
            ; clear n
            mov esi, n
            mov edi, n
            mov ecx, 50
            ciclu:
                lodsb
                xor al, al
                stosb
            loop ciclu    
            
            jmp repeta
            
        afara:
        
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
