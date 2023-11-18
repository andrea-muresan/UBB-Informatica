bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               
import exit msvcrt.dll    
import scanf msvcrt.dll    
import printf msvcrt.dll    
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 0
    b dd 0 
    
    mesaja db "a = ", 0
    mesajb db "b = ", 0
    format_a_mic db "%d < %d", 0
    format_a_mare db "%d > %d", 0
    format_egalitate db "%d = %d", 0
    format db "%d", 0
    

; 25. Sa se citeasca de la tastatura doua numere a si b (in baza 10) şi să se determine relaţia de ordine dintre ele (a < b, a = b sau a > b). Afisati rezultatul în următorul format: "<a> < <b>, <a> = <b> sau <a> > <b>".
segment code use32 class=code
    start:
        ; a
        push dword mesaja
        call [printf]
        add esp, 4*1
        
        push dword a
        push dword format
        call [scanf]
        add esp, 4*2
        
        ; b
        push dword mesajb
        call [printf]
        add esp, 4*1
        
        push dword b
        push dword format
        call [scanf]
        add esp, 4*2
        
        ; comparatie
        mov eax, [a]
        mov ebx, [b]
        
        cmp eax, ebx
        je egalitate
        
        cmp eax, ebx
        jl a_mai_mic
        
        a_mai_mare:
            push dword [b]
            push dword [a]
            push format_a_mare
            call [printf]
            add esp, 4*3
            jmp final
            
        egalitate:
            push dword [b]
            push dword [a]
            push format_egalitate
            call [printf]
            add esp, 4*3
            jmp final
            
        a_mai_mic:
            push dword [b]
            push dword [a]
            push format_a_mic
            call [printf]
            add esp, 4*3
            jmp final
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
