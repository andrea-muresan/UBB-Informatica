bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fscanf, printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fscanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    nume_fisier db "numere.txt", 0
    mod_acces db "r", 0
    descriptor dd -1
    
    numar dd 0
    sir_pare times 30 dd 0
    sir_impare times 30 dd 0
    
    format_cit db "%d", 0
    format_afis db "%d ", 0
    format_enter db 13, 10, 0
    
    eroare db "Deschiderea fisierul a esuat", 0

; 6. Se citesc din fisierul numere.txt mai multe numere (pare si impare). Sa se creeze 2 siruri rezultat N si P astfel: N - doar numere impare si P - doar numere pare. Afisati cele 2 siruri rezultate pe ecran.
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
        
        mov esi, 0 ; pare
        mov edi, 0 ; impare
        repeta:
            ; fscanf(descriptor, format_cit, numar)
            push dword numar
            push dword format_cit
            push dword [descriptor]
            call [fscanf]
            add esp, 4*3
            
            cmp eax, 1
            jne afisare
            
            mov ebx, [numar]
            test ebx, 0001h
            jz par
            
            impar:
                mov [sir_impare + edi], ebx
                inc edi
                jmp next
            par:
                mov [sir_pare + esi], ebx
                inc esi
                
            next:
                jmp repeta
                
        afisare:
            cld
            mov ecx, esi ; pare
            mov esi, sir_pare
            bucla:
                push ecx
                
                mov eax, 0
                lodsb
                ; printf(format_afis, eax)
                push dword eax
                push dword format_afis
                call [printf]
                add esp, 4*2
                
                pop ecx
            loop bucla
            
            push dword format_enter
            call [printf]
            add esp, 4
            
            cld
            mov ecx, edi ; impare
            mov esi, sir_impare
            bucla2:
                push ecx
                
                mov eax, 0
                lodsb
                ; printf(format_afis, eax)
                push dword eax
                push dword format_afis
                call [printf]
                add esp, 4*2
                
                pop ecx
            loop bucla2
        
        
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
