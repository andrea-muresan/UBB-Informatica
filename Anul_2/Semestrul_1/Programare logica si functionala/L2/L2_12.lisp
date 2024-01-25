; 12. Sa se construiasca lista nodurilor unui arbore de tipul (2) parcurs in 
; preordine.

(defun preordine (lst)
	(cond
		((null lst) nil)
		(t (cons (car lst) (append (preordine (cadr lst)) (preordine (caddr lst)) )))
	)
)