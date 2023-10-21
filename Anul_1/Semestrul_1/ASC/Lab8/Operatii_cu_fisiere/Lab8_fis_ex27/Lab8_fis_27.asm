bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

extern exit, fopen, fclose, fscanf, fprintf, printf
import exit msvcrt.dll 
import fopen msvcrt.dll 
import fclose msvcrt.dll 
import fscanf msvcrt.dll 
import fprintf msvcrt.dll 
import printf msvcrt.dll 
   
segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "a+", 0
    descriptor dd -1
    
    n dd 0
    
    format_cit db "%d", 0
    format_afis db " %d", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 27. Se da un fisier text. Fisierul contine numere (in baza 10) separate prin spatii. Sa se citeasca continutul acestui fisier, sa se determine minimul numerelor citite si sa se scrie rezultatul la sfarsitul fisierului.
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
        
        ; citim primul numar si il consieram maxim
        ; fscanf(descriptor, format_cit, n)
        push dword n
        push dword format_cit
        push dword [descriptor]
        call [fscanf]
        add esp, 4*3
        
        cmp eax, 1
        jne final
        
        mov ebx, [n] ; il consideram maxim
        
        repeta:
            ; fscanf(descriptor, format_cit, n)
            push dword n
            push dword format_cit
            push dword [descriptor]
            call [fscanf]
            add esp, 4*3
            
            cmp eax, 1
            jne afisare
            
            cmp ebx, [n]
            jl next
            
            mov ebx, [n]
            
            next:
                jmp repeta
                
        afisare:
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
