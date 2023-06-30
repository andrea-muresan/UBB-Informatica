bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, printf, fprintf
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import printf msvcrt.dll    
import fprintf msvcrt.dll    


segment data use32 class=data
    nume_fisier db "fisier.txt", 0
    mod_acces db "w", 0
    descriptor dd -1
    
    text db "ana are mult mere", 0
    len equ $ - text -1
    text_final times 2 * len db 0
    zece db 10
    
    nr dd 0
    
    format_afis db "%s", 0
    
    eroare db "Deschiderea fisierului a esuat", 0

; 20. Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici si spatii. Sa se inlocuiasca toate literele de pe pozitii pare cu numarul pozitiei. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.
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
        
        
        cld
        mov esi, text
        mov edi, 0
        
        mov bl, 0
        mov ecx, len
        repeta:
            test bl, 01h
            jz par
            
            impar:
                lodsb
                mov [text_final + edi], al
                inc edi
                jmp next
            par:
                ; verificam daca e litera
                lodsb
                cmp al, 'a'
                jl pas
                
                cmp al, 'z'
                jg pas
                
                ; daca e litera
                mov al, bl
                
                mov edx, 0 ; in nr formam pozitia curenta sub forma de string, edx indicele
                bucla: 
                    mov ah, 0
                    div byte [zece]
                    add ah, '0'
                                        
                    ;mov [text_final + edi], ah
                    ;inc edi
                    mov [nr + edx], ah
                    inc edx
                    
                    cmp al, 0
                    je afara
                    
                    jmp bucla
                    
                afara:
                    push ecx
                    
                    mov ecx, edx
                    bucla2:
                        mov dl, byte [nr + ecx - 1]
                        mov [text_final + edi], dl
                        inc edi
                    loop bucla2
                    
                    pop ecx
                    
                jmp next
                pas:
                    mov [text_final + edi], al
                    inc edi
                
                
            next:
                inc bl
            
            loop repeta
                
                
            
        
        ; fprintf(descriptor, format_afis, text)
        push dword text_final
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
