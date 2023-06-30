bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        


extern exit, fopen, fclose, printf, scanf, fprintf, fread
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
import fread msvcrt.dll
import fprintf msvcrt.dll
import exit msvcrt.dll

segment data use32 class=data
    nume_input times 30 db 0
    acces_input db "r", 0
    desc_input dd -1
    
    nume_output db "output.txt", 0
    acces_output db "w", 0
    desc_output dd -1
    
    caract dd 0
    
    format_caract db "%c", 0
    format_numar db "%d", 0
    format_cit db "%s", 0

    eroare db "Deschiderea unui fisier a esuat", 0
    
; our code starts here
segment code use32 class=code
    start:
        ; scanf(format_cit, nume_input)
        push dword nume_input
        push dword format_cit
        call [scanf]
        add esp, 4*2
        
        ; fisier input
        ; fopen(nume_input, acces_input)
        push dword acces_input
        push dword nume_input
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [desc_input], eax
        
        ; fisier output
        ; fopen(nume_input, acces_input)
        push dword acces_output
        push dword nume_output
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [desc_output], eax
        
        
        repeta:
            ; fread(caract, 1, 1, desc_input)
            push dword [desc_input]
            push dword 1
            push dword 1
            push dword caract
            call [fread]
            add esp, 4*4
            
            cmp eax, 0
            je afara
            
            mov eax, 0
            mov al, byte [caract]
            cmp al, 'a'
            jl afisare
            
            cmp al, 'z'
            jg afisare
            
            ; fprintf(desc_output, format_numar, eax)
            push dword eax
            push dword format_numar
            push dword [desc_output]
            call [fprintf]
            add esp, 4*3
            
            jmp repeta
        
            afisare:
                ; fprintf(desc_output, format_caract, eax)
                push dword eax
                push dword format_caract
                push dword [desc_output]
                call [fprintf]
                add esp, 4*3
                
            jmp repeta
            
            
        afara:
        
        ; fclose(desc_output) 
        push dword [desc_output]
        call [fclose]
        add esp, 4
        
        ; fclose(desc_input) 
        push dword [desc_input]
        call [fclose]
        add esp, 4
        
        jmp final
        eroare_deschidere:
            push dword eroare
            call [printf]
            add esp,4
            
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
