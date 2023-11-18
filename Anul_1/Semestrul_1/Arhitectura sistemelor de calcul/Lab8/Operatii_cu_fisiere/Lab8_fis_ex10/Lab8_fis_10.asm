bits 32 ; assembling for the 32 bits architecture


global start        


extern exit, fopen, fclose, printf, fprintf, scanf, gets, getchar
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
import gets msvcrt.dll
import getchar msvcrt.dll

segment data use32 class=data
    nume_fisier times 30 db 0
    mod_acces db "w", 0
    descriptor dd -1
    
    text times 130 db 0
    
    mesaj_nume_fis db "Introduceti numele fisierului: ", 0
    mesaj_text db "Introduceti textul: ", 0
    
    format_cit db "%s", 0
    format_afis db "%s", 0
    
    eroare db "Crearea fisierului a esuat", 0

; 10. Sa se citeasca de la tastatura un nume de fisier si un text. Sa se creeze un fisier cu numele dat in directorul curent si sa se scrie textul in acel fisier. Observatii: Numele de fisier este de maxim 30 de caractere. Textul este de maxim 120 de caractere.
segment code use32 class=code
    start:
        ;mesaj nume fisier
        push dword mesaj_nume_fis
        call [printf]
        add esp, 4
        
        ; scanf(format_cit, nume_fisier)
        push dword nume_fisier
        push dword format_cit
        call [scanf]
        add esp, 4*2
        
        call [getchar]
        
        ; mesaj text
        push dword mesaj_text
        call [printf]
        add esp, 4
        
        ; gets(text)
        push dword text
        call [gets]
        add esp, 4
        
        ; creare fisier - se realizeaza automat la deschidere (mod de acces - write)
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je eroare_deschidere
        
        mov [descriptor], eax
        
        ; fprintf(descriptor, format_afis, text)
        push dword text
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
