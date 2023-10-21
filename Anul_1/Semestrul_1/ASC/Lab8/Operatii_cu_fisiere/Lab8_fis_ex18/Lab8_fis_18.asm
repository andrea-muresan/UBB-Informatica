bits 32

global start        


extern exit, fopen, fclose, fread, printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "r", 0
    descriptor dd -1
    
    n dd 0
    anterior dd 0
    
    format_cit db "%c", 0
    format_afis db "%d", 0
    
    speciale db ". ", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 18. Se da un fisier text care contine litere, spatii si puncte. Sa se citeasca continutul fisierului, sa se determine numarul de cuvinte si sa se afiseze pe ecran aceasta valoare. (Se considera cuvant orice secventa de litere separate prin spatiu sau punct)
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
        
        mov ebx, 0
        
        repeta:
            ; fread(n, 1, 1, descriptor)
            push dword [descriptor]
            push dword 1
            push dword 1
            push dword n
            call [fread]
            add esp, 4*4
            
            cmp eax, 0
            je afara
            
            mov ecx, 2
            mov al, byte [n]
            mov edi, speciale
            .cauta:
                scasb
                je gasit
            loop .cauta
            
            negasit:
                jmp next
            gasit:
                mov ecx, 2
                mov al, byte [anterior]
                mov edi, speciale
                .cauta:
                    scasb
                    je next
                loop .cauta
                
                inc ebx
            
            next:
                mov al, byte [n]
                mov [anterior], al
                jmp repeta
                
              
            
        afara:
        
        ; printf(format_afis, ebx)
        push ebx
        push format_afis
        call [printf]
        add esp, 4*2
        
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
