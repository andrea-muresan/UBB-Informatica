bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    numar dd 0
    cuvant times 20 db 0
    cuvant_nou times 60 db 0
    
    format_cuv db "%s", 0
    format_numar db "%d", 0
    
    vocale db "aeiouAeiou", 0
    

; Se citeste de la tastatura un cuvant si un numar. Daca numarul este par se cere criptarea cuvantului prin adunarea la fiecare caracter a numarului 20. Daca numarul este impar se cere criptarea cuvantului prin adaugarea dupa fiecare vocala a gruparii "p"+vocala.Se cere afisarea cuvantului criptat.
segment code use32 class=code
    start:
        ; scanf(format_cuv, cuvant)
        push dword cuvant
        push dword format_cuv
        call [scanf]
        add esp, 4*2
        
        ; scanf(format_numar, numar)
        push dword numar
        push dword format_numar
        call [scanf]
        add esp, 4*2
        
        mov ebx, [numar]
        test ebx, 0001h
        jz par
        
        impar:
            mov esi, 0
            mov ebx, 0
            bucla:
                mov al, byte [cuvant + esi]
                cmp al, 0
                je gata_impar
                
                mov [cuvant_nou + ebx], al
                inc ebx ; punem litera in noul cuvant
                
                cld
                mov edi, vocale
                mov ecx, 10
                cauta:
                    scasb
                    je gasit
                loop cauta
                
                jmp next
                gasit:
                    mov cl, 'p'
                    mov [cuvant_nou + ebx], cl
                    inc ebx ; punem litera in noul cuvant
                    
                    mov [cuvant_nou + ebx], al
                    inc ebx ; punem litera in noul cuvant
                    
                next:
                    inc esi
                    jmp bucla
        par:
            mov esi, 0
            repeta:
                mov al, byte [cuvant + esi]
                cmp al, 0
                je gata_par
                
                add al, 20
                mov byte [cuvant + esi], al
                
                inc esi
                jmp repeta
                
        gata_par:
        
        ; printf(format_cuv, cuvant)
        push dword cuvant
        push dword format_cuv
        call [printf]
        
        jmp final
        gata_impar:
        ; printf(format_cuv, cuvant_nou)
        push dword cuvant_nou
        push dword format_cuv
        call [printf]
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
