; 13. Se da un arbore de tipul (2). Sa se afiseze calea de la radacina pana la un 
; nod x dat.

(defun membru (lst el)
	(cond
		((null lst) nil)
		((eq (car lst) el) t)
		(t (or (membru (cadr lst) el) (membru (caddr lst) el)))
	)
)

(defun cale (lst nod)
	(cond
		((eql (car lst) nod) (list nod))
		((membru (cadr lst) nod) (cons (car lst) (cale (cadr lst) nod)))
    		((membru (caddr lst) nod) (cons (car lst) (cale (caddr lst) nod)))
		(t nil)
	)
)