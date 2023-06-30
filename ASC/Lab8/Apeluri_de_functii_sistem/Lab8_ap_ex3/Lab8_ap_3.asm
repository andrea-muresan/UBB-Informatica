bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf              
import exit msvcrt.dll    
import scanf msvcrt.dll    
import printf msvcrt.dll    

; 3. Se dau doua numere naturale a si b (a, b: dword, definite in segmentul de date). Sa se calculeze suma lor si sa se afiseze in urmatorul format: "<a> + <b> = <result>"
; Exemplu: "1 + 2 = 3"
; Valorile vor fi afisate in format decimal (baza 10) cu semn.
segment data use32 class=data
    a dd 0
    b dd 0
    
    mesaja db "a = ", 0
    mesajb db "b = ", 0
    format_suma db "%d + %d = %d", 0
    format db "%d", 0
    
; our code starts here
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
        
        ; suma
        mov eax, [a]
        add eax, [b]
        
        push dword eax
        push dword [b]
        push dword [a]
        push dword format_suma
        call [printf]
        add esp, 4*4
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
