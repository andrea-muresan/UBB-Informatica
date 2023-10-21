bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, printf, scanf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fprintf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    cuvant times 20 db 0
    numar dd 0
    
    format_cit_nr db "%d", 0
    format_cit_cuv db "%s", 0
    format_afis db "%s ", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; Sa se citeasca de la tastatura un numar n, apoi sa se citeasca mai multe cuvinte, pana cand se citeste cuvantul/caracterul "#". Sa se scrie intr-un fisier text toate cuvintele citite care incep si se termina cu aceeasi litera si au cel putin n litere.
segment code use32 class=code
    start:
        ; scanf(format_cit_nr, numar)
        push dword numar
        push dword format_cit_nr
        call [scanf]
        add esp, 4*2
        
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        repeta:            
            ; scanf(format_cit_cuv, cuvant)
            push dword cuvant
            push dword format_cit_cuv
            call [scanf]
            add esp, 4*2
            
            mov esi, 0 ;lungime - index
            bucla:
                mov al, byte [cuvant + esi]
                cmp al, 0
                je gata
                
                cmp al, '#'
                je inchidere
                
                inc esi
                jmp bucla
            
            gata:
                cmp esi, [numar]
                jl next
                
                mov al, byte [cuvant]
                cmp al, [cuvant + esi - 1]
                jne next
                
                ; fprintf(descriptor, format_afis, cuvant)
                push dword cuvant
                push dword format_afis
                push dword [descriptor]
                call [fprintf]
                add esp, 4*2
                
                next:
                    jmp repeta
            
        inchidere:
        
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
