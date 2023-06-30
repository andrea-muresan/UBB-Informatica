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
    m dd 0
    n dd 0
    
    mesaja db "a = ", 0
    mesajm db "m = ", 0
    mesajn db "n = ", 0
    format db "%d", 0
    format_afis db "Numarul este: %d", 0

; 29. Se citesc de la tastatura trei numere a, m si n (a: word, 0 <= m, n <= 15, m > n). Sa se izoleze bitii de la m-n ai lui a si sa se afiseze numarul intreg reprezentat de acesti bitii in baza 10.
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
        
        ; m
        push dword mesajm
        call [printf]
        add esp, 4*1
        
        push dword m
        push dword format
        call [scanf]
        add esp, 4*2
        
        ; n
        push dword mesajn
        call [printf]
        add esp, 4*1
        
        push dword n
        push dword format
        call [scanf]
        add esp, 4*2
        
        ; izolare
        mov ecx, 0
        mov ax, [a]
        mov cl, 15
        sub cl, [m]
        shl ax, cl
        
        add cl, [n]
        shr ax, cl
        
        mov cl, [n]
        shl ax, cl
    
        ; afisare
        push dword eax
        push dword format_afis
        call [printf]
        add esp, 4*2
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
