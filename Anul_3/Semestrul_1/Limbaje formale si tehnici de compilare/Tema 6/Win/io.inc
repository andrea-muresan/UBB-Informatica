%ifndef __IO_INC__
%define __IO_INC__

; Reads a 32-bit integer
; Out: EAX - integer
extern io_readint

; Reads a 32-bit integer in base-16
; Out: EAX - integer
extern io_readhex

; Reads a 32-bit integer in base-2
; Out: EAX - integer
extern io_readbin

; Reads a string into the specified buffer (null-terminated)
; The size of the buffer should be at least 256 bytes!
; In: EAX - pointer
extern io_readstr

; Writes a 32-bit integer
; In: EAX - integer
extern io_writeint

; Writes a 32-bit integer in base-16
; In: EAX - integer
extern io_writehex

; Writes a 32-bit integer in base-2
; In: EAX - integer
extern io_writebin

; Writes a string (null-terminated)
; In: EAX - pointer
extern io_writestr

; Reads a 32-bit float
; Out: XMM0 - float
extern io_readflt

; Writes a 32-bit float
; In: XMM0 - float
extern io_writeflt

; New line
extern io_writeln

%endif