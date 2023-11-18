bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf      
import exit msvcrt.dll    
import printf msvcrt.dll    
import scanf msvcrt.dll   
 
segment data use32 class=data
    a dw 0
    b dw 0
    
    mesaja db "a = ", 0
    mesajb db "b = ", 0
    format db "%d", 0
    format_afis db "Cat = %d, rest = %d", 0
    
    

; 5. Se dau doua numere naturale a si b (a, b: word, definite in segmentul de date). Sa se calculeze a/b si sa se afiseze catul si restul impartirii in urmatorul format: "Cat = <cat>, rest = <rest>"
; Exemplu: pentru a=23 si b=10 se va afisa: "Cat = 2, rest = 3"
; Valorile vor fi afisate in format decimal (baza 10) cu semn.
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
        
        mov edx, 0
        ; impartire
        mov ax, [a]
        cwd
        mov bx, [b]
        
        idiv word bx
        
        cwde
        
        mov ecx, eax ; catul
        
        mov ax, dx
        cwd ; restul e in eax
        
        ; printf % , cat, rest
        push dword eax
        push dword ecx
        push dword format_afis
        call [printf]
        add esp, 4*3
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
