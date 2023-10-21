bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        


extern exit, fopen, fclose, printf, fscanf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fscanf msvcrt.dll
  
segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "r", 0
    descriptor dd -1
    
    numar dd 0
    
    format_cit db "%d", 0
    format_afis db "%d", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 16. Se citesc nr din fisier, sa se faca suma nr mai mici decat 8 si sa se afiseze pe ecran.
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
            ; fscanf(descriptor, format_cit, numar)
            push dword numar
            push dword format_cit
            push dword [descriptor]
            call [fscanf]
            add esp, 4*3
            
            cmp eax, 1
            jne afisare
            
            mov eax, [numar]
            cmp eax, 8
            jge next
            
            add ebx, eax
            
            next:
                jmp repeta
            
        afisare:
            ; printf(format_afis, ebx)
            push ebx
            push dword format_afis
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
