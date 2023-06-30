bits 32 ; assembling for the 32 bits architecture


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
    
    caract dd 0
    litz dd 0
    lity dd 0
    
    format_cit db "%c", 0
    format_afis db "Numar litere 'y': %d | Numar litere 'z': %d", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 16. Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de litere 'y' si 'z' si sa se afiseze aceaste valori. Numele fisierului text este definit in segmentul de date.
segment code use32 class=code
    start:
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare
        
        mov [descriptor], eax
        
        mov ebx, 0 ; in ebx contorizam cifrele de y si z
        repeta:
            ; fread(caract, 1, 1, descriptor)
            push dword [descriptor]
            push dword 1
            push dword 1
            push dword caract
            call [fread]
            add esp, 4*4
            
            cmp eax, 0
            je afara
            
            mov al, [caract]
            
            cmp al, 'z'
            jne verifica_y
            
            inc dword [litz]
            jmp next
            
            verifica_y:
                cmp al, 'y'
                jne next
                
                inc dword [lity]
            
            next:
            
            jmp repeta
            
        afara:
        
        ; printf(format, lity, litz)
        push dword [litz]
        push dword [lity]
        push dword format_afis
        call [printf]
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
