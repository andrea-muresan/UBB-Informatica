; 16. Sa se decida daca un arbore de tipul (2) este echilibrat (diferenta dintre
; adancimile celor 2 subarbori nu este mai mare decat 1)

(defun maxi (a b)
	(cond
		((> a b) a)
		(t b)
	)
)

(defun adancime (lst)
	(cond 
		((null lst) 0)
		(t (maxi (+ 1 (adancime (cadr lst))) (+ 1 (adancime (caddr lst)))))
	)	
)

(defun echilibrat (lst)
	(cond 
		((> (- (adancime (cadr lst)) (adancime (caddr lst))) 1) nil)
		((< (- (adancime (cadr lst)) (adancime (caddr lst))) -1) nil)
		(t t)
	)
)