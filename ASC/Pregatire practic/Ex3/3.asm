bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

extern exit, gets, printf, 
import exit msvcrt.dll
import gets msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data    
    string times 100 db 0
    invers times 100 db 0
    format_afis db "%s", 0    
    

; 3. Se citeste o propozitie de la tastatura. Sa se inverseze fiecare cuvant si sa se afiseze pe ecran.
segment code use32 class=code
    start:
        ; gets(string)
        push dword string
        call [gets]
        add esp, 4
        
        mov esi, 0
        repeta:
            mov al, 0
            cmp al, byte [string + esi]
            je afara
            
            inc esi
            jmp repeta
            
        afara:
        
        mov bl, ' '
        mov [string + esi], bl
        
        mov ecx, esi
        mov ebx, esi
        mov edi, invers
        bucla:
            
            mov bl, ' '
            cmp byte [string + ecx - 1], bl
            jne next   

            push ecx
            
            inversare:
                mov bl, ' '
                cmp byte [string + ecx], bl
                je gata
                
                mov al, byte [string + ecx]
                stosb
                inc ecx
                jmp inversare
            
            gata:
            mov al, ' '
            stosb
            pop ecx
            
            next:
        loop bucla
        
        mov ecx, 0
        inversare2:
                mov bl, ' '
                cmp byte [string + ecx], bl
                je gata2
                
                mov al, byte [string + ecx]
                stosb
                inc ecx
                jmp inversare2
        gata2:
        mov al, ' ' 
        stosb
        
        push dword invers
        push dword format_afis
        call [printf]
        add esp, 4*2
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
